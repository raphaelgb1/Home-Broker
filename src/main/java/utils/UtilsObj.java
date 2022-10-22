/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author rapha
 */
public class UtilsObj {
    public int vetorLength (Object[] vetor){
        int count = 0;
        for (Object element : vetor) {
            if(element != null){
                count++;
            }
        }
        return count;
    }
    
    public int verifyObjectIsVoid (Object[] vetor) {
        try {
             for (Object obj : vetor) {
                if(obj != null) {
                    return 0;
                }                      
            }
            return 1;
        } catch (Exception err) {
            return -1;
        }
    }
}
