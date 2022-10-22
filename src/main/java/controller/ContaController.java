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
    
    public int returnIndex (int id, ContaDAO[] vetor){
        try {
            for (int x = 0; x < vetor.length; x++) {
                if(vetor[x] != null) {
                    if(vetor[x].id == id){
                        return x;
                    }
                }            
            }
            return -1;
        } catch (Exception err) {
            return -2;
        }
    }
    
    public ContaDAO returnObjectById (int id, ContaDAO[] vetor){
        try {
            for (ContaDAO obj : vetor) {
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
    
    public ContaDAO returnContaByCliente (int cliente, ContaDAO[] vetor) {
        try {
            for (ContaDAO obj : vetor) {
                if(obj != null) {
                    if(obj.cliente == cliente){
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
