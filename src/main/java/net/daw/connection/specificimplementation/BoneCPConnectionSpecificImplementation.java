package net.daw.connection.specificimplementation;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;

public class BoneCPConnectionSpecificImplementation implements ConnectionInterface {
	private Connection oConnection;
	private BoneCP oConnectionPool;

	public Connection newConnection() throws Exception {

		BoneCPConfig config = new BoneCPConfig();
		
		config.setJdbcUrl(ConnectionConstants.getConnectionChain()); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb

		config.setUsername(ConnectionConstants.databaseLogin);
		config.setPassword(ConnectionConstants.databasePassword);
		config.setMinConnectionsPerPartition(ConnectionConstants.getDatabaseMinPoolSize);
		config.setMaxConnectionsPerPartition(ConnectionConstants.getDatabaseMaxPoolSize);
		config.setPartitionCount(1);
	
		try {
			oConnectionPool = new BoneCP(config);
			oConnection = (Connection) oConnectionPool.getConnection();
			return oConnection;

		} catch (SQLException ex) {
			String msgError = this.getClass().getName() + ":" + (ex.getStackTrace()[1]).getMethodName();
			throw new Exception(msgError, ex);
		}

	}

	public void disposeConnection() throws Exception {
		if (oConnection != null) {
			oConnection.close();
		}
		if (oConnectionPool != null) {
			oConnectionPool.close();
		}
	}
}