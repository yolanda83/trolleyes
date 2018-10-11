package net.daw.connection.publicinterface;

import java.sql.Connection;

public interface ConnectionInterface {

	public Connection newConnection() throws Exception;

	public void disposeConnection() throws Exception;

}