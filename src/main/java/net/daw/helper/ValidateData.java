/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.daw.helper;

public class ValidateData {
    public static boolean validateId(String entero){
        return entero.matches("^[1-9][0-9]*$");
    }
}
