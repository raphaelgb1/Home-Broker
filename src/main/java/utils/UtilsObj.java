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

    public double calculaJuros () {
        try {
            double random = -2 + (double) (Math.random() * 5);
            return random;
        } catch (Exception err) {
            throw err;
        }
    }
}
