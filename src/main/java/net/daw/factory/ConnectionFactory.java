package net.daw.factory;

import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.connection.specificimplementation.HikariConnectionSpecificImplementation;
import net.daw.connection.specificimplementation.ViburConnectionSpecificImplementation;
import net.daw.constant.ConnectionConstants;

public class ConnectionFactory {

    public static ConnectionInterface getConnection(ConnectionConstants.EnumConstans enumConnection) {
        ConnectionInterface oConnectionInterface = null;
        switch (enumConnection) {
            case Hikari:
                oConnectionInterface = new HikariConnectionSpecificImplementation();
                break;
            case Vibur:
                oConnectionInterface = new ViburConnectionSpecificImplementation();
                break;
            default:
                oConnectionInterface = new HikariConnectionSpecificImplementation();
                break;
        }
        return oConnectionInterface;

    }
}
