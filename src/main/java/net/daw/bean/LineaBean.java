/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.daw.dao.FacturaDao;
import net.daw.dao.ProductoDao;

/**
 *
 * @author a044531896d
 */
public class LineaBean {

    private int id, cantidad, id_producto, id_factura;
    private ProductoBean obj_producto;
    private FacturaBean obj_factura;

    public ProductoBean getObj_producto() {
        return obj_producto;
    }

    public void setObj_producto(ProductoBean obj_producto) {
        this.obj_producto = obj_producto;
    }

    public FacturaBean getObj_factura() {
        return obj_factura;
    }

    public void setObj_factura(FacturaBean obj_factura) {
        this.obj_factura = obj_factura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public LineaBean fill(ResultSet oResultSet, Connection oConnection, Integer expandProducto, Integer expandFactura) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));
        if (expandProducto > 0) {
            ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
            this.setObj_producto(oProductoDao.get(oResultSet.getInt("id_producto"), expandProducto));
        } else {
            this.setId_producto(oResultSet.getInt("id_producto"));
        }
        if (expandFactura > 0) {
            FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
            this.setObj_factura(oFacturaDao.get(oResultSet.getInt("id_factura"), expandFactura));
        } else {
            this.setId_factura(oResultSet.getInt("id_factura"));
        }

        return this;
    }

}
