package net.daw.factory;

import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.connection.specificimplementation.HikariConnectionSpecificImplementation;
import net.daw.helper.EnumHelper;

public class ConnectionFactory {
	public static ConnectionInterface getConnection(EnumHelper.connectionEnum enumConnection) {
		ConnectionInterface oConnectionInterface = null;
		switch (enumConnection) {
		case Hikari:
			oConnectionInterface = new HikariConnectionSpecificImplementation();
			break;
		}
		return oConnectionInterface;

	}
}
