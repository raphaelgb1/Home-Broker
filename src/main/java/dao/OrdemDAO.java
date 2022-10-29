package dao;

public class OrdemDAO{
    int id;
    AtivosDAO ativo; //ativo a ser negociado
    ContaDAO conta;  // conta da pessoa
    int tipo_ordem;  // 2 = venda -------------- 1 = compra -------------- 0 = ordem 0
    boolean pendente;
    /**
     * Construtor
     */
    public OrdemDAO(int idOrdem) {
        id = idOrdem;
        this.ativo = new AtivosDAO();
    }

    // gets e sets
    public int getTipo_ordem() {
        return tipo_ordem;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public AtivosDAO getAtivo() {
        return ativo;
    }
    public void setAtivo(AtivosDAO ativo) {
        this.ativo = ativo;
    }
    public void setPendente(boolean pendente) {
        this.pendente = pendente;
    }
    public ContaDAO getConta() {
        return conta;
    }
    public void setConta(ContaDAO conta) {
        this.conta = conta;
    }
    public int istipo_ordem() {
        return tipo_ordem;
    }
    public void settipo_ordem(int tipo_ordem) {
        this.tipo_ordem = tipo_ordem;
    }
}
