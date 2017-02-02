import plumbing.App;
import plumbing.Database;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            runMigrationsOrStartServer(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void runMigrationsOrStartServer(String[] commandLineArgs) throws Exception {
        if( runMigrationsIfRequested(commandLineArgs) ){
            return;
        }

        App.initServer(getPort());
    }

    private static boolean runMigrationsIfRequested(String[] commandLineArgs) throws Exception {
        return Database.fromEnvVar().runMigrationsIfRequested(commandLineArgs);
    }

    private static int getPort() {
        return Optional.ofNullable(System.getenv("PORT"))
                .map(Integer::parseInt)
                .orElse(4567);
    }
}