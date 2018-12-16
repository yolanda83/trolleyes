package net.daw.service;

import com.google.gson.Gson;
import java.io.Serializable;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
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
import net.daw.helper.ValidateData;

/**
 *
 * @author a073597589g
 */
public class CarritoService implements Serializable {

    HttpServletRequest oRequest;
    String ob = null;

    public CarritoService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    protected Boolean checkPermission(String strMethodName) {
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        return oUsuarioBean != null;
    }

    public ReplyBean add() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("add")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            Gson oGson = new Gson();
            try {
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                String strProducto = oRequest.getParameter("id");
                String strCantidad = oRequest.getParameter("cant");
                if (ValidateData.validateId(strProducto)) {
                    if (ValidateData.validateId(strCantidad)) {
                        Integer id_producto = Integer.parseInt(strProducto);
                        Integer cantidad = Integer.parseInt(strCantidad);
                        ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                        ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                        ProductoBean oProductoBean = oProductoDao.get(id_producto, 1);
                        if (oProductoBean != null) {
                            if (alCarrito != null) {
                                Boolean exists = false;
                                for (CarritoBean o : alCarrito) {
                                    if (id_producto == o.getObj_producto().getId()) {
                                        o.setCantidad(o.getCantidad() + cantidad);
                                        exists = true;
                                        break;
                                    }
                                }
                                if (!exists) {
                                    CarritoBean oCarritoBean = new CarritoBean();
                                    oProductoDao = new ProductoDao(oConnection, "producto");
                                    oProductoBean = oProductoDao.get(id_producto, 1);
                                    oCarritoBean.setCantidad(cantidad);
                                    oCarritoBean.setObj_producto(oProductoBean);
                                    alCarrito.add(oCarritoBean);
                                }
                                oRequest.getSession().setAttribute("carrito", alCarrito);
                            } else {
                                ArrayList<CarritoBean> newAlCarrito = new ArrayList<>();
                                CarritoBean oCarritoBean = new CarritoBean();
                                oProductoDao = new ProductoDao(oConnection, "producto");
                                oProductoBean = oProductoDao.get(id_producto, 1);
                                oCarritoBean.setCantidad(cantidad);
                                oCarritoBean.setObj_producto(oProductoBean);
                                newAlCarrito.add(oCarritoBean);
                                oRequest.getSession().setAttribute("carrito", newAlCarrito);
                            }
                            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                        } else {
                            oReplyBean = new ReplyBean(400, "Ese producto no existe.");
                        }
                    } else {
                        oReplyBean = new ReplyBean(400, "La cantidad tiene que ser un numero mayor a 0.");
                    }
                } else {
                    oReplyBean = new ReplyBean(400, "El id tiene que ser un numero mayor a 0.");
                }
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: add method: " + ob + " object: " + ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean reduce() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("reduce")) {
            Gson oGson = new Gson();
            try {
                String strProducto = oRequest.getParameter("id");
                String strCantidad = oRequest.getParameter("cant");
                if (ValidateData.validateId(strProducto)) {
                    if (ValidateData.validateId(strCantidad)) {
                        Integer id_producto = Integer.parseInt(strProducto);
                        Integer cantidad = Integer.parseInt(strCantidad);
                        ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                        if (alCarrito != null) {
                            Boolean exists = false;
                            for (CarritoBean o : alCarrito) {
                                if (id_producto == o.getObj_producto().getId()) {
                                    exists = true;
                                    if (o.getCantidad() > cantidad) {
                                        o.setCantidad(o.getCantidad() - cantidad);
                                    } else {
                                        alCarrito.remove(o);
                                    }
                                    break;
                                }
                            }
                            if (!alCarrito.isEmpty() && exists) {
                                oRequest.getSession().setAttribute("carrito", alCarrito);
                                oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                            } else {
                                if (alCarrito.isEmpty()) {
                                    oRequest.getSession().setAttribute("carrito", null);
                                    oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                                } else {
                                    oReplyBean = new ReplyBean(400, "El producto seleccionado no existe.");
                                }
                            }
                        } else {
                            oReplyBean = new ReplyBean(400, "El carrito esta vacio.");
                        }
                    } else {
                        oReplyBean = new ReplyBean(400, "La cantidad tiene que ser un numero mayor a 0.");
                    }
                } else {
                    oReplyBean = new ReplyBean(400, "El id tiene que ser un numero mayor a 0.");
                }
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: reduce method: " + ob + " object: " + ex);
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("remove")) {
            Gson oGson = new Gson();
            try {
                String strProducto = oRequest.getParameter("id");
                if (ValidateData.validateId(strProducto)) {
                    Integer id_producto = Integer.parseInt(strProducto);
                    ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                    if (alCarrito != null) {
                        Boolean exists = false;
                        for (CarritoBean o : alCarrito) {
                            if (id_producto == o.getObj_producto().getId()) {
                                exists = true;
                                alCarrito.remove(o);
                                break;
                            }
                        }
                        if (!alCarrito.isEmpty() && exists) {
                            oRequest.getSession().setAttribute("carrito", alCarrito);
                            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                        } else {
                            if (alCarrito.isEmpty()) {
                                oRequest.getSession().setAttribute("carrito", null);
                                oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                            } else {
                                oReplyBean = new ReplyBean(400, "El producto seleccionado no existe.");
                            }
                        }
                    } else {
                        oReplyBean = new ReplyBean(400, "El carrito esta vacio.");
                    }
                } else {
                    oReplyBean = new ReplyBean(400, "El id tiene que ser un numero mayor a 0.");
                }
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: remove method: " + ob + " object: " + ex);
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean empty() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("empty")) {
            Gson oGson = new Gson();
            oRequest.getSession().setAttribute("carrito", null);
            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean show() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("show")) {
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean buy() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("buy")) {
            ConnectionInterface oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            Connection oConnection = oConnectionPool.newConnection();
            try {
                oConnection.setAutoCommit(false);
                UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
                ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                if (alCarrito != null) {
                    //Crear obj_factura (preguntar sobre iva en factura y hora de la fecha y como ensenyarla en plist)
                    FacturaBean oFacturaBean = new FacturaBean();
                    FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
                    LocalDateTime fechaHora = LocalDateTime.now();
                    Instant instant = fechaHora.toInstant(ZoneOffset.ofHours(+1));
                    Date fecha = Date.from(instant);
                    oFacturaBean.setFecha(fecha);
                    oFacturaBean.setIva(21);
                    oFacturaBean.setId_usuario(oUsuarioBean.getId());
                    oFacturaBean = oFacturaDao.create(oFacturaBean);
                    //Crear Lineas (preguntar sobre id_tipoProducto en productoBean)
                    LineaBean oLineaBean = new LineaBean();
                    LineaDao oLineaDao = new LineaDao(oConnection, "linea");
                    ProductoBean oProductoBean;
                    ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                    boolean cantidadCorrecta = true;
                    String productoIncorrecto = null;
                    for (CarritoBean o : alCarrito) {
                        oLineaBean.setId_factura(oFacturaBean.getId());
                        if (o.getCantidad() <= o.getObj_producto().getExistencias()) {
                            oLineaBean.setCantidad(o.getCantidad());
                            oProductoBean = oProductoDao.get(o.getObj_producto().getId(), 0);
                            oProductoBean.setExistencias(oProductoBean.getExistencias() - o.getCantidad());
                            oProductoDao.update(oProductoBean);
                            oLineaBean.setId_producto(o.getObj_producto().getId());
                            oLineaDao.create(oLineaBean);
                        } else {
                            cantidadCorrecta = false;
                            productoIncorrecto = o.getObj_producto().getDesc();
                            break;
                        }
                    }
                    if (cantidadCorrecta) {
                        oConnection.commit();
                        Gson oGson = new Gson();
                        oRequest.getSession().setAttribute("carrito", null);
                        oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                    } else {
                        oConnection.rollback();
                        oReplyBean = new ReplyBean(400, "Se ha seleccionado una cantidad superior a las existencias del producto " + productoIncorrecto);
                    }
                } else {
                    oConnection.rollback();
                    oReplyBean = new ReplyBean(400, "No existen productos.");
                }
            } catch (Exception ex) {
                oConnection.rollback();
                throw new Exception("ERROR: Service level: buy method: " + ob + " object: " + ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

}
