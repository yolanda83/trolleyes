package net.daw.service;

import java.sql.Connection;

import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;

public class TipousuarioService {

	public ReplyBean get() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection ;
		try {
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();

			// servir la petición utilizando capa dao y oConnection

			oReplyBean = new ReplyBean(200, "OK");

			oConnectionPool.disposeConnection();

		} catch (Exception ex) {
			oReplyBean = new ReplyBean(500, "Bad Connection: " + EncodingHelper.escapeQuotes(ex.getMessage()));
			oConnectionPool.disposeConnection();
		}

		return oReplyBean;

	}

}
