/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.Date;

/**
 *
 * @author rapha
 */
public class ContaDAO {
    public int id;
    public int cliente;
    public double saldo;
    public Date dataCriacao;
    public Date dataModificacao;
    
    public void newData (int id, int cliente, double saldo, Date dataCriacao, Date dataModificacao) {
        this.id = id;
        this.cliente = cliente;
        this.saldo = saldo;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }
}
