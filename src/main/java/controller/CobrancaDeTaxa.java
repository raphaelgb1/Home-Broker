/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ContaDAO;
import dao.OperacoesContaDAO;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author rapha
 */
public class CobrancaDeTaxa {
    
    ContaController contaController = new ContaController();
    OperacoesContaController operacoesContaController = new OperacoesContaController();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public int cobrarTaxa (ContaDAO[] vetorConta, ContaDAO conta, int idOperacoesConta, OperacoesContaDAO[] vetorOperacoesConta, GregorianCalendar calendario) {
        try {           
            for (ContaDAO element : vetorConta) {
                if(element != null && element.id != 1) {    
                    double userResult = operacoesContaController.depositoSaque(element.saldo, 20, false);
                    double admResult = operacoesContaController.depositoSaque(vetorConta[0].saldo, 20, true);

                    boolean userContaUpdate = element.setSaldo(userResult);
                    boolean admContaUpdate =  vetorConta[0].setSaldo(admResult);

                    if(userContaUpdate && admContaUpdate){
                       idOperacoesConta = operacoesContaController.newOperation(element, vetorConta[0], vetorOperacoesConta, idOperacoesConta, null, calendario, 20, admResult);
                    } 
                }
            }
            
            return idOperacoesConta;
        } catch (Exception err) {
            throw err;
        }
    }
}
