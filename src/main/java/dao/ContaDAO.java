/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import controller.CrudController;

/**
 *
 * @author rapha
 */
public class ContaDAO extends CrudController {
    public int id;
    public int cliente;
    public double saldo;
    public String dataCriacao;
    public String dataModificacao;
    
    public void newData (int id, int cliente, double saldo, String dataCriacao, String dataModificacao) {
        this.id = id;
        this.cliente = cliente;
        this.saldo = saldo;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    public boolean setSaldo (double saldo) {
        this.saldo = saldo;
        return true;
    }

    public ContaDAO returnClone () {
        try {
          return (ContaDAO)this.clone();
        }
        catch (CloneNotSupportedException ex) {
          ex.printStackTrace();
          return null;
        }
      }
}
