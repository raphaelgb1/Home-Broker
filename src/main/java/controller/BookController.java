package controller;

import dao.BookDAO;

public class BookController{
    public static BookDAO NewBook(String data) {
        BookDAO book = new BookDAO();
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
