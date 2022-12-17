package dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import controller.DBConnectionController;

public class NewBookDAO {
    ArrayList<NewAtivoDAO> Ativos = new ArrayList<NewAtivoDAO>();//so vai guarda os ativos do book de ofertas
    DBConnectionController dbconect = new DBConnectionController();
    public NewBookDAO() {
        try {
            String sql = "SELECT * FROM ATIVO;";
            ResultSet result = dbconect.execute(sql);
            while(result.next()){
                NewAtivoDAO Ativo = new NewAtivoDAO();
                Ativo.IDATIVO = result.getInt("IDATIVO");
                Ativo.TICKER = result.getString("TICKER");
                Ativo.DESCRICAO = result.getString("DESCRICAO");
                this.Ativos.add(Ativo);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw null;
        }
    }
    public boolean verificaOrdem_0(int id_conta, int id_ativo){
        try {
            int idorden = 0;
            ResultSet result  = dbconect.execute("SELECT IDORDENS  FROM ORDENS o WHERE " + id_conta + " = IDCONTA AND " + id_ativo + " = IDATIVO AND TIPOORDEN = 0;");
            result.next();
            idorden = result.getInt("IDORDENS");
            if (idorden != 0) {
                return false;
            }
            return true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw null;
        }
    }
    public double get_quantidadeordenscompra(int id_conta, int id_ativo){
        try {
            int count = 0;
            ResultSet result  = dbconect.execute("SELECT QUANT, QUANTEXE  FROM ORDENS o WHERE " + id_conta + " = IDCONTA AND " + id_ativo + " = IDATIVO AND QUANTEXE > 0;");
            while(result.next()){
                count += result.getInt("QUANT") - result.getInt("QUANTEXE");
            }
            return count;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw null;
        }
    }
    public String Ativos_book(){
        String extrato = "";
        for (NewAtivoDAO element : this.Ativos) {
            extrato += "Id: " + element.IDATIVO + "\n";
            extrato += "Ticker: " + element.TICKER + "\n";
            extrato += "Empresa: " + element.IDATIVO + "\n";
            extrato += "\n------------------------------\n";
        }
        return extrato;
    }
    public boolean Cadastro_Ordem(NewOrdenDAO Ordem){
        Map <String,String> map = new LinkedHashMap<String,String>();
        map.put("IDATIVO", Integer.toString(Ordem.IDATIVO));
        map.put("IDCONTA", Integer.toString(Ordem.IDCONTA));
        map.put("TIPOORDEN", Integer.toString(Ordem.TIPOORDEN));
        map.put("QUANT", Double.toString(Ordem.QUANT));
        map.put("QUANTEXE", Double.toString(Ordem.QUANTEXE));
        map.put("VALOR", Double.toString(Ordem.VALOR));
        map.put("DATAORDEN", Ordem.DATAORDEN);
        int status = 0;
        status = dbconect.insert("ORDENS",map);
        if(status > 0){
            return true;
        }
        return false;
    }
    public String getMeu_ativo(int id_conta) {//pega quantidade de acões de um ativo
        try{
            String extrato = "";
            for (NewAtivoDAO obj : this.Ativos) {
                double count = 0;
                double cont = 0;
                double media = 0;
                ResultSet result  = dbconect.execute("SELECT a.IDATIVO, a.TICKER , o.VALOR , a.DESCRICAO , o.QUANTEXE  FROM ATIVO a right join ORDENS o on a.IDATIVO = o.IDATIVO WHERE " + id_conta + " = o.IDCONTA AND " + obj.IDATIVO + " = o.IDATIVO AND QUANTEXE > 0;");
                extrato += "Id Ativo: " + result.getInt("a.IDATIVO") + ",   ";
                extrato += "Ticker: " + result.getInt("a.TICKER") + ",   ";
                extrato += "Empresa: " + result.getInt("a.DESCRICAO") + ",   ";
                while(result.next()){
                    media += result.getInt("o.VALOR");
                    count += result.getInt("o.QUANTEXE");
                    cont++;
                }
                extrato += "Valor: R$" + (double)Math.round(media/cont) + ",   ";
                extrato += "Quantidade: " + count + ";\n";
            }
            return extrato;
        }
        catch (Exception err ){
            return "";
        }
    }
    public String getBook_ativo() {//mostra ativos na bolsa
        try{
            String extrato = "";
            for (NewAtivoDAO obj : this.Ativos) {
                ResultSet result  = dbconect.execute("SELECT a.IDATIVO, a.TICKER , o.VALOR , a.DESCRICAO , o.QUANTEXE  FROM ATIVO a right join ORDENS o on a.IDATIVO = o.IDATIVO WHERE " + obj.IDATIVO + " = o.IDATIVO AND (o.QUANT - o.QUANTEXE) > 0;");
                while(result.next()){
                extrato += "Id Ativo: " + result.getInt("a.IDATIVO") + ",   ";
                extrato += "Ticker: " + result.getInt("a.TICKER") + ",   ";
                extrato += "Empresa: " + result.getInt("a.DESCRICAO") + ",   ";
                extrato += "Valor: R$" + result.getInt("o.VALOR") + ",   ";
                extrato += "Quantidade: " + result.getInt("o.QUANTEXE") + ";\n";
                }
            }
            return extrato;
        }
        catch (Exception err ){
            return "";
        }
    }
    
}
