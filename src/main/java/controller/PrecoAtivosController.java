package controller;

import java.util.Set;

import dao.AtivosDAO;
import dao.BookDAO;
import dao.ContaDAO;
import dao.OrdemDAO;
import utils.UtilsObj;

public class PrecoAtivosController {
    UtilsObj util = new UtilsObj();

    public void atualizaTaxa (BookDAO book) {
        for (AtivosDAO element : book.ativo) {
            element.taxa = util.calculaJuros();
        }
    }

    public void atualizaPrecoAtivos (BookDAO book) {
        this.atualizaTaxa(book);
        for (AtivosDAO element : book.ativo) {
            double valorizacao = (element.taxa < 0) ? (100-(element.taxa*-1))/100 
            : (element.taxa == 0) ? 99.999 : (element.taxa+100)/100;
            element.preço_inicial *= valorizacao;
        }
    }

}
