/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ContaDAO;

/**
 *
 * @author rapha
 */
public class ContaController extends CrudController {
    
    public int returnId (int id, ContaDAO[] vetor){
        try {
            for (ContaDAO obj : vetor) {
                if(obj != null) {
                    if(obj.id == id){
                        return id-1;
                    }
                }            
            }
            return -1;
        } catch (Exception err) {
            return -1;
        }
    }
    
    public ContaDAO returnObjectById (int id, ContaDAO[] vetor){
        try {
            for (ContaDAO obj : vetor) {
                if(obj != null) {
                    if(obj.cliente == id){
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
