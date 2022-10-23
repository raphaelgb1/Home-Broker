/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.OperacoesContaDAO;

/**
 *
 * @author rapha
 */
public class OperacoesContaController extends CrudController {
    public int returnIndex (int id, OperacoesContaDAO[] vetor){
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
    
    public OperacoesContaDAO returnObjectById (int id, OperacoesContaDAO[] vetor){
        try {
            for (OperacoesContaDAO obj : vetor) {
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
    
    public double depositoSaque (double saldo, double valor, boolean tipo){
        try {
            if(tipo){//DEPÓSITO
                return saldo += valor;
            } else {//SAQUE
                return saldo -= valor;
            }
        } catch (Exception err) {
            return -1;
        }
    }
}
