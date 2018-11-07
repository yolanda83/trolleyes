/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.TipousuarioBean;
import net.daw.bean.UsuarioBean;
import net.daw.helper.SqlBuilder;

/**
 *
 * @author Ramón
 */
public class UsuarioDao {

	Connection oConnection;
	String ob = null;
	String ob2 = "tipoUsuario";

	public UsuarioDao(Connection oConnection, String ob) {
		super();
		this.oConnection = oConnection;
		this.ob = ob;
	}

	public UsuarioBean get(int id) throws Exception {
		String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
		UsuarioBean oUsuarioBean;
		ResultSet oResultSet = null;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
			oPreparedStatement.setInt(1, id);
			oResultSet = oPreparedStatement.executeQuery();
			if (oResultSet.next()) {
				oUsuarioBean = new UsuarioBean();
				oUsuarioBean.setId(oResultSet.getInt("id"));
				oUsuarioBean.setDni(oResultSet.getString("dni"));
				oUsuarioBean.setNombre(oResultSet.getString("nombre"));
				oUsuarioBean.setApe1(oResultSet.getString("ape1"));
				oUsuarioBean.setApe2(oResultSet.getString("ape2"));
				oUsuarioBean.setLogin(oResultSet.getString("login"));
				oUsuarioBean.setPass(oResultSet.getString("pass"));
				oUsuarioBean.setId_tipoUsuario(oResultSet.getInt("id_tipoUsuario"));
			} else {
				oUsuarioBean = null;
			}
		} catch (SQLException e) {
			throw new Exception("Error en Dao get de " + ob, e);
		} finally {
			if (oResultSet != null) {
				oResultSet.close();
			}
			if (oPreparedStatement != null) {
				oPreparedStatement.close();
			}
		}
		return oUsuarioBean;
	}

	public int remove(int id) throws Exception {
		int iRes = 0;
		String strSQL = "DELETE FROM " + ob + " WHERE id=?";
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
			oPreparedStatement.setInt(1, id);
			iRes = oPreparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error en Dao remove de " + ob, e);
		} finally {
			if (oPreparedStatement != null) {
				oPreparedStatement.close();
			}
		}
		return iRes;
	}

	public int getcount() throws Exception {
		String strSQL = "SELECT COUNT(id) FROM " + ob;
		int res = 0;
		ResultSet oResultSet = null;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
			oResultSet = oPreparedStatement.executeQuery();
			if (oResultSet.next()) {
				res = oResultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new Exception("Error en Dao get de " + ob, e);
		} finally {
			if (oResultSet != null) {
				oResultSet.close();
			}
			if (oPreparedStatement != null) {
				oPreparedStatement.close();
			}
		}
		return res;
	}

	public UsuarioBean create(UsuarioBean oUsuarioBean) throws Exception {
		String strSQL = "INSERT INTO " + ob
				+ " (id,dni,nombre,ape1,ape2,login,pass,id_tipoUsuario) VALUES (NULL, ?,?,?,?,?,?,?); ";
		ResultSet oResultSet = null;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
			oPreparedStatement.setString(1, oUsuarioBean.getDni());
			oPreparedStatement.setString(2, oUsuarioBean.getNombre());
			oPreparedStatement.setString(3, oUsuarioBean.getApe1());
			oPreparedStatement.setString(4, oUsuarioBean.getApe2());
			oPreparedStatement.setString(5, oUsuarioBean.getLogin());
			oPreparedStatement.setString(6, oUsuarioBean.getPass());
			oPreparedStatement.setInt(7, oUsuarioBean.getId_tipoUsuario());
			oPreparedStatement.executeUpdate();
			oResultSet = oPreparedStatement.getGeneratedKeys();
			if (oResultSet.next()) {
				oUsuarioBean.setId(oResultSet.getInt(1));
				oUsuarioBean.setPass(null);
			} else {
				oUsuarioBean.setId(0);
				oUsuarioBean.setPass(null);
			}
		} catch (SQLException e) {
			throw new Exception("Error en Dao create de " + ob, e);
		} finally {
			if (oResultSet != null) {
				oResultSet.close();
			}
			if (oPreparedStatement != null) {
				oPreparedStatement.close();
			}
		}
		return oUsuarioBean;
	}

	public int update(UsuarioBean oUsuarioBean) throws Exception {
		int iResult = 0;
		String strSQL = "UPDATE " + ob
				+ " SET dni = ?, nombre = ?, ape1 = ?, ape2 = ?, login = ?, pass = ?, id_tipoUsuario = ? WHERE id = ? ;";

		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
			oPreparedStatement.setString(1, oUsuarioBean.getDni());
			oPreparedStatement.setString(2, oUsuarioBean.getNombre());
			oPreparedStatement.setString(3, oUsuarioBean.getApe1());
			oPreparedStatement.setString(4, oUsuarioBean.getApe2());
			oPreparedStatement.setString(5, oUsuarioBean.getLogin());
			oPreparedStatement.setString(6, oUsuarioBean.getPass());
			oPreparedStatement.setInt(7, oUsuarioBean.getId_tipoUsuario());
			oPreparedStatement.setInt(8, oUsuarioBean.getId());
			iResult = oPreparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Error en Dao update de " + ob, e);
		} finally {
			if (oPreparedStatement != null) {
				oPreparedStatement.close();
			}
		}
		return iResult;
	}

	public ArrayList<UsuarioBean> getpage(int iRpp, int iPage, HashMap<String, String> hmOrder) throws Exception {
//		String strSQL = "SELECT * FROM " + ob;
		String strSQL = "SELECT u.id, u.dni, u.nombre, u.ape1, u.ape2, u.login, u.id_tipoUsuario, tu.desc";
                strSQL += " FROM " + ob + " u, " + ob2 + " tu";
                strSQL += " WHERE u.id_tipoUsuario = tu.id";
		strSQL += SqlBuilder.buildSqlOrder(hmOrder);
		ArrayList<UsuarioBean> alUsuarioBean;
		if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
			strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
			ResultSet oResultSet = null;
			PreparedStatement oPreparedStatement = null;
			try {
				oPreparedStatement = oConnection.prepareStatement(strSQL);
				oResultSet = oPreparedStatement.executeQuery();
				alUsuarioBean = new ArrayList<UsuarioBean>();
				while (oResultSet.next()) {
					UsuarioBean oUsuarioBean = new UsuarioBean();
					TipousuarioBean oTipousuarioBean = new TipousuarioBean();
					oUsuarioBean.setId(oResultSet.getInt("id"));
					oUsuarioBean.setDni(oResultSet.getString("dni"));
					oUsuarioBean.setNombre(oResultSet.getString("nombre"));
					oUsuarioBean.setApe1(oResultSet.getString("ape1"));
					oUsuarioBean.setApe2(oResultSet.getString("ape2"));
					oUsuarioBean.setLogin(oResultSet.getString("login"));
					oUsuarioBean.setPass(null);
                                        oUsuarioBean.setId_tipoUsuario(oResultSet.getInt("id_tipoUsuario"));
                                        oTipousuarioBean.setId(oResultSet.getInt("id_tipoUsuario"));
                                        oTipousuarioBean.setDesc(oResultSet.getString("desc"));
					oUsuarioBean.setObj_tipoUsuario(oTipousuarioBean);
					alUsuarioBean.add(oUsuarioBean);
				}
			} catch (SQLException e) {
				throw new Exception("Error en Dao getpage de " + ob, e);
			} finally {
				if (oResultSet != null) {
					oResultSet.close();
				}
				if (oPreparedStatement != null) {
					oPreparedStatement.close();
				}
			}
		} else {
			throw new Exception("Error en Dao getpage de " + ob);
		}
		return alUsuarioBean;
	}
}