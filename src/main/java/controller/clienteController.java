/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.clienteDAO;
import utils.crudUtils;

/**
 *
 * @author rapha
 */
public class clienteController extends crudController {
    
    public int returnId (int id, clienteDAO[] vetor){
        for (clienteDAO obj : vetor) {
            if(obj != null) {
                if(obj.id == id){
                    return id-1;
                }
            } else {
                return -1;
            }                     
        }
        return -1;
    }
}
