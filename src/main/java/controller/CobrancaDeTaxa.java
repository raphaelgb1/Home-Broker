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

    public void cobrarTaxa (GregorianCalendar calendario, Set<CobrancaDAO> aux) {
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

    public Map getIds (GregorianCalendar calendario) {
        try {
            String date = format.format(calendario.getTime());
            String sql = "SELECT DISTINCT IDCONTA FROM CONTA WHERE IDCONTA NOT IN ("
                        + "SELECT IDCONTA FROM OPERACOES WHERE TIPO = 4 AND OPERACAO = 2 AND DTCRIACAO LIKE '%" + date + "%'" 
                        + "AND DESCRICAO LIKE '%Taxa%') AND IDCONTA NOT IN (1,2)";
            
            ResultSet result =  dbConn.execute(sql);
            Map<String, Integer> obj = new LinkedHashMap<String, Integer>();
            while(result.next()) {
                obj.put("IDCONTA", result.getInt("IDCONTA"));
            }
            return obj;
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
            return null;
        }
    }
}
