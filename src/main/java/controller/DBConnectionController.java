package controller;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import dao.DBConnectionDAO;

public class DBConnectionController {

    private DBConnectionDAO dbConnectionDAO = new DBConnectionDAO();

    private DBConnectionDAO getDbConn() {
        return this.dbConnectionDAO;
    }

    //USAR SOMENTO PARA SELECT
    public  ResultSet execute (String sql) {
        DBConnectionDAO dbConnectionDAO = getDbConn();
        ResultSet resultDB = dbConnectionDAO.execute(sql);
        return resultDB;
    }

    //USAR SOMENTE PARA INSERTS ESPECÍFICOS
    public int executeInsert (String sql) {
        DBConnectionDAO dbConnectionDAO = getDbConn();
        int resultDB = dbConnectionDAO.update(sql);
        return resultDB;
    }

    //UPDATE RETORNA A QUANTIDADE DE COLUNAS AFETADAS
    public  int update (String tableName, String key, int id, Map colunas) {
        try {
            String sql = "UPDATE " + tableName + " SET ";
            Iterator it = colunas.entrySet().iterator();
            Map.Entry elements = null;
            while(it.hasNext()){ 
                elements = (Map.Entry)it.next();
                sql += " " + elements.getKey() + " = " + " '" + elements.getValue() + "' ";
                if (it.hasNext()) sql += ", ";
            }
            sql += " WHERE " + key + " = " + id;
            DBConnectionDAO dbConnectionDAO = getDbConn();
            int result = dbConnectionDAO.update(sql);
            colunas.clear();
            return result;   
        } catch (Exception err) {
            throw err;
        }
    }

    //DELETE RETORNA A QUANTIDADE DE COLUNAS AFETADAS
    public int delete (String tableName, String key, int id) {
        try {
            String sql = "DELETE FROM " + tableName + " WHERE " + key + " = " + id;
            DBConnectionDAO dbConnectionDAO = getDbConn();
            int result = dbConnectionDAO.update(sql);
            return result;
        } catch (Exception err) {
            throw err;
        }
    }

    //INSERT RETORNA O ID DO ELEMENTO INSERIDO
    public int insert (String tableName, Map colunas) {
        try {
            String sql = "INSERT INTO " + tableName + " (";
            Iterator itColunas = colunas.entrySet().iterator();
            Iterator itValues = colunas.entrySet().iterator();
            while(itColunas.hasNext()){ 
                Map.Entry elements = (Map.Entry)itColunas.next();
                sql += elements.getKey();
                if (itColunas.hasNext()) sql += ", ";
            }
            sql += ") VALUES (";

            while(itValues.hasNext()){ 
                Map.Entry elements = (Map.Entry)itValues.next();
                sql += "'" + elements.getValue() + "'";
                if (itValues.hasNext()) sql += ", ";
            }
            sql += ")";
            DBConnectionDAO dbConnectionDAO = getDbConn();
            int result = dbConnectionDAO.update(sql);
            colunas.clear();
            return result;     
        } catch (Exception err) {
            throw err;
        }
    }

    public void close () {
        dbConnectionDAO.close();
    }
}
