package dao;

import controller.CrudController;

public class InvestimentoDAO {
public int id;
    public int conta;
    public double totalInvestido;
    public double lucroPrejuizo;
    // public InvestOrdemDAO[] ordem = new InvestOrdemDAO[100];
    public String dataCriacao;
    public String dataModificacao;
    
    public void newData (int id, int conta, /*InvestOrdemDAO[] ordem,*/ String dataCriacao, String dataModificacao) {
        this.id = id;
        this.conta = conta;
        // this.ordem = ordem;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }


    public boolean setTotalInvestido(double totalInvestido) {
        try {
            this.totalInvestido = totalInvestido;
            return true;
        } catch (Exception err) {
            throw err;
        }
        
    } 

    public boolean setLucroPrejuizo(double totalInvestido) {
        try {
            this.lucroPrejuizo = totalInvestido;
            return true;
        } catch (Exception err) {
            throw err;
        }
        
    } 

    public double getTotalInvestido() {
        try {
            return this.totalInvestido;
        } catch (Exception err) {
            throw err;
        } 
    } 

    // public InvestOrdemDAO[] getInvestimentoOrdem() {
    //     try {
    //         return ordem;
    //     } catch (Exception err) {
    //         throw err;
    //     }
        
    // }
}
