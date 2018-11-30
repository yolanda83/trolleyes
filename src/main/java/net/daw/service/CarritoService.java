/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.daw.bean.CarritoBean;
import net.daw.bean.FacturaBean;
import net.daw.bean.LineaBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.FacturaDao;
import net.daw.dao.LineaDao;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;

/**
 *
 * @author Yolanda
 */

/*
El porque de implementar Serializable en la clase CarritoService 
http://chuwiki.chuidiang.org/index.php?title=Serializaci%C3%B3n_de_objetos_en_java
 */
public class CarritoService implements Serializable{
    
     HttpServletRequest oRequest;
    String ob = null;

    public CarritoService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean add() throws Exception {
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        Gson oGson = new Gson();

        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();

            Integer idProducto = Integer.parseInt(oRequest.getParameter("producto"));
            Integer cantidad = Integer.parseInt(oRequest.getParameter("cantidad"));

            //Esto no hace falta hacerlo pero esta muy bien porque asi se ve lo que contiene el objeto sesion y
            //que cosas puedo sacar de el
            HttpSession session = oRequest.getSession();
            //Aqui es donde el implements de la clase serializable hace su trabajo (o eso creo)
            ArrayList<CarritoBean> productosGuardados = (ArrayList<CarritoBean>) session.getAttribute("producto");

            //No puedo hacer una iteracion de un objeto en null, porque salta excepcion
            if (productosGuardados != null) {
                Boolean exists = false;
                for (CarritoBean o : productosGuardados) {
                    if (cantidad > o.getObj_producto().getExistencias()) {
                        oConnection.rollback();
                        throw new Exception(" cantidad superior a existencias");
                    }
                    if (idProducto == o.getObj_producto().getId()) {
                        o.setCantidad(o.getCantidad() + cantidad);
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    CarritoBean oCarritoBean = new CarritoBean();
                    ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                    ProductoBean oProductoBean = oProductoDao.get(idProducto, 1);
                    if (cantidad > oProductoBean.getExistencias()) {
                        oConnection.rollback();
                        throw new Exception(" cantidad superior a existencias");
                    }
                    oCarritoBean.setCantidad(cantidad);
                    oCarritoBean.setObj_producto(oProductoBean);
                    productosGuardados.add(oCarritoBean);
                }
                oRequest.getSession().setAttribute("producto", productosGuardados);
            } else {
                ArrayList<CarritoBean> alCarrito = new ArrayList<CarritoBean>();
                CarritoBean oCarritoBean = new CarritoBean();
                ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                ProductoBean oProductoBean = oProductoDao.get(idProducto, 1);
                if (cantidad > oProductoBean.getExistencias()) {
                    oConnection.rollback();
                    throw new Exception(" cantidad superior a existencias");
                }
                oCarritoBean.setCantidad(cantidad);
                oCarritoBean.setObj_producto(oProductoBean);
                alCarrito.add(oCarritoBean);
                oRequest.getSession().setAttribute("producto", alCarrito);
            }

        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getpage method: " + ob + " Error msg: " + ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("producto")));

    }

    public ReplyBean empty() {
        Gson oGson = new Gson();
        oRequest.getSession().setAttribute("producto", null);
        return new ReplyBean(200, oGson.toJson(oRequest.getSession()));
    }

    public ReplyBean show() throws Exception {
        Gson oGson = new Gson();
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("producto")));
    }

    public ReplyBean reduce() {
        Gson oGson = new Gson();
        Integer id = Integer.parseInt(oRequest.getParameter("id"));
        ArrayList<CarritoBean> alCarritoBeans = new ArrayList<CarritoBean>();
        ArrayList<CarritoBean> productosGuardados = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("producto");

        //No se puede hacer una iteracion de un objeto en null, porque salta excepcion
        if (productosGuardados != null) {
            for (CarritoBean o : productosGuardados) {
                if (o.getObj_producto().getId() != id) {
                    alCarritoBeans.add(o);
                } else {
                    int cantidad = o.getCantidad();
                    cantidad--;
                    o.setCantidad(cantidad);
                    alCarritoBeans.add(o);
                }
            }
        }
        oRequest.getSession().setAttribute("producto", alCarritoBeans);
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("producto")));
    }

    public ReplyBean buy() throws Exception {
        Gson oGson = new Gson();
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        //Arraylist donde guardare todas las lineas que habre creado por cada producto que haya en sesion
        ArrayList<LineaBean> alLineaProducto = new ArrayList<LineaBean>();
        FacturaBean bFacturaBean = null;
        LineaBean bLineaBean = null;

        Date oDate = new Date(); //fecha actual

        UsuarioBean oUsuarioSesion = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioSesion == null) {
            
            throw new Exception("Usuario no logeado");
        }
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            //Inicio transaccion
            oConnection.setAutoCommit(false);
            //Creo una nueva factura en bFacturaBean
            FacturaDao dFacturaDao = new FacturaDao(oConnection, "factura");
            bFacturaBean = new FacturaBean();
            bFacturaBean.setFecha(oDate);
            bFacturaBean.setIva(4);
            bFacturaBean.setObj_usuario(oUsuarioSesion);

            //Creo un DAO de Factura a partir de bFacturaBean y lo guardo en oNewFactura
            FacturaBean oNewFactura = new FacturaBean();
            oNewFactura = dFacturaDao.create(bFacturaBean);

            //Obtengo todos los productos de la sesion y los guardo en alCarritoSesion
            ArrayList<CarritoBean> alCarritoSesion = new ArrayList<CarritoBean>();
            alCarritoSesion = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("producto");

            //Creo lineas de facturas por cada producto que haya en la sesion
            for (CarritoBean o : alCarritoSesion) {
                LineaBean oNewLinea = new LineaBean();

                //Valido las existencias de la sesion con la de la base de datos?
                ProductoDao oProductoDaoValidacion = new ProductoDao(oConnection, "producto");
                ProductoBean oProductoBeanValidacion = new ProductoBean();
                oProductoBeanValidacion = oProductoDaoValidacion.get(o.getObj_producto().getId(), 1);
                if (o.getCantidad() > oProductoBeanValidacion.getExistencias()) {
                    oConnection.rollback();
                    throw new Exception("Existencias de sesion mayor que existencias almacen");
                }

                //A�ado los datos a la nueva linea y la guardo en el arrayList
                oNewLinea.setCantidad(o.getCantidad());
                oNewLinea.setObj_producto(o.getObj_producto());
                oNewLinea.setObj_factura(bFacturaBean);
                alLineaProducto.add(oNewLinea);
            }

            //Pasarela de pago
            //Creo una nueva linea por cada linea que haya en el array alLineaProducto
            LineaDao dLineaDao = new LineaDao(oConnection, "linea");
            for (LineaBean o : alLineaProducto) {
                dLineaDao.create(o);

                //Actualizo las existencias del producto comprado en la base de datos
                ProductoDao oActualizarExistenciasProductoDao = new ProductoDao(oConnection, "producto");
                ProductoBean oActualizarExistenciasProductoBean = oActualizarExistenciasProductoDao.get(o.getObj_producto().getId(), 1);
                oActualizarExistenciasProductoBean.setExistencias(oActualizarExistenciasProductoBean.getExistencias() - o.getCantidad());
                oActualizarExistenciasProductoDao.update(oActualizarExistenciasProductoBean);

            }
            //vaciamos el carrito
            oRequest.getSession().setAttribute("producto", null);
            //Fin transaccion
            oConnection.setAutoCommit(true);

        } catch (Exception e) {
            throw new Exception("ERROR: Service level: buy method: " + ob + " Erro msg: " + e);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return new ReplyBean(200, oGson.toJson("OK?"));
    }

    public ReplyBean showSessionAll() {
        Gson oGson = new Gson();
        return new ReplyBean(200, oGson.toJson(oRequest.getSession()));
    }
    
}