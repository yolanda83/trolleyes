/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.connection.specificimplementation;

import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author jesus
 */
public class DBCPConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection;
    private BasicDataSource config;

    public Connection newConnection() throws Exception {
        config = new BasicDataSource();
        config.setUrl(ConnectionConstants.getConnectionChain());
        config.setUsername(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setMaxIdle(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setMinIdle(ConnectionConstants.getDatabaseMinPoolSize);

        try {
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