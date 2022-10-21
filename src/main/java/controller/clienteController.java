/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ClienteDAO;

/**
 *
 * @author rapha
 */
public class ClienteController extends CrudController {
    
    public int returnId (int id, ClienteDAO[] vetor){
        for (ClienteDAO obj : vetor) {
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
    
    public ClienteDAO returnObjectById (int id, ClienteDAO[] vetor){
        try {
            for (ClienteDAO obj : vetor) {
                if(obj != null) {
                    if(obj.id == id){
                        return obj;
                    }
                }                      
            }
            return null;
        } catch (Exception err) {
            return null;
        }
    }
}
