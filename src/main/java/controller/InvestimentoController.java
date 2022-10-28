package controller;

import dao.InvestimentoDAO;

public class InvestimentoController extends CrudController{

    public InvestimentoDAO returnObjectByConta (int id, InvestimentoDAO[] vetor){
        try {
            for (InvestimentoDAO obj : vetor) {
                if(obj != null) {
                    if(obj.conta == id){
                        return obj;
                    }
                }                      
            }
            return null;
        } catch (Exception err) {
            return null;
        }
    }
}
