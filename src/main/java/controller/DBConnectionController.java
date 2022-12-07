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

    public  ResultSet execute (String sql) {
        DBConnectionDAO dbConnectionDAO = getDbConn();
        ResultSet resultDB = dbConnectionDAO.execute(sql);
        return resultDB;
    }

    public int executeInsert (String sql) {
        DBConnectionDAO dbConnectionDAO = getDbConn();
        int resultDB = dbConnectionDAO.update(sql);
        return resultDB;
    }

    // public Set search(Set<Object> obj2) {

    //     Set<Object> obj = new LinkedHashSet<>();
    //     try {
    //         String sql = "SELECT * FROM CLIENTE";
    //         ResultSet result = dbConnectionController.execute(sql);;
    //         Iterator<String> it = 
    //         while(result.next()) {
    //             Object cliente = new Object();
    //             cliente.newData(
    //                   result.getInt(   "IDCLIENTE")
    //                 , result.getString("NOME")
    //                 , result.getString("ENDERECO")
    //                 , result.getString("CPF")
    //                 , result.getString("TELEFONE")
    //                 , result.getString("LOGIN")
    //                 , result.getString("SENHA")
    //                 , result.getBoolean("ADM")
    //                 , result.getString("DATACRIACAO")
    //                 , result.getString("DATAMODIFICACAO")
    //             );
    //             obj.add(cliente);
    //         }
    //         return obj;
    //     } catch (SQLException err) {
    //         JOptionPane.showMessageDialog(null, err.getMessage());
    //         throw null;
    //     }

    // }
    
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
