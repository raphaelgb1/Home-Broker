package dao;


public class AtivosDAO {
    // id, nome da empresa, ticker, total de ativos (cotas/acoes/...), preço inicial,  data criação e data modificação.
    /* --------------------------------------------------------------- */
    public int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    /* --------------------------------------------------------------- */
    public String empresa;
    public String getEmpresa() {
        return empresa;
    }
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    /* --------------------------------------------------------------- */
    public String ticker;
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    
    /* --------------------------------------------------------------- */
    public int total_de_ativos;
    public int getTotal_de_ativos() {
        return total_de_ativos;
    }
    public void setTotal_de_ativos(int total_de_ativos) {
        this.total_de_ativos = total_de_ativos;
    }
    
    /* --------------------------------------------------------------- */
    public double preço_inicial;
    public double getPreço_inicial() {
        return preço_inicial;
    }
    public void setPreço_inicial(double preço_inicial) {
        this.preço_inicial = preço_inicial;
    }
    
    /* --------------------------------------------------------------- */
    public String dataCriacao;
    public String getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    /* --------------------------------------------------------------- */
    public String dataModificacao;
    public String getDataModificacao() {
        return dataModificacao;
    }
    public void setDataModificacao(String dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    /* --------------------------------------------------------------- */
    public int Quantidade;
    public int getQuantidade() {
        return Quantidade;
    }
    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    /* --------------------------------------------------------------- */
    public Double ultimo_valor;
    public Double getUltimo_valor() {
        return ultimo_valor;
    }
    public void setUltimo_valor(Double ultimo_valor) {
        this.ultimo_valor = ultimo_valor;
    }

    public void newData(int id, String empresa, String ticker,int total_de_ativos,
                        double preço_inicial,String dataCriacao,String dataModificacao) {
        this.setId(id);
        this.setEmpresa(empresa);
        this.setTicker(ticker);
        this.setTotal_de_ativos(total_de_ativos);
        this.setPreço_inicial(preço_inicial);
        this.setDataCriacao(dataCriacao);
        this.setDataModificacao(dataModificacao);
    }
    public AtivosDAO ativosBook(String data, int id){

        // ativo - 1
        if (id == 1) {
            this.setId(id);
            this.setTicker("PETR4");
            this.setEmpresa("Petrobras");
            this.setPreço_inicial(40.00);
            this.setDataCriacao(data);
            this.setDataModificacao(data);
            this.setQuantidade(1000000);
            this.setUltimo_valor(40.00);
        }
        // ativo - 2
        if (id == 2) {
            this.setId(id);
            this.setTicker("BBDC4");
            this.setEmpresa("Bradesco");
            this.setPreço_inicial(50.00);
            this.setDataCriacao(data);
            this.setDataModificacao(data);
            this.setQuantidade(1000000);
            this.setUltimo_valor(50.00);
        }
        // ativo - 3
        if (id == 3) {
            this.setId(id);
            this.setTicker("GGBR4");
            this.setEmpresa("Gerdau");
            this.setPreço_inicial(10.00);
            this.setDataCriacao(data);
            this.setDataModificacao(data);
            this.setQuantidade(1000000);
            this.setUltimo_valor(10.00);
        }
        return this;
    }
}
