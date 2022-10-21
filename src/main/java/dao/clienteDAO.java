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
public class ClienteDAO {
    public int id;
    public String nome;
    public String endereco;
    public String CPF;
    public String telefone;
    public String login;
    public String senha;
    public boolean adm;
    public Date dataCriacao;
    public Date dataModificacao;
    
    public void newData (int id, String nome, String endereco, 
            String CPF, String telefone, String login, String senha, 
            boolean adm, Date dataCriacao, Date dataModificacao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.CPF = CPF;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.adm = adm;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }
}
