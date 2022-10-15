/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author rapha
 */
public class crudUtils {
    
    public int verificar (Object vetor[]) {
        for(int x = 0; x < vetor.length; x++){
            if(vetor[x] == null){
                return x;
            }
        }
        return -1;
    }
    
    public int encontrar (Object vetor[], Object object) {
        for(int x = 0; x < vetor.length; x++){
            if(vetor[x].equals(object)){
                return x;
            }
        }
        return -1;
    }
    
    public boolean insert (Object vetor[], Object obj, int indice) {
        vetor[indice] = obj;   
        return true;
    }
    
    public boolean delete (Object vetor[], Object obj, int indice) {
        vetor[indice] = null;   
        return true;
    }
}

