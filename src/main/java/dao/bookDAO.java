package dao;

import controller.BookController;

public class BookDAO extends BookController {
    public AtivosDAO[] ativo = new AtivosDAO[3];
    private OrdemDAO[] ofertas_vendas = new OrdemDAO[500];
    private OrdemDAO[] ofertas_compra = new OrdemDAO[500];

    public OrdemDAO[] getOfertas_vendas() {
        return ofertas_vendas;
    }
    public OrdemDAO[] getOfertas_compra() {
        return ofertas_compra;
    }
    public AtivosDAO getAtivo(int id) {
        return ativo[id-1];
    }
    /**
     * @param ofertas_compra oferta de Compra
     */
    /**
     * @param ofertas_vendas oferta de Venda
     */
    public boolean setOfertas_Compra(OrdemDAO ofertas_compra) {
        try{
            int count = 0;
            for (OrdemDAO obj : this.ofertas_compra) {
                if(obj == null) {
                    this.ofertas_compra[count] = ofertas_compra;
                    return true;
                }
                count++;
            }
            return false;
        }
        catch (Exception err ){
            return false;
        }
    }

    public AtivosDAO[] getAtivos () {
        return this.ativo;
    }
    public boolean setOfertas_Venda(OrdemDAO ofertas_vendas) {
        try{
            int count = 0;
            for (OrdemDAO obj : this.ofertas_vendas) {
                if(obj == null) {
                    this.ofertas_vendas[count] = ofertas_vendas;
                    return true;
                }
                count++;
            }
            return false;
        } catch (Exception err){
            return false;
        }
    }

    public BookDAO newData(String data) {
        int id = 1;
        for (AtivosDAO ativos : this.ativo) {
            ativos = new AtivosDAO();
            ativos.ativosBook(data, id);
            ativo[id-1] = ativos;
            id++;
        }
        return this;
    }
    public String Ativos_book(){
        String extrato = "";
        for (AtivosDAO element : this.ativo) {
            extrato += "Id: " + element.id + "\n";
            extrato += "Ticker: " + element.ticker + "\n";
            extrato += "Empresa: " + element.empresa + "\n";
            extrato += "Preço inicial: " + element.preço_inicial + "\n";
            extrato += "Ultimo valor: " + element.ultimo_valor + "\n";
            extrato += "\n------------------------------\n";
        }
        return extrato;
    }
    public boolean Cadastro_Ordem(OrdemDAO Ordem){
        boolean status;
        if(Ordem.getTipo_ordem() == 1)
            status = this.setOfertas_Compra(Ordem);
        else
            status = this.setOfertas_Venda(Ordem);
        
        
        return status;
    }
}
