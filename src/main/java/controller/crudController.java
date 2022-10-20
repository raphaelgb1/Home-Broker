/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import utils.crudUtils;

/**
 *
 * @author rapha
 */
public class crudController {
    
    crudUtils utils = new crudUtils();
    
    public boolean insert (Object obj, Object[] vetor) {
        int indice = utils.verificar(vetor);
        if( indice >= 0){
           if(utils.insert(vetor, obj, indice)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean update (Object newObj, Object[] vetor, int indice) {
        try {
            utils.insert(vetor, newObj, indice);
            return true;
        } catch (Exception err) {
            return false;  
        }
    }
    
    public boolean delete (int indice, Object[] vetor) {
        try {
            utils.delete(vetor, indice);
            return true;
        } catch (Exception err) {
            return false;  
        }
    }
}
