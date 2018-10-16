package net.daw.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;

public class TipousuarioService {

	HttpServletRequest oRequest ;
	
	
	public TipousuarioService(HttpServletRequest oRequest) {
		super();
		this.oRequest = oRequest;
	}


	public ReplyBean get() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection ;
		try {
			
			Integer id = Integer.parseInt(oRequest.getParameter("id"));
			
			
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();

			// servir la petición utilizando capa dao con el id  y oConnection

			oReplyBean = new ReplyBean(200, "OK");

			oConnectionPool.disposeConnection();

		} catch (Exception ex) {
			oReplyBean = new ReplyBean(500, "Bad Connection: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
			oConnectionPool.disposeConnection();
		}

		return oReplyBean;

	}

}
