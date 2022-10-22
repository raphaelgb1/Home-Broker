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
    
    public int returnIndex (int id, ClienteDAO[] vetor){
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
    
    public int verifyObjectIsVoid (ClienteDAO[] vetor) {
        try {
             for (ClienteDAO obj : vetor) {
                if(obj != null) {
                    return 0;
                }                      
            }
            return 1;
        } catch (Exception err) {
            return -1;
        }
    }
    
    public int verifyHaveOnlyAdm (ClienteDAO[] vetor) {//VERIFICAR SE HÁ 
        try {
            int count = 0;
             for (ClienteDAO obj : vetor) {
                if(obj != null) {
                    count++;
                }                      
            }
            return count;
        } catch (Exception err) {
            return -1;
        }
    }
    
    public ClienteDAO verifyUserName (ClienteDAO[] vetor, String name) {
        try {
            for (ClienteDAO element : vetor) {
                if(element.login.hashCode() == name.hashCode()){
                    return element;
                }
            }
            return null;
        } catch (Exception err) {
            return null;
        }
    }
}
