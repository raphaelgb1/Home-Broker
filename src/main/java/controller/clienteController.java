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

import dao.ClienteDAO;
import dao.ContaDAO;

/**
 *
 * @author rapha
 */
public class ClienteController {

    DBConnectionController dbConnectionController = new DBConnectionController();

    public Set search() {

        Set<ClienteDAO> obj = new LinkedHashSet<>();
        try {
            String sql = "SELECT * FROM CLIENTE";
            ResultSet result = dbConnectionController.execute(sql);
            while(result.next()) {
                ClienteDAO cliente = new ClienteDAO();
                cliente.newData(
                      result.getInt(   "IDCLIENTE")
                    , result.getString("NOME")
                    , result.getString("ENDERECO")
                    , result.getString("CPF")
                    , result.getString("TELEFONE")
                    , result.getString("LOGIN")
                    , result.getString("SENHA")
                    , result.getBoolean("ADM")
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
    
    // public int returnIndex (int id, Set<ClienteDAO> vetor){
    //     try {
    //         for (ClienteDAO obj : vetor) {
    //                 if(element.id == id){
    //                     return element.;
    //                 }          
    //         }
    //         return -1;
    //     } catch (Exception err) {
    //         return -2;
    //     }
    // }
    
    public ClienteDAO returnObjectById (int id, Set<ClienteDAO> vetor){
        try {
            for (ClienteDAO obj : vetor) {
                if(obj != null) {
                    if(obj.id == id){
                        return obj;
                    }
                }                      
            }
            return null;
        } catch (Exception err) {
            return null;
        }
    }

    public boolean verify (int id, Set<ClienteDAO> vetor){
        try {
            for (ClienteDAO obj : vetor) {
                if(obj != null) {
                    if(obj.id == id){
                        return true;
                    }
                }                      
            }
            return false;
        } catch (Exception err) {
            return false;
        }
    }
    
    public int verifyObjectIsVoid (Set<ClienteDAO> vetor) {
        try {
             for (ClienteDAO obj : vetor) {
                if(obj != null) {
                    return 0;
                }                      
            }
            return 1;
        } catch (Exception err) {
            return -1;
        }
    }
    
    public int verifyHaveOnlyAdm (Set<ClienteDAO> vetor) {//VERIFICAR SE HÁ 
        try {
            int count = 0;
             for (ClienteDAO obj : vetor) {
                if(obj != null) {
                    count++;
                }                      
            }
            return count;
        } catch (Exception err) {
            return -1;
        }
    }
    
    public ClienteDAO verifyUserName (Set<ClienteDAO> vetor, String name) {
        try {
            for (ClienteDAO element : vetor) {
                if(element.login.hashCode() == name.hashCode()){
                    return element;
                }
            }
            return null;
        } catch (Exception err) {
            return null;
        }
    }

    public ClienteDAO getClienteByConta (int id, Set<ContaDAO> vetorConta, Set<ClienteDAO> vetorCliente) {
        try {
            for (ContaDAO conta : vetorConta) {
                if(conta.id == id) {
                    for (ClienteDAO cliente : vetorCliente) {
                        if(conta.cliente == cliente.id) {
                            return cliente;
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
