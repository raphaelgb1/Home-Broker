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
public class crudController {
    
    private clienteDAO[] cliente = new clienteDAO[5];
    private crudUtils utils = new crudUtils();
    
    public int returnId (int id){
        for (clienteDAO obj : cliente) {
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
    
    public boolean insert (Object obj) {
        int indice = utils.verificar(cliente);
        if( indice >= 0){
           if(utils.insert(cliente, obj, indice)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean update (Object newObj, int indice) {
        try {
            utils.insert(cliente, newObj, indice);
            return true;
        } catch (Exception err) {
            return false;  
        }
    }
    
    public clienteDAO[] acessarVetor () {
        clienteDAO[] aux = new clienteDAO[5];
        aux = cliente;
        return aux;
    }
}
