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
    
    public boolean cobrarTaxa (ContaDAO[] vetorConta, ContaDAO conta, int idOperacoesConta, OperacoesContaDAO[] vetorOperacoesConta, GregorianCalendar calendario) {
        try {           
            for (ContaDAO element : vetorConta) {
                if(element != null && element.id != 1) {    
                    int indice = contaController.returnIndex(element.id, vetorConta);
                    double userResult = operacoesContaController.depositoSaque(element.saldo, 20, false);
                    double admResult = operacoesContaController.depositoSaque(vetorConta[0].saldo, 20, true);

                    element.newData(element.id, element.cliente, userResult, element.dataCriacao, element.dataModificacao);
                    vetorConta[0].newData(vetorConta[0].id, vetorConta[0].cliente, admResult, vetorConta[0].dataCriacao, vetorConta[0].dataModificacao);
                    boolean userContaUpdate = contaController.update(element, vetorConta, indice);
                    boolean admContaUpdate = contaController.update(vetorConta[0], vetorConta, 0);

                    if(userContaUpdate && admContaUpdate){
                        int idPagador = ++idOperacoesConta;
                        int idRecebedor = ++idOperacoesConta;
                        boolean result = operacoesContaController.newOperation(element, vetorConta[0], vetorOperacoesConta, idPagador, idRecebedor, null, calendario, 20, admResult);
                        if(result) {
                            if(conta != null){
                                if(element.id == conta.id) {
                                    conta = element;
                                } 
                            }
                        } 
                    } 
                }
            }
            
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
