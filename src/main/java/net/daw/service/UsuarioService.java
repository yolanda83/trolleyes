/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ProductoBean;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.UsuarioDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;

import org.apache.commons.io.IOUtils;

/**
 *
 * @author Ram�n
 */
public class UsuarioService {

    HttpServletRequest oRequest;
    String ob = null;

    public UsuarioService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean get() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            UsuarioBean oUsuarioBean = oUsuarioDao.get(id, 1);
            // Gson oGson = new Gson();
//			Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            Gson oGson = (new GsonBuilder()).create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            int iRes = oUsuarioDao.remove(id);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: remove method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;

    }

    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            int registros = oUsuarioDao.getcount();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            UsuarioBean oUsuarioBean = new UsuarioBean();
            oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            oUsuarioBean = oUsuarioDao.create(oUsuarioBean);
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
//			Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            Gson oGson = (new GsonBuilder()).create();
            UsuarioBean oUsuarioBean = new UsuarioBean();
            oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            iRes = oUsuarioDao.update(oUsuarioBean);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            ArrayList<UsuarioBean> alUsuarioBean = oUsuarioDao.getpage(iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alUsuarioBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean fill() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        ArrayList<UsuarioBean> alUsuarioBean = new ArrayList<UsuarioBean>();

        try {
            alUsuarioBean = obtenerDatos();

            //String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = new Gson();
            UsuarioBean oUsuarioBean = new UsuarioBean();
            //oProductoBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);

            for (UsuarioBean usuarios : alUsuarioBean) {
                oUsuarioBean = oUsuarioDao.create(usuarios);
            }
//            oProductoBean = oProductoDao.create(oProductoBean);
//            oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
            oReplyBean = new ReplyBean(200, oGson.toJson("Usuarios creados correctamente"));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ArrayList<UsuarioBean> obtenerDatos() {
        ArrayList<UsuarioBean> alUsuario = new ArrayList<UsuarioBean>();
        Random randomDni = new Random();
        Random randomNombre = new Random();
        Random randomApe1 = new Random();
        Random randomApe2 = new Random();
        Random randomLogin = new Random();
        Random randomPass = new Random();
        Random randomTipoUsuario = new Random();
        UsuarioBean oUsuarioBean;

        String[] dni = {"76294479Y", "35015012L", "02562016T", "85478299H", "22910746D",
            "84459428R", "07424310W", "03146216T", "01715312H", "56338513J",
            "87911495K", "03309113B", "36646306S", "15928151F", "14973941K", "42402099N", "40274838K",
            "87430150C", "33081180G", "54757727V"};
        String[] nombre = {"ANTONIO", "JOSE", "JUAN", "GERMAN", "MIKEL",
            "GERARDO", "PASCUAL", "INAKI", "LEO", "GINES",
            "JOSEFA", "LUCIA", "JULIA", "SUSANA", "EVA", "CATALINA", "DANIELA",
            "LUISA", "ADRIANA", "ESTEFANIA"};
        String[] ape1 = {"GONZALEZ", "RODRIGUEZ", "FERNANDEZ", "LOPEZ", "MARTINEZ", "SANCHEZ", "PEREZ", "GOMEZ",
            "MARTIN", "JIMENEZ", "RUIZ", "HERNANDEZ", "DIAZ", "MUNOZ", "ALVAREZ", "ROMERO", "ALONSO", "GUTIERREZ",
            "RAMOS", "CASTILLO"};
        String[] ape2 = {"GONZALEZ", "RODRIGUEZ", "FERNANDEZ", "LOPEZ", "MARTINEZ", "SANCHEZ", "PEREZ", "GOMEZ",
            "MARTIN", "JIMENEZ", "RUIZ", "HERNANDEZ", "DIAZ", "MUNOZ", "ALVAREZ", "ROMERO", "ALONSO", "GUTIERREZ",
            "RAMOS", "CASTILLO"};
        String[] login = {"Ton", "Kitty", "Dog", "Rob", "Cat",
            "Ger", "Pascu", "Isla", "Cinta", "Japan",
            "Sonar", "Miki", "Cons", "Green", "Black", "Pat", "Azar",
            "Batik", "Play", "Monster"};
        String[] pass = {"abc", "def", "ghi", "jkl", "mnn",
            "opq", "rst", "uvw", "xyz", "123",
            "456", "789", "0AB", "CDE", "FGH", "IJK", "LMN",
            "NOP", "QRS", "TUV"};
        Integer[] tipoUsuario = {1, 2}; //vigilar que existan estos tipo usuario si no no los crear

        for (int i = 0; i < 5; i++) {
            oUsuarioBean = new UsuarioBean();
            int randDni = randomDni.nextInt(20);
            int randNombre = randomNombre.nextInt(20);
            int randApe1 = randomApe1.nextInt(20);
            int randApe2 = randomApe2.nextInt(20);
            int randLogin = randomLogin.nextInt(20);
            int randPass = randomPass.nextInt(20);
            int randTipoUsuario = randomTipoUsuario.nextInt(2);

            oUsuarioBean.setDni(dni[randDni]);
            oUsuarioBean.setNombre(nombre[randNombre]);
            oUsuarioBean.setApe1(ape1[randApe1]);
            oUsuarioBean.setApe2((ape2[randApe2]));
            oUsuarioBean.setLogin(login[randLogin]);
            oUsuarioBean.setPass(pass[randPass]);
            oUsuarioBean.setId_tipoUsuario(tipoUsuario[randTipoUsuario]);
            alUsuario.add(oUsuarioBean);
        }
        return alUsuario;
    }

    public ReplyBean login() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        String strLogin = oRequest.getParameter("user");
        String strPassword = oRequest.getParameter("pass");

        oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
        oConnection = oConnectionPool.newConnection();
        UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);

        UsuarioBean oUsuarioBean = oUsuarioDao.login(strLogin, strPassword);
        if (oUsuarioBean != null) {
            oRequest.getSession().setAttribute("user", oUsuarioBean);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            //throw new Exception("ERROR Bad Authentication: Service level: get page: " + ob + " object");
            oReplyBean = new ReplyBean(401, "Bad Authentication");
        }
        return oReplyBean;
    }

    public ReplyBean logout() throws Exception {
        oRequest.getSession().invalidate();
        return new ReplyBean(200, "OK");
    }

    public ReplyBean check() throws Exception {
        ReplyBean oReplyBean;
        UsuarioBean oUsuarioBean;
        oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean != null) {
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            oReplyBean = new ReplyBean(401, "No active session");
        }
        return oReplyBean;
    }

}
