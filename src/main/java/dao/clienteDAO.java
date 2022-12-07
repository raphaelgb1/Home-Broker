/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

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
    public String dataCriacao;
    public String dataModificacao;
    
    public void newData (int id, String nome, String endereco, 
            String CPF, String telefone, String login, String senha, 
            boolean adm, String dataCriacao, String dataModificacao) {
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

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCPF() {
        return CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAdm() {
        return adm;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public String getDataModificacao() {
        return dataModificacao;
    }

    
}
