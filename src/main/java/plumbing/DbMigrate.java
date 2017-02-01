package plumbing;

import org.flywaydb.core.Flyway;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class DbMigrate {
    public static boolean runMigrationsIfRequested(String[] commandLineArgs) throws Exception {
        if( commandLineArgs.length < 1 ){
            return false;
        }
        if(!commandLineArgs[0].equals("migrate")){
            return false;
        }

        System.out.println("migrating database...");

        Flyway flyway = new Flyway();

        setDataSource(flyway);

        flyway.migrate();

        return true;
    }

    // based on https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#using-the-database_url-in-plain-jdbc
    private static void setDataSource(Flyway flyway) throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        flyway.setDataSource(dbUrl,username,password);
    }
}
