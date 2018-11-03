/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.ProductoBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author a044531896d
 */
public class ProductoService {

    HttpServletRequest oRequest;
    String ob = null;

    public ProductoService(HttpServletRequest oRequest) {
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
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ProductoBean oProductoBean = oProductoDao.get(id);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
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
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            int iRes = oProductoDao.remove(id);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
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
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            int registros = oProductoDao.getcount();
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        ArrayList<ProductoBean> alProductoBean = new ArrayList<ProductoBean>();

        try {
            alProductoBean = obtenerDatos();

            //String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = new Gson();
            ProductoBean oProductoBean = new ProductoBean();
            //oProductoBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);

            for (ProductoBean productos : alProductoBean) {
                oProductoBean = oProductoDao.create(productos);
            }
//            oProductoBean = oProductoDao.create(oProductoBean);
//            oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
            oReplyBean = new ReplyBean(200, oGson.toJson("Productos creados correctamente"));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ArrayList<ProductoBean> obtenerDatos() {
        ArrayList<ProductoBean> alProducto = new ArrayList<ProductoBean>();
        Random randomDesc = new Random();
        Random randomTipoProducto = new Random();
        Random randomCodigo = new Random();
        ProductoBean oProductoBean;

        String[] desc = {"Leche sin lactosa", "Kobe", "Entrecot", "Flan casero", "Salm�n ahumado",
            "Yogurt pi�a", "Lubina", "Cordero", "Arroz con leche", "Flan",
            "Natillas", "Dorada", "Pechuga pavo", "Conejo", "Mantequilla", "Bacalao", "Leche entera",
            "Filete de buey", "Hamburguesa pollo", "Queso fresco"};
        Integer[] tipoProducto = {1, 2, 3, 4};
        String[] codigo = {"3K9GVf", "7MCm7L", "A4ny6n", "ASKP2y", "NzXW2z", "PoFiCh", "SMzVCG", "YPnoRa",
            "adEDSf", "iSejg3", "jFyTtN", "jWTBAq", "kBX8wX", "kYQVb2", "pRQjFo", "rhCnTF", "ruHK5q", "s8yuKi",
            "vWsPAh", "yB4cRh"};

        for (int i = 0; i < 5; i++) {
            oProductoBean = new ProductoBean();
            int randDesc = randomDesc.nextInt(20);
            int randTipoProducto = randomTipoProducto.nextInt(4);
            int randCodigo = randomCodigo.nextInt(20);
            int existencias = ThreadLocalRandom.current().nextInt(0, 3000 + 1);
            double precio = ThreadLocalRandom.current().nextDouble(1, 1000 + 1);

            oProductoBean.setDesc(desc[randDesc]);
            oProductoBean.setId_tipoProducto(tipoProducto[randTipoProducto]);
            oProductoBean.setCodigo(codigo[randCodigo]);
            oProductoBean.setExistencias(existencias);
            oProductoBean.setPrecio((float) precio);
            alProducto.add(oProductoBean);
        }
        return alProducto;
    }

    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {

//            BufferedReader br = new BufferedReader(new InputStreamReader(oRequest.getInputStream()));
//            String json = "";
//            if (br != null) {
//                json = br.readLine();
//            }

            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = new Gson();
            ProductoBean oProductoBean = new ProductoBean();
            oProductoBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            iRes = oProductoDao.update(oProductoBean);
            oReplyBean.setStatus(200);
            oReplyBean.setJson(Integer.toString(iRes));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
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
            String order = oRequest.getParameter("order");
            String ordervalue = oRequest.getParameter("ordervalue");
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ArrayList<ProductoBean> alProductoBean = oProductoDao.getpage(iRpp, iPage, order, ordervalue);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(alProductoBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }
}
