/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import java.util.ArrayList;
import net.daw.bean.ProductoBean;

/**
 *
 * @author a044531896d
 */
public class RellenarService {

    public ArrayList<ProductoBean> RellenarProducto(int numero) {
        String[] codigo = {"D3Q","F5Q","DP5","84D","63P"};
        String[] desc = {"sistema", "tubo", "soporte","estación", " accesorio"};
        String[] desc2 = {"capilar", "tubos","trabajo","coche","tren"};
        String foto = "Foto";
        int[] id_tipoProducto = {1, 2, 3, 4, 5};
        int[] existencias = {1, 2, 3, 4, 5};
        
        //Para tener mayor control de el maximo de objetos que tengo en los arrays
        int maxDatos = 5;
        
        ArrayList<ProductoBean> resultado = new ArrayList<>();
        ProductoBean resultadoProducto;
        for (int i  = 0;i < numero; i++ ){
            resultadoProducto = new ProductoBean();
            resultadoProducto.setCodigo(codigo[randomMath(maxDatos)]);
            resultadoProducto.setDesc(desc[randomMath(maxDatos)] + " " + desc2[randomMath(maxDatos)]);
            //El precio sale con 4 decimales porque creo que para el cambio de divisas mola, si no pues se redondea y au
            resultadoProducto.setPrecio((float) (Math.random()*100));
            resultadoProducto.setFoto(foto);
            resultadoProducto.setId_tipoProducto(id_tipoProducto[randomMath(maxDatos)]);
            resultadoProducto.setExistencias(existencias[randomMath(maxDatos)]);
            resultado.add(resultadoProducto);
        }
        return resultado;
    }
    private int randomMath(int number){
        return (int) (Math.random()*number);
    }

}
