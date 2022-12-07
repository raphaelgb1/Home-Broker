/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ContaDAO;
import dao.OperacoesContaDAO;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 *
 * @author rapha
 */
public class CobrancaDeTaxa {
    
    ContaController contaController = new ContaController();
    OperacoesContaController operacoesContaController = new OperacoesContaController();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public int cobrarTaxa (Set<ContaDAO> vetor, int idOperacoesConta, OperacoesContaDAO[] vetorOperacoesConta, GregorianCalendar calendario) {
        
        ContaDAO adm = contaController.getContaAdm(vetor);
        try {           
            for (ContaDAO element : vetor) {
                if(element != null && element.id > 2) {    
                    double userResult = operacoesContaController.depositoSaque(element.saldo, 20, false);
                    double admResult = operacoesContaController.depositoSaque(adm.saldo, 20, true);

                    boolean userContaUpdate = element.setSaldo(userResult);
                    boolean admContaUpdate =  adm.setSaldo(admResult);

                    if(userContaUpdate && admContaUpdate){
                       idOperacoesConta = operacoesContaController.newOperation(element, adm, vetorOperacoesConta, idOperacoesConta, null, calendario, 20, admResult,true);
                    } 
                }
            }
            
            return idOperacoesConta;
        } catch (Exception err) {
            throw err;
        }
    }
}
