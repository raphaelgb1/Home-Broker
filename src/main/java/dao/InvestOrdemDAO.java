package dao;

import controller.CrudController;

public class InvestOrdemDAO extends CrudController {

    OrdemDAO ordem;

    public void newData(OrdemDAO ordem){
        this.ordem = ordem;
    }

    public OrdemDAO getOrdem(){
        return this.ordem;
    }
    
}
