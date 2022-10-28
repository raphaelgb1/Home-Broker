package dao;

import controller.CrudController;

public class InvestimentoDAO extends CrudController {
    public int id;
    public int conta;
    public InvestOrdemDAO[] ordem = new InvestOrdemDAO[100];
    public String dataCriacao;
    public String dataModificacao;
    
    public void newData (int id, int conta, InvestOrdemDAO[] ordem, String dataCriacao, String dataModificacao) {
        this.id = id;
        this.conta = conta;
        this.ordem = ordem;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    public boolean setInvestimentoOrdem(InvestOrdemDAO investimento) {
        try {
            investimento.insert(investimento, ordem);
            return true;
        } catch (Exception err) {
            throw err;
        }
        
    }

    public InvestOrdemDAO[] getInvestimentoOrdem() {
        try {
            return ordem;
        } catch (Exception err) {
            throw err;
        }
        
    }
}
