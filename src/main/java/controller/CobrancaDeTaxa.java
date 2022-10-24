/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ContaDAO;
import dao.OperacoesContaDAO;
import javax.swing.JOptionPane;

/**
 *
 * @author rapha
 */
public class CobrancaDeTaxa {
    
    ContaController contaController = new ContaController();
    OperacoesContaController operacoesContaController = new OperacoesContaController();
    
    public boolean cobrarTaxa (ContaDAO[] vetorConta, ContaDAO conta, int idOperacoesConta, OperacoesContaDAO[] vetorOperacoesConta) {
        try {           
            for (ContaDAO element : vetorConta) {
                if(element != null) {    
                    int indice = contaController.returnIndex(element.id, vetorConta);
                    double userResult = operacoesContaController.depositoSaque(element.saldo, 20, false);
                    double admResult = operacoesContaController.depositoSaque(vetorConta[0].saldo, 20, true);

                    element.newData(element.id, element.cliente, userResult, element.dataCriacao, element.dataModificacao);
                    vetorConta[0].newData(vetorConta[0].id, vetorConta[0].cliente, admResult, vetorConta[0].dataCriacao, vetorConta[0].dataModificacao);
                    boolean userContaUpdate = contaController.update(element, vetorConta, indice);
                    boolean admContaUpdate = contaController.update(vetorConta[0], vetorConta, 0);

                    if(userContaUpdate && admContaUpdate){
                        OperacoesContaDAO userOperacao = new OperacoesContaDAO();
                        userOperacao.newData(++idOperacoesConta, element.id, vetorConta[0].id
                                , 1, userResult, 4, "Taxa de Manutenção"
                                , 20, element.dataCriacao, element.dataModificacao);
                        boolean userOp = operacoesContaController.insert(userOperacao, vetorOperacoesConta);

                        OperacoesContaDAO admOperacao = new OperacoesContaDAO();
                        admOperacao.newData(++idOperacoesConta, vetorConta[0].id, element.id
                                , 2, admResult, 5, "Taxa de Manutenção"
                                , 20, vetorConta[0].dataCriacao, vetorConta[0].dataModificacao);
                        boolean admOp = operacoesContaController.insert(admOperacao, vetorOperacoesConta);
                        if(userOp && admOp) {
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
