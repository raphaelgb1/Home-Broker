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
public class clienteDAO {
    public int id = 0;
    public String nome;
    public String endereco;
    public String CPF;
    public String telefone;
    public String login;
    public String senha;
    public boolean adm;
    public Date dataCriacao;
    public Date dataModificacao;
    
    public void newData (String name, String adress, 
            String cpf, String phone, String user, String pass, 
            boolean admin) {
        this.id++;
        this.nome = name;
        this.endereco = adress;
        this.CPF = cpf;
        this.telefone = phone;
        this.login = user;
        this.senha = pass;
        this.adm = admin;
        this.dataCriacao = new Date();
        this.dataModificacao = null;
    }
}
