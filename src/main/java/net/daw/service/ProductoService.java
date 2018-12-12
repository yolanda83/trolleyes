package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import net.daw.helper.ParameterCook;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
            ProductoBean oProductoBean = oProductoDao.get(id, 1);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
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
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            int iRes = oProductoDao.remove(id);
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
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            int registros = oProductoDao.getcount();
            Gson oGson = new Gson();
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
            Gson oGson = new Gson();
            ProductoBean oProductoBean = new ProductoBean();
            oProductoBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            oProductoBean = oProductoDao.create(oProductoBean);
            oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean fill() throws Exception {
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

        String[] desc = {"Leche sin lactosa", "Kobe", "Entrecot", "Flan casero", "Salmon ahumado",
            "Yogurt pina", "Lubina", "Cordero", "Arroz con leche", "Flan",
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
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = new Gson();
            ProductoBean oProductoBean = new ProductoBean();
            oProductoBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            iRes = oProductoDao.update(oProductoBean);
            oReplyBean = new ReplyBean(200, oGson.toJson(iRes));
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
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ArrayList<ProductoBean> alProductoBean = oProductoDao.getpage(iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alProductoBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

//    public ReplyBean getpage() throws Exception {
//        ReplyBean oReplyBean;
//        ConnectionInterface oConnectionPool = null;
//        Connection oConnection;
//        try {
//            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
//            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
//            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
//            oConnection = oConnectionPool.newConnection();
//            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
//            ArrayList<ProductoBean> alProductoBean = oProductoDao.getpage(iRpp, iPage);
//            Gson oGson = new Gson();
//            oReplyBean = new ReplyBean(200, oGson.toJson(alProductoBean));
//        } catch (Exception ex) {
//            throw new Exception("ERROR: Service level: getpage method: " + ob + " object", ex);
//        } finally {
//            oConnectionPool.disposeConnection();
//        }
//
//        return oReplyBean;
//
//    }
    public ReplyBean loaddata() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        ArrayList<ProductoBean> productos = new ArrayList<>();
        RellenarService oRellenarService = new RellenarService();
        try {
            Integer number = Integer.parseInt(oRequest.getParameter("number"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            productos = oRellenarService.RellenarProducto(number);
            for (ProductoBean producto : productos) {
                oProductoDao.create(producto);
            }
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson("Productos creados: " + number));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        }
        return oReplyBean;
    }

    public ReplyBean addimage() throws Exception {

        String name = "";
        ReplyBean oReplyBean;
        Gson oGson = new Gson();

        HashMap<String, String> hash = new HashMap<>();

        if (ServletFileUpload.isMultipartContent(oRequest)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(oRequest);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        name = new File(item.getName()).getName();
                        item.write(new File(".//..//webapps//ROOT//imagenes//" + name));
                    } else {
                        hash.put(item.getFieldName(), item.getString());
                    }
                }
                oReplyBean = new ReplyBean(200, oGson.toJson("File upload: " + name));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500, oGson.toJson("Error while uploading file: " + name));
            }
        } else {
            oReplyBean = new ReplyBean(500, oGson.toJson("Can't read image"));
        }

        return oReplyBean;
    }
}
