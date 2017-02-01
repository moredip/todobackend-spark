package plumbing;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Database {
    private final DataSource dataSource;

    public static Database fromEnvVar() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return new Database(dbUrl, username, password);
    }

    public static Database forIntegrationTesting() {
        return new Database(
                "jdbc:postgresql://192.168.99.100:5432/app_test",
                "postgres",
                "pass"
        );
    }

    private Database(String url, String username, String password) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

        this.dataSource = ds;
    }

    public boolean runMigrationsIfRequested(String[] commandLineArgs) {
        if( commandLineArgs.length < 1 ){
            return false;
        }
        if(!commandLineArgs[0].equals("migrate")){
            return false;
        }

        System.out.println("migrating database...");

        Flyway flyway = new Flyway();
        flyway.setDataSource(this.dataSource);
        flyway.migrate();

        return true;
    }

    public <T> T getDao( Class<T> daoType) {
        DBI dbi = new DBI(this.dataSource);
        return dbi.open(daoType);
    }

    // based on https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#using-the-database_url-in-plain-jdbc
    private static DataSource getDataSource() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(dbUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    public Database nukeAndRecreate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(this.dataSource);
        flyway.clean();
        flyway.migrate();
        return this;
    }
}
