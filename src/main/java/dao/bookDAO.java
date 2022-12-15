package dao;

import controller.BookController;

public class BookDAO extends BookController {
    public AtivosDAO[] ativo = new AtivosDAO[3];
    private OrdemDAO[] ofertas_vendas = new OrdemDAO[500];
    private OrdemDAO[] ofertas_compra = new OrdemDAO[500];
    private OrdemDAO[] Ofertas_ORDEM0 = new OrdemDAO[500];


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
    // public boolean get_ordenscompra(int id){
    //     try{
    //         for (OrdemDAO obj : this.ofertas_vendas) {
    //             if(obj.getConta().id == id) {
    //                 return true;
    //             }
    //         }
    //         return false;
    //     }
    //     catch (Exception err ){
    //         return false;
    //     }
    // }
    public double get_quantidadeordenscompra(int id){
        try{
            double count = 0;
            for (OrdemDAO obj : this.ofertas_vendas) {
                if(obj.getConta().id == id && (obj.ativo.Quantidade - obj.ativo.quantidade_excultada) > 0 ) {
                    count += obj.ativo.quantidade_excultada;
                }
            }
            return count;
        }
        catch (Exception err ){
            return 0;
        }
    }
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
    public boolean ordem_venda(OrdemDAO Ordem){// Ordem = Ordem de compra
        try{
            double count = 0;
            for (OrdemDAO obj : this.ofertas_vendas) {
                if(obj.getAtivo().getId() == Ordem.getAtivo().getId() && (Ordem.getAtivo().getQuantidade()- Ordem.getAtivo().getQuantidade_excultada()) !=0 && Ordem.getAtivo().getPreço_inicial() >= obj.getAtivo().getPreço_inicial()) {
                    if ((obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada()) < (Ordem.getAtivo().getQuantidade() - Ordem.getAtivo().getQuantidade_excultada()))
                        count  = (obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada());
                    else
                        count  = (Ordem.getAtivo().getQuantidade() - Ordem.getAtivo().getQuantidade_excultada());
                    Ordem.ativo.quantidade_excultada += count;
                    obj.ativo.quantidade_excultada += count;
                }
            }
            return true;
        }
        catch (Exception err ){
            return false;
        }
    }
    public boolean ordem_Compra(OrdemDAO Ordem){// Ordem = Ordem de venda
        try{
            double count = 0;
            for (OrdemDAO obj : this.ofertas_compra) {
                if(obj.getAtivo().getId() == Ordem.getAtivo().getId() && (Ordem.getAtivo().getQuantidade()- Ordem.getAtivo().getQuantidade_excultada()) !=0 && Ordem.getAtivo().getPreço_inicial() >= obj.getAtivo().getPreço_inicial() ) {
                    if ((obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada()) < (Ordem.getAtivo().getQuantidade() - Ordem.getAtivo().getQuantidade_excultada()))
                        count  = (obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada());
                    else
                        count  = (Ordem.getAtivo().getQuantidade() - Ordem.getAtivo().getQuantidade_excultada());
                    
                    Ordem.ativo.quantidade_excultada += count;
                    obj.ativo.quantidade_excultada += count;
                }
            }
            return true;
        }
        catch (Exception err ){
            return false;
        }
    }

    public int getOfertas_Compra(AtivosDAO ativo, ContaDAO conta) {//pega quantidade de acões de um ativo
        try{
            int Quantidade = 0;
            for (OrdemDAO obj : this.ofertas_compra) {
                if(obj.getConta().id == conta.id && obj.getAtivo().getId() == ativo.getId()) {
                    Quantidade += obj.getAtivo().getQuantidade();
                }
            }
            return Quantidade;
        }
        catch (Exception err ){
            return 0;
        }
    }
    public String getMeu_ativo(ContaDAO conta) {//pega quantidade de acões de um ativo
        try{
            String extrato = "";
            for (OrdemDAO obj : this.ofertas_compra) {
                if(obj != null && obj.getConta().id == conta.id && obj.getAtivo().getQuantidade_excultada() > 0) {
                    extrato += "Id: " + obj.getAtivo().getId() + "\n";
                    extrato += "Ticker: " + obj.getAtivo().getTicker() + "\n";
                    extrato += "Valor: R$" + obj.getAtivo().getPreço_inicial() + "\n";
                    extrato += "Empresa: " + obj.getAtivo().getEmpresa() + "\n";
                    extrato += "quantidade: " + obj.getAtivo().getQuantidade_excultada() + "\n";
                    extrato += "\n------------------------------\n";
                }
            }
            return extrato;
        }
        catch (Exception err ){
            return "";
        }
    }
    public String getBook_ativo() {//pega quantidade de acões de um ativo
        try{
            String extrato = "";
            for (OrdemDAO obj : this.ofertas_compra) {
                if(obj != null && (obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada()) != 0) {
                    extrato += "Id: " + obj.getAtivo().getId() + "\n";
                    extrato += "Ticker: " + obj.getAtivo().getTicker() + "\n";
                    extrato += "Empresa: " + obj.getAtivo().getEmpresa() + "\n";
                    extrato += "quantidade: " + (obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada())  + "\n";
                    extrato += "Ativo para \"compra.\"";
                    extrato += "\n------------------------------\n";
                }
            }
            for (OrdemDAO obj : this.ofertas_vendas) {
                if(obj != null && (obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada()) != 0) {
                    extrato += "Id: " + obj.getAtivo().getId() + "\n";
                    extrato += "Ticker: " + obj.getAtivo().getTicker() + "\n";
                    extrato += "Empresa: " + obj.getAtivo().getEmpresa() + "\n";
                    extrato += "quantidade: " + (obj.getAtivo().getQuantidade() - obj.getAtivo().getQuantidade_excultada())  + "\n";
                    extrato += "Ativo para \"venda.\" \n";
                    extrato += "\n------------------------------\n";
                }
            }
            return extrato;
        }
        catch (Exception err ){
            return "";
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
        boolean status = false;
        if(Ordem.getTipo_ordem() == 1){
            status = this.setOfertas_Compra(Ordem);
            this.ordem_venda(Ordem);
        }else{
            status = this.setOfertas_Venda(Ordem);
            this.ordem_Compra(Ordem);
        }
        return status;
    }
    public int Quantidade_Ativos_Conta(AtivosDAO ativo, ContaDAO conta){
        int Quantidade;
        Quantidade = this.getOfertas_Compra(ativo, conta);
        return Quantidade;
    }
    public String Meu_ativos(ContaDAO conta){
        String string = "";
        string = this.getMeu_ativo(conta);
        return string;
    }
    public boolean verificaOrdem_0(int id_conta, int id_ativo){
        try{
            int count = 0;
            for (OrdemDAO obj : this.Ofertas_ORDEM0) {
                if(obj != null && obj.getAtivo().getId() == id_ativo && id_conta == obj.getConta().id)  {
                    count++;
                }
            }
            if (count == 1) {   
                return true;
            }
            return false;
        }
        catch (Exception err ){
            return false;
        }
    }
    public boolean setOfertas_ORDEM_0(OrdemDAO Ofertas_ORDEM_0) {
        try{
            int count = 0;
            for (OrdemDAO obj : this.Ofertas_ORDEM0) {
                if(obj == null) {
                    this.Ofertas_ORDEM0[count] = Ofertas_ORDEM_0;
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
}
