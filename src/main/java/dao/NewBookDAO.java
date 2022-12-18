package dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import controller.DBConnectionController;

public class NewBookDAO {
    ArrayList<NewAtivoDAO> Ativos = new ArrayList<NewAtivoDAO>();//so vai guarda os ativos do book de ofertas
    DBConnectionController dbconect = new DBConnectionController();
    NumberFormat number = NumberFormat.getCurrencyInstance();
    SimpleDateFormat formatBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            String sql = "SELECT " 
            + "(CASE "
                + " WHEN (SELECT "
                + " SUM(QUANTEXE)"
                  + " FROM ORDENS"
                    + " WHERE IDCONTA = " + id_conta + " AND " + id_ativo + " = IDATIVO"
                  + " AND TIPOORDEN = 1) IS NULL THEN 0"
                + " ELSE (SELECT "
                  + " SUM(QUANTEXE)"
                  + " FROM ORDENS"
                      + " WHERE IDCONTA = " + id_conta + " AND " + id_ativo + " = IDATIVO"
                  + " AND TIPOORDEN = 1)"
            + " END "
            + " - CASE "
                + " WHEN (SELECT "
                  + " SUM(QUANTEXE)"
                  + " FROM ORDENS"
                     + "  WHERE IDCONTA = " + id_conta + " AND " + id_ativo + " = IDATIVO"
                  + " AND TIPOORDEN = 2) IS NULL THEN 0"
                + " ELSE (SELECT "
                  + " SUM(QUANTEXE)"
                  + " FROM ORDENS"
                      + " WHERE IDCONTA = " + id_conta + " AND " + id_ativo + " = IDATIVO"
                  + " AND TIPOORDEN = 2)"
              + " END) AS QTD"
        + " FROM ORDENS "
        + " WHERE IDCONTA = " + id_conta + " "
        + " AND IDATIVO = " + id_ativo + ";";
            int count = 0;
            ResultSet result  = dbconect.execute(sql);
            if(result.next()){
                count = result.getInt("QTD");
            }
            result.close();
            return count;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw null;
        }
    }
    public String Ativos_book() throws SQLException{
        String extrato = "";
        for (NewAtivoDAO element : this.Ativos) {
            String sql = "SELECT a.IDATIVO, a.TICKER, a.DESCRICAO, o.DATAORDEN, o.VALOR FROM ATIVO a left join ORDENS o on (o.IDATIVO = a.IDATIVO) WHERE o.IDATIVO = " + element.IDATIVO +" ORDER by o.DATAORDEN DESC LIMIT 1;";
            ResultSet result  = dbconect.execute(sql);
            extrato += "Id: " + element.IDATIVO + "\n";
            extrato += "Ticker: " + element.TICKER + "\n";
            extrato += "Empresa: " + element.IDATIVO + "\n";
            extrato += "Ultimo Valor negociado: " + number.format(result.getDouble("VALOR"));
            extrato += "\n------------------------------\n";
            result.close();
        }
        return extrato;
    }
    public boolean Cadastro_Ordem(NewOrdenDAO Ordem) throws SQLException{
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
            verificaOrdem(Ordem, status);

            return true;
        }
        return false;
    }
    public String getMeu_ativo(int id_conta) {//pega quantidade de acões de um ativo
        try{
            String extrato = "";
            for (NewAtivoDAO obj : this.Ativos) {
                int count = 0;
                int cont = 0;
                double media = 0;
                ResultSet result  = dbconect.execute("SELECT a.IDATIVO, a.TICKER , o.VALOR , a.DESCRICAO , o.QUANTEXE FROM ORDENS o left join ATIVO a on a.IDATIVO = o.IDATIVO WHERE " + id_conta + " = o.IDCONTA AND " + obj.IDATIVO + " = o.IDATIVO AND QUANTEXE > 0;");
                if(result.next()){
                    extrato += "Id Ativo: " + result.getInt("IDATIVO") + "\n";
                    extrato += "Ticker: " + result.getString("TICKER") + "\n";
                    extrato += "Empresa: " + result.getString("DESCRICAO") + "\n";
                    while(result.next()){
                        media += result.getInt("VALOR");
                        count += result.getInt("QUANTEXE");
                        cont++;
                    }
                    extrato += "Valor: " + number.format((double)Math.round(media/cont)) + "\n";
                    extrato += "Quantidade: " + count + ";";
                    extrato += "\n------------------------------\n";
                }
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
                ResultSet result  = dbconect.execute("SELECT o.IDATIVO, a.TICKER , o.VALOR , a.DESCRICAO , o.QUANTEXE, o.QUANT , o.TIPOORDEN FROM ORDENS o left join ATIVO a on a.IDATIVO = o.IDATIVO WHERE " + obj.IDATIVO + " = o.IDATIVO AND (o.QUANT - o.QUANTEXE) > 0;");
                while(result.next()){
                    extrato += "Id Ativo: " + result.getInt("IDATIVO") + ",   ";
                    extrato += "Ticker: " + result.getInt("TICKER") + ",   ";
                    extrato += "Empresa: " + result.getInt("DESCRICAO") + ",   ";
                    extrato += "Tipo: " + (result.getInt("TIPOORDEN") == 1 ? "Compra" : "Venda") + ",   ";
                    extrato += "Valor: R$" + result.getInt("VALOR") + ",   ";
                    extrato += "Quantidade: " + (result.getInt("QUANT") - result.getInt("QUANTEXE"))+ ";\n";
                }
            }
            return extrato;
        }
        catch (Exception err ){
            return "";
        }
    }
    private void verificaOrdem(NewOrdenDAO Ordem, int id_Ordem) throws SQLException{
        while(!((Ordem.QUANT - Ordem.QUANTEXE) == 0)){
            String sql = "SELECT IDORDENS, (QUANT - QUANTEXE) as QTD, QUANTEXE  FROM ORDENS WHERE " + (Ordem.TIPOORDEN == 1 ? "2" : "1") + " = TIPOORDEN AND VALOR <= " + Ordem.VALOR + " and (QUANT - QUANTEXE) > 0 ORDER by VALOR ASC LIMIT 1;";
            ResultSet result  = dbconect.execute(sql);
            if (!result.next())
                break;
            int id_sql = result.getInt("IDORDENS");
            int quant = result.getInt("QTD");
            int quantexe = result.getInt("QUANTEXE");
            result.close();
            if ((Ordem.QUANT - Ordem.QUANTEXE) >= quant ) {
                Ordem.QUANTEXE += quant;
                Map <String,String> map = new LinkedHashMap<String,String>();
                map.put("IDORDENS", Integer.toString(id_Ordem));
                map.put("QUANTEXE", Integer.toString(Ordem.QUANTEXE));
                dbconect.update("ORDENS","IDORDENS", id_Ordem, map);
                map.put("IDORDENS", Integer.toString(id_sql));
                map.put("QUANTEXE", Integer.toString(quant));
                dbconect.update("ORDENS","IDORDENS", id_sql, map);
            }else{
                quant = (Ordem.QUANT - Ordem.QUANTEXE) + quantexe;
                Ordem.QUANTEXE = Ordem.QUANT;
                Map <String,String> map = new LinkedHashMap<String,String>();
                map.put("IDORDENS", Integer.toString(id_Ordem));
                map.put("QUANTEXE", Integer.toString(Ordem.QUANTEXE));
                dbconect.update("ORDENS","IDORDENS", id_Ordem, map);
                map.put("IDORDENS", Integer.toString(id_sql));
                map.put("QUANTEXE", Integer.toString(quant));
                dbconect.update("ORDENS","IDORDENS", id_sql, map);
            }
        }
    }
    
}
