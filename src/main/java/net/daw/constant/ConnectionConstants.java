package net.daw.constant;

public class ConnectionConstants {

    public static enum EnumConstans {
        Hikari,
        DBCP,
        Vibur
    };

    public static final EnumConstans connectionPool = EnumConstans.Vibur;
    public static final String databaseName = "trolleyes";
    public static final String databaseLogin = "root2";
    public static final String databasePassword = "bitnami";
    public static final String databasePort = "3306";
    public static final String databaseHost = "localhost";
    public static final int getDatabaseMaxPoolSize = 10;
    public static final int getDatabaseMinPoolSize = 5;

    public static String getConnectionChain() {
        return "jdbc:mysql://" + ConnectionConstants.databaseHost + ":" + ConnectionConstants.databasePort + "/"
                + ConnectionConstants.databaseName;
    }

}
