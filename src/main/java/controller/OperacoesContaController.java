/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import dao.ContaDAO;
import dao.OperacoesContaDAO;

/**
 *
 * @author rapha
 */
public class OperacoesContaController extends CrudController {

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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

    public int newOperation (ContaDAO contaPagador, ContaDAO contaRecebedor, OperacoesContaDAO[] vetorOperacoesConta, int idOperacoesConta, String descricao, GregorianCalendar calendario, double transferencia, double resultDE, boolean pagTrans) {
        try{
            //TRUE = PAGAMENTO, FALSE = TRANSFERÊCIA
            int tipo = (pagTrans) ? 4 : 3;
            String descricaoRec = (pagTrans) ? "Recebimento de Pagamento" : "Recebimento de Transferência";
            OperacoesContaDAO pagador = new OperacoesContaDAO();
            OperacoesContaDAO recebedor = new OperacoesContaDAO();  
            
            pagador.newData(++idOperacoesConta, contaPagador.id, contaRecebedor.id,1, contaPagador.saldo,tipo, descricao, transferencia, format.format(calendario.getTime()), null);
            recebedor.newData(++idOperacoesConta, contaRecebedor.id, contaPagador.id,2, resultDE,5, descricaoRec, transferencia, format.format(calendario.getTime()), null);
            insert(pagador, vetorOperacoesConta);
            insert(recebedor, vetorOperacoesConta);

            return idOperacoesConta;
        } catch (Exception err) {
            throw err;
        }
    }
}
