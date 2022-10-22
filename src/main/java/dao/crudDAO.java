/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author rapha
 */
public class CrudDAO {
    
    public int verificar (Object[] vetor) {
        try {
            for(int x = 0; x < vetor.length; x++){
                if(vetor[x] == null){
                    return x;
                }
            }
            return -1;
        } catch (Exception err) {
            return -2;
        }
        
    }
    
    public boolean insert (Object[] vetor, Object obj, int indice) {
         try {
            vetor[indice] = obj;   
            return true;
        } catch (Exception err) {
            return false;
        }
    }
    
    public boolean delete (Object[] vetor, int indice) {
        try {
            vetor[indice] = null;   
            return true;
        } catch (Exception err) {
            return false;
        }
    }
    
//    public boolean read (Object[] vetor, Object obj){
//        try {
//            for (Object element : vetor) {
//                if(element.hashCode() == obj.hashCode()){
//                    return true;
//                }
//            }
//            return false;
//        } catch (Exception e) {
//            return false;
//        }
//    }
}

