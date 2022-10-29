package controller;

import javax.lang.model.element.Element;
import javax.swing.JOptionPane;

import dao.AtivosDAO;
import dao.BookDAO;
import dao.ContaDAO;
import dao.InvestOrdemDAO;
import dao.InvestimentoDAO;
import dao.OrdemDAO;
import utils.UtilsObj;

public class PrecoAtivosController {
    UtilsObj util = new UtilsObj();
    InvestimentoController investimentoController = new InvestimentoController();

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

    public void atualizaTotalInvestido (ContaDAO[] conta, InvestimentoDAO[] vetorInvestimento, BookDAO ativos) {
        for (ContaDAO element : conta) {
            double totalInvestido = 0;
            double lucroPreju = 0;
            if(element != null && element.cliente != 1){
                InvestimentoDAO investimento = investimentoController.returnObjectByConta(element.id, vetorInvestimento);
                for (OrdemDAO elementOrdem : ativos.getOfertas_compra()) {
                    if(elementOrdem != null) {
                        AtivosDAO ativ = elementOrdem.getAtivo();
                        if(elementOrdem.getTipo_ordem() != 2) {
                            totalInvestido += ativ.preço_inicial * ativ.quantidade_excultada;//INVESTIMENTO INICIAL
                            lucroPreju += (ativos.getAtivo(ativ.getId()).preço_inicial * ativ.quantidade_excultada)-totalInvestido;//INVESTIMENTO ATUAL
                        } 

                    }
                }
                investimento.setTotalInvestido(totalInvestido);
                investimento.setLucroPrejuizo(lucroPreju);
            }   
        }
        // for (ContaDAO element : conta) {
        //     double totalInvestido = 0;
        //     double lucroPreju = 0;
        //     if(element != null && element.cliente != 1){
        //         InvestimentoDAO investimento = investimentoController.returnObjectByConta(element.id, vetorInvestimento);
        //         for (InvestOrdemDAO elementOrdem : investimento.ordem) {
        //             if(elementOrdem != null) {
        //                 AtivosDAO ativo = elementOrdem.getOrdem().getAtivo();
        //                 if(elementOrdem.getOrdem().getTipo_ordem() != 2) {
        //                     totalInvestido += ativo.preço_inicial*ativo.Quantidade;//INVESTIMENTO INICIAL
        //                     lucroPreju += (ativo2.preço_inicial*ativo.Quantidade)-totalInvestido;//INVESTIMENTO ATUAL
        //                 } 
        //                 } else {
        //                     totalInvestido -= ativo.preço_inicial*ativo.Quantidade;//INVESTIMENTO INICIAL
        //                     lucroPreju -= (ativo2.preço_inicial*ativo.Quantidade)-totalInvestido;
        //                 }
        //             }
        //         }
        //         investimento.setTotalInvestido(totalInvestido);
        //         investimento.setLucroPrejuizo(lucroPreju);
        //     }   
        // }
        
    }
}
