/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CrudDAO;

/**
 *
 * @author rapha
 */
public class CrudController extends Object implements Cloneable {
    
    CrudDAO crudDAO = new CrudDAO();
    
    public boolean insert (Object obj, Object[] vetor) {
        try {
            int indice = crudDAO.verificar(vetor);
            crudDAO.insert(vetor, obj, indice);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
    
    public boolean update (Object newObj, Object[] vetor, int indice) {
        try {
            crudDAO.insert(vetor, newObj, indice);
            return true;
        } catch (Exception err) {
            return false;  
        }
    }
    
    public boolean delete (int indice, Object[] vetor) {
        try {
            crudDAO.delete(vetor, indice);
            return true;
        } catch (Exception err) {
            return false;  
        }
    }
 
}
