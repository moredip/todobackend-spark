import plumbing.App;
import plumbing.Database;

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

    static int getPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }else{
            return 4567;
        }
    }
}