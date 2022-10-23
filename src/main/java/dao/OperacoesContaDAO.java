/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author rapha
 */
public class OperacoesContaDAO {
    public int id;
    public int conta;
    public int operacao;//1 - Crédito, 2 - Débito
    public int tipo;//1 - Depósito, 2 - Saque, 3 - Transferência, 4 - Pagamento
    public String descricao;
    public double valor;
    public String dataCriacao;
    public String dataModificacao;
    
    public void newData (int id, int conta, int operacao, int tipo, String descricao, double valor, String dataCriacao, String dataModificacao) {
        this.id = id;
        this.conta = conta;
        this.operacao = operacao;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }
}
