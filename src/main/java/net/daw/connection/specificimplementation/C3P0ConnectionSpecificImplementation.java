package net.daw.connection.specificimplementation;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;

public class C3P0ConnectionSpecificImplementation implements ConnectionInterface {
	
	private Connection oConnection;
	private ComboPooledDataSource config;
	

	public Connection newConnection() throws Exception {
		config = new ComboPooledDataSource();
		
		config.setJdbcUrl(ConnectionConstants.getConnectionChain());
		config.setUser(ConnectionConstants.databaseLogin);
		config.setPassword(ConnectionConstants.databasePassword);
		config.setMaxPoolSize(ConnectionConstants.getDatabaseMaxPoolSize);
		config.setMinPoolSize(ConnectionConstants.getDatabaseMinPoolSize);
		
		
		
		config.setInitialPoolSize(5);
		
		config.setAcquireIncrement(5);
		
		config.setMaxStatements(100);
		
		

		try {
			
			//oConnectionPool = new ComboPooledDataSource(config);
			oConnection = config.getConnection();
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
		if (config != null) {
			config.close();
		}

	}

}
