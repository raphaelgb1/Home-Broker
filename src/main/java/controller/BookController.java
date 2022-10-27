package controller;

import dao.bookDAO;

public class BookController{
    public static bookDAO NewBook(String data) {
        bookDAO book = new bookDAO();
        book.newData(data);
        return book;
    }
    
    // public void Altera_Ordem(int id, AtivosDAO ativo,ContaDAO Conta, String data, bookDAO book, int tipo_ordem){
    //     OrdemDAO nova = new OrdemDAO();
    //     nova.setId(id);
    //     nova.setAtivo(ativo);
    //     nova.setConta(Conta);
    //     nova.settipo_ordem(tipo_ordem);
    // }
}
