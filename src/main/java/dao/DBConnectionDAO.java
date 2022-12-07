package dao;

import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionDAO {
    private static Connection open() {
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/rapha/OneDrive/Análise e Desenvolvimento de Sistemas/3° Período/Programação Orientada a Objetos/Trabalho 2/Home-Broker/src/database/homeBroker.sqlite");
            return connection;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }


    public ResultSet execute (String query) {
        try {
            Connection connection = open();
            Statement statement = connection.createStatement();
            ResultSet result  = statement.executeQuery(query);
            return result;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw null;
        }
    }

    public int update (String query) {

        Pattern word = Pattern.compile("INSERT", Pattern.CASE_INSENSITIVE);
        Matcher verify = word.matcher(query);
        boolean isInsert = verify.find();
        int result = 0;
        Statement statement = null;
        try {
            Connection connection = open();
            statement = connection.createStatement();
            result  = statement.executeUpdate(query);
            if(isInsert && result > 0){
                ResultSet aux = statement.executeQuery("SELECT LAST_INSERT_ROWID() AS ID");
                while(aux.next())
                    result = aux.getInt("ID");
            } 
            
            return result;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw null;
        }
    }
    
}
