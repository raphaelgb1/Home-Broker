/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.ClienteController;
import dao.ClienteDAO;
import java.util.Scanner;
import javax.swing.JOptionPane;
import utils.UtilsObj;

/**
 *
 * @author rapha
 */
public class HomeBroker {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ClienteController clienteController = new ClienteController();
        ClienteDAO[] vetorCliente = new ClienteDAO[5];
        UtilsObj utils = new UtilsObj();
        
        int op = 0;
        int opDelete = 0;
        int opUpdate = 0;
        int id = 1;
        String menu = "1 - Adicionar Usuario\n2 - Editar Usuario\n3 - Mostrar Cadastros\n4 - Excluir Usuário\n\nDigite uma opção";
             
        ClienteDAO clienteAux = new ClienteDAO();
        clienteAux.newData(id, "nome", "endereco", "cpf", "phone", "usuario", "senha", true);
        clienteController.insert(clienteAux, vetorCliente);
        
        do{
            op =  Integer.parseInt(JOptionPane.showInputDialog(menu));
            switch (op){
                case 1:
                    
                    if(utils.vetorLength(vetorCliente) != 5) {
                        id++;
                        String name   = JOptionPane.showInputDialog("Digite um nome");
                        String adress = JOptionPane.showInputDialog("Escolha o usuário");
                        String cpf    = JOptionPane.showInputDialog("Escolha uma senha");
                        String phone  = JOptionPane.showInputDialog("Digite o Endereço");
                        String user   = JOptionPane.showInputDialog("Digite o CPF");
                        String pass   = JOptionPane.showInputDialog("Digite o Telefone");
                        ClienteDAO newClient = new ClienteDAO();
                        newClient.newData(id, name, adress, cpf, phone, user, pass, false);
                        int resultado = clienteController.insert(newClient, vetorCliente);
                        if (resultado > 0) {
                            JOptionPane.showMessageDialog (null, "Usuário Criado");
                        }  else {
                            JOptionPane.showMessageDialog (null, "Ocorreu um erro durante a criação");
                        }
                    } else {
                        JOptionPane.showMessageDialog (null, "Máximo de Usuários Cadastrados, exclua um usuário");
                    }
                    
                    break;
                    
                case 2:
                    do {
                        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog("Digite o Id do Usuário"));
                        int indice = clienteController.returnId(idUpdate, vetorCliente);
                        if(indice >= 0) {
                            String nameUpdate   = JOptionPane.showInputDialog("Digite um nome");
                            String userUpdate   = JOptionPane.showInputDialog("Escolha o usuário");
                            String passUpdate   = JOptionPane.showInputDialog("Escolha uma senha");
                            String adressUpdate = JOptionPane.showInputDialog("Digite o Endereço");
                            String cpfUpdate    = JOptionPane.showInputDialog("Digite o CPF");
                            String phoneUpdate  = JOptionPane.showInputDialog("Digite o Telefone");
                            ClienteDAO newObj   = new ClienteDAO();
                            newObj.newData(idUpdate, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false);
                            if(clienteController.update(newObj, vetorCliente, indice)){
                               System.out.println("Atualizado");
                            } else {
                                System.out.println("Ocorreu um erro durante a atualização"); 
                            }
                            opUpdate = 1;
                        } else {
                            if(indice == -1) {
                                System.out.println("Usuário não encontrado");
                                System.out.println("\nDeseja Voltar? \n1 - Sim \n2 - Não");
                                opUpdate = scan.nextInt();
                            } else {
                                System.out.println("Ocorreu um erro durante a atualização"); 
                            }
                        }
                    } while(opUpdate != 1);
                    
                    break;
                    
                case 3:
                    for (ClienteDAO dAO : vetorCliente) {
                        if(dAO != null){
                            System.out.println(dAO.nome);
                            System.out.println(dAO.id);
                        }                      
                    }
                    break;
                    
                case 4:
                    do {
                        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog("Digite o Id do Usuário"));
                        int indice = clienteController.returnId(idUpdate, vetorCliente);
                        if(indice >= 0) {
                            if(clienteController.delete(indice, vetorCliente)){
                               System.out.println("Usuário Excluído");
                            } else {
                                System.out.println("Ocorreu um erro durante a exclusão"); 
                            }
                            opDelete = 1;
                        } else {
                            if(indice == -1) {
                                System.out.println("Usuário não encontrado");
                                System.out.println("\nDeseja Voltar? \n1 - Sim \n2 - Não");
                                opUpdate = scan.nextInt();
                            } else {
                                System.out.println("Ocorreu um erro durante a atualização"); 
                            }
                        }
                    } while(opDelete != 1);
                    break;
            } 
        } while (op != 6);
    }
}
