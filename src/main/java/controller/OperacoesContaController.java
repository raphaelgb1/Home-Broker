/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import dao.ContaDAO;
import dao.OperacoesContaDAO;

/**
 *
 * @author rapha
 */
public class OperacoesContaController extends CrudController {

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    DBConnectionController dbConnectionController = new DBConnectionController();
    public Set search(int id, int offset, String dtinicial, String dtfinal) {

        Set<OperacoesContaDAO> obj = new LinkedHashSet<>();
        try { 
            String sql = "SELECT * FROM OPERACOES WHERE IDCONTA = " + id + " AND DTCRIACAO BETWEEN DATE('"
                        + dtinicial + "') AND DATE('" + dtfinal + "') ORDER BY DTCRIACAO DESC LIMIT 5 OFFSET " + offset;
            ResultSet result = dbConnectionController.execute(sql);
            while(result.next()) {
                OperacoesContaDAO operacoes = new OperacoesContaDAO();
                operacoes.newData(
                      result.getInt("IDOPERACOES")
                    , result.getInt("IDCONTA")
                    , result.getInt("IDCONTADEST")
                    , result.getInt("OPERACAO")
                    , result.getInt("TIPO")
                    , result.getDouble("SALDOFINAL")
                    , result.getDouble("VALOROP")
                    , result.getString("DESCRICAO")
                    , result.getString("DTCRIACAO")
                    , result.getString("DTMODIFICACAO")
                );
                obj.add(operacoes);
            }
            return obj;
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
            throw null;
        }

    }

    public Set relatorio(int id, String dtinicial, String dtfinal) {
        Set<OperacoesContaDAO> obj = new LinkedHashSet<>();
        try { 
            String sql = "SELECT * FROM OPERACOES WHERE IDCONTA = " + id + " AND DTCRIACAO BETWEEN DATE('"
                        + dtinicial + "') AND DATE('" + dtfinal + "') ORDER BY DTCRIACAO";
            ResultSet result = dbConnectionController.execute(sql);
            while(result.next()) {
                OperacoesContaDAO operacoes = new OperacoesContaDAO();
                operacoes.newData(
                      result.getInt("IDOPERACOES")
                    , result.getInt("IDCONTA")
                    , result.getInt("IDCONTADEST")
                    , result.getInt("OPERACAO")
                    , result.getInt("TIPO")
                    , result.getDouble("SALDOFINAL")
                    , result.getDouble("VALOROP")
                    , result.getString("DESCRICAO")
                    , result.getString("DTCRIACAO")
                    , result.getString("DTMODIFICACAO")
                );
                obj.add(operacoes);
            }
            return obj;
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
            throw null;
        }
    }

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
            
            pagador.newData(0, 0, 0, 0, 0, 0, 0, " ", " ", " ");
            recebedor.newData(0, 0, 0, 0, 0, 0, 0, " ", " ", " ");
            pagador.newData(0, 0, 0, 0, 0, 0, 0, " ", " ", " ");
            recebedor.newData(0, 0, 0, 0, 0, 0, 0, " ", " ", " ");
            insert(pagador, vetorOperacoesConta);
            insert(recebedor, vetorOperacoesConta);

            return idOperacoesConta;
        } catch (Exception err) {
            throw err;
        }
    }
}
