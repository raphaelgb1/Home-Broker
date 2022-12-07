package dao;

public class CobrancaDAO {

    public int conta;
    public int contaDE;
    public double saldo;
    public double valorOp;
    
    public void newData (int conta, int contaDE, double saldo) {
        this.conta = conta;
        this.contaDE = contaDE;
        this.saldo = saldo;
    }

}
