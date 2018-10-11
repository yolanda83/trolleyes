package net.daw.helper;

public class ConnectionHelper {

    public static String getDatabaseName() {
        return "trolleyes";
    }

    public static String getDatabaseLogin() {
        return "root";
    }

    public static String getDatabasePassword() {
        return "bitnami";
    }

    public static String getDatabasePort() {
        return "3306";
    }

    public static String getDatabaseHost() {
        return "127.0.0.1";
    }

    public static String getConnectionChain() {
        return "jdbc:mysql://" + ConnectionHelper.getDatabaseHost() + ":" + ConnectionHelper.getDatabasePort() + "/" + ConnectionHelper.getDatabaseName();
    }
    
    public static int getDatabaseMaxPoolSize() {
        return 10;
    }
    
    public static int getDatabaseMinPoolSize() {
        return 5;
    }
		
}
