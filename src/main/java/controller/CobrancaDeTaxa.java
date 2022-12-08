/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CobrancaDAO;
import dao.ContaDAO;
import dao.DBConnectionDAO;
import dao.OperacoesContaDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JOptionPane;

/**
 *
 * @author rapha
 */
public class CobrancaDeTaxa {
    
    ContaController contaController = new ContaController();
    OperacoesContaController operacoesContaController = new OperacoesContaController();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DBConnectionController dbConn = new DBConnectionController();

    public void registrarTaxa (GregorianCalendar calendario, Set<CobrancaDAO> aux) {
        try {
            if (!aux.isEmpty()) {
                Map<String, String> objUpdate = new LinkedHashMap<String, String>();   
                String sql = "INSERT INTO OPERACOES (IDCONTA,IDCONTADEST,OPERACAO,TIPO,SALDOFINAL,VALOROP,DESCRICAO,DTCRIACAO) VALUES";            
                int x = 1;
                for (CobrancaDAO element : aux) {
                    int tipo = 2;
                    int oper = 4;
                    if (element.conta == 1) {
                        tipo = 1;
                        oper = 5;
                    }
                    if (x == aux.size())
                        sql += "(" + element.conta + "," + element.contaDE + "," + tipo + "," + oper + "," + element.saldo + ",20,'Taxa de Manutenção','" + formatBanco.format(calendario.getTime()) + "')";            
                    else
                        sql += "(" + element.conta + "," + element.contaDE + "," + tipo + "," + oper + "," + element.saldo + ",20,'Taxa de Manutenção','" + formatBanco.format(calendario.getTime()) + "'),"; 
                    x++;
                }
                dbConn.executeInsert(sql);
            }       
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err.getMessage()); 
            throw err;
        }
    }   

    private Map getIds (GregorianCalendar calendario) {
        try {
            String date = format.format(calendario.getTime());
            String sql = "SELECT DISTINCT IDCONTA FROM CONTA WHERE IDCONTA NOT IN ("
                        + "SELECT IDCONTA FROM OPERACOES WHERE TIPO = 4 AND OPERACAO = 2 AND DTCRIACAO LIKE '%" + date + "%'" 
                        + "AND DESCRICAO LIKE '%Taxa%') AND IDCONTA NOT IN (1,2)";
            
            ResultSet result =  dbConn.execute(sql);
            Map<String, String> obj = new LinkedHashMap<String, String>();
            while(result.next()) {
                obj.put(result.getString("IDCONTA"), result.getString("IDCONTA"));
            }
            return obj;
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
            return null;
        }
    }

    public Set cobrarTaxa (GregorianCalendar calendario, Set<ContaDAO> vetorConta) {
        try {
            Map result = this.getIds(calendario);
            Map<String, String> objUpdate = new LinkedHashMap<String, String>();
            Set aux = new LinkedHashSet<>();
            Iterator it = result.entrySet().iterator();
            Map.Entry elements = null;
            ContaDAO contaAdm = contaController.getContaAdm(vetorConta);
        
            while(it.hasNext()) {
                elements = (Map.Entry)it.next();
                String value = elements.getValue().toString();
                ContaDAO contaCobranca = contaController.getContaUser(Integer.parseInt(value), vetorConta);
                
                if (contaCobranca == null) {
                    continue;
                }
                
                CobrancaDAO taxaUsuario = new CobrancaDAO();                            
                CobrancaDAO taxaAdm = new CobrancaDAO();
        
                double saldo = operacoesContaController.depositoSaque(contaCobranca.getSaldo(), 20, false);
                taxaUsuario.newData(contaCobranca.id, contaAdm.id, saldo);
                contaCobranca.setSaldo(saldo);
        
                objUpdate.put("SALDO", Double.toString(saldo));
                dbConn.update("CONTA", "IDCONTA", contaCobranca.id, objUpdate);
        
                saldo = operacoesContaController.depositoSaque(contaAdm.getSaldo(), 20, true);
                taxaAdm.newData(contaAdm.id, contaCobranca.id, saldo);
                objUpdate.put("SALDO", Double.toString(saldo));
                dbConn.update("CONTA", "IDCONTA", contaAdm.id, objUpdate);
                contaAdm.setSaldo(saldo);
        
                aux.add(taxaUsuario);
                aux.add(taxaAdm);
            }
            return aux;
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
            throw err;
        }
    }
}
