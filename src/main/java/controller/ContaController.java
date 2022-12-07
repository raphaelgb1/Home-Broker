/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import dao.ContaDAO;

/**
 *
 * @author rapha
 */
public class ContaController extends CrudController {

    DBConnectionController dbConnectionController = new DBConnectionController();

    public Set search() {

        Set<ContaDAO> obj = new LinkedHashSet<>();
        try {
            String sql = "SELECT * FROM CONTA";
            ResultSet result = dbConnectionController.execute(sql);
            while(result.next()) {
                ContaDAO cliente = new ContaDAO();
                cliente.newData(
                      result.getInt("IDCONTA")
                    , result.getInt("IDCLIENTE")
                    , result.getDouble("SALDO")
                    , result.getString("DTCRIACAO")
                    , result.getString("DTMODIFICACAO")
                );
                obj.add(cliente);
            }
            return obj;
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
            throw null;
        }

    }

    public ContaDAO getContaAdm (Set<ContaDAO> vetor) {
        try {
            for (ContaDAO element : vetor) {
                if(element.id == 1) {
                    return element;
                }
            }
            return null;
        } catch (Exception err) {
            throw err;
        }
    }

    public ContaDAO getContaBolsa (Set<ContaDAO> vetor) {
        try {
            for (ContaDAO element : vetor) {
                if(element.id == 2) {
                    return element;
                }
            }
            return null;
        } catch (Exception err) {
            throw err;
        }
    }

    public ContaDAO getContaUser (int id, Set<ContaDAO> vetor) {
        try {
            for (ContaDAO element : vetor) {
                if(element.id == id) {
                    return element;
                }
            }
            return null;
        } catch (Exception err) {
            throw err;
        }
    }
    
    public ContaDAO getContaByCliente (int id, Set<ContaDAO> vetor) {
        try {
            for (ContaDAO obj : vetor) {
                if(obj != null) {
                    if(obj.cliente == id){
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
