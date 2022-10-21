/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.ClienteController;
import controller.ContaController;
import dao.ClienteDAO;
import dao.ContaDAO;
import java.util.Date;
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
        ContaController contaController = new ContaController();
        ClienteDAO[] vetorCliente = new ClienteDAO[5];
        ContaDAO[] vetorConta = new ContaDAO[5];
        UtilsObj utils = new UtilsObj();
        
        int op = 0;
        int opDelete = 0;
        int opUpdate = 0;
        
        int idCliente = 1;
        int idConta = 0;
        String menu = "1 - Adicionar Usuario\n2 - Editar Usuario\n3 - Mostrar Cadastros\n4 - Excluir Usuário\n5 - Conta\n\nDigite uma opção";
        String menuConta = "1 - Visualizar Informações da Conta\n2 - Editar Conta\n3 - Excluir Conta\n4 - Voltar\n\nDigite Uma Opção";
        //CRIAÇÃO DO USUÁRIO ADM
        Date creationAdm       = new Date();    
        ClienteDAO clienteAux = new ClienteDAO();
        clienteAux.newData(idCliente, "adm", "adm", "adm", "adm", "adm", "adm123", true, creationAdm, null);
        clienteController.insert(clienteAux, vetorCliente);
        
        do{
            op =  Integer.parseInt(JOptionPane.showInputDialog(menu));
            switch (op){
                case 1:
                    if(utils.vetorLength(vetorCliente) != 5) {
                        String name   = JOptionPane.showInputDialog("Digite um nome");
                        String adress = JOptionPane.showInputDialog("Escolha o usuário");
                        String cpf    = JOptionPane.showInputDialog("Escolha uma senha");
                        String phone  = JOptionPane.showInputDialog("Digite o Endereço");
                        String user   = JOptionPane.showInputDialog("Digite o CPF");
                        String pass   = JOptionPane.showInputDialog("Digite o Telefone");
                        Date creation = new Date();
                        ClienteDAO newClient = new ClienteDAO();
                        newClient.newData(++idCliente, name, adress, cpf, phone, user, pass, false, creation, null);
                        if (clienteController.insert(newClient, vetorCliente)) {
                            ContaDAO newConta = new ContaDAO();
                            //PROVISÓRIO DEPOSITO DE CLIENTE E BOLSA
                            newConta.newData(++idConta, idCliente, 520000.00, creation, null);
                            if(contaController.insert(newConta, vetorConta)){
                                JOptionPane.showMessageDialog (null, "Usuário e Conta Criados");
                            } else {
                                JOptionPane.showMessageDialog (null, "Ocorreu um erro durante a criação da Conta");
                            }
                        }  else {
                            JOptionPane.showMessageDialog (null, "Ocorreu um erro durante a criação do Usuário");
                        }
                    } else {
                        JOptionPane.showMessageDialog (null, "Máximo de Usuários Cadastrados, exclua um usuário");
                    }
                    
                    break;
                    
                case 2:
                    do {
                        String auxMenuContaView = "";
                        for (ClienteDAO element : vetorCliente) {
                            if(element != null && element.adm == false){
                                auxMenuContaView += "Id: " + element.id + "-  Cliente: " + element.nome + "\n";
                            }                      
                        }
                        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(auxMenuContaView + "\n\nDigite o Id do Usuário"));
                        int indice = clienteController.returnId(idUpdate, vetorCliente);
                        if(indice >= 0) {
                            String nameUpdate   = JOptionPane.showInputDialog("Digite um nome");
                            String userUpdate   = JOptionPane.showInputDialog("Escolha o usuário");
                            String passUpdate   = JOptionPane.showInputDialog("Escolha uma senha");
                            String adressUpdate = JOptionPane.showInputDialog("Digite o Endereço");
                            String cpfUpdate    = JOptionPane.showInputDialog("Digite o CPF");
                            String phoneUpdate  = JOptionPane.showInputDialog("Digite o Telefone");
                            Date update       = new Date();
                            ClienteDAO newObj   = new ClienteDAO();
                            newObj.newData(idUpdate, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false, null, update);
                            if(clienteController.update(newObj, vetorCliente, indice)){
                               System.out.println("Atualizado");
                            } else {
                                System.out.println("Ocorreu um erro durante a atualização"); 
                            }
                            opUpdate = 1;
                        } else {
                            if(indice == -1) {
                                JOptionPane.showMessageDialog(null, "Usuário não encontrado");
                            } else {
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a atualização"); 
                            }
                        }
                    } while(opUpdate != 1);
                    
                    break;
                    
                case 3:
                    for (ClienteDAO element : vetorCliente) {
                        if(element != null){
                            System.out.println(element.nome);
                            System.out.println(element.id);
                        }                      
                    }
                    break;
                    
                case 4:
                    do {
                        int idDelete = Integer.parseInt(JOptionPane.showInputDialog("Digite o Id do Usuário"));
                        int indice = clienteController.returnId(idDelete, vetorCliente);
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
                    
                 case 5:
                    int opConta = 0;
                    do {
                        opConta = Integer.parseInt(JOptionPane.showInputDialog(menuConta));
                        switch (opConta){
                            case 1:
                                String auxMenuContaView = "";
                                for (ClienteDAO element : vetorCliente) {
                                    if(element != null && element.adm == false){
                                        auxMenuContaView += "Id: " + element.id + "-  Cliente: " + element.nome + "\n";
                                    }                      
                                }
                                int idViewConta = Integer.parseInt(JOptionPane.showInputDialog(auxMenuContaView));
                                ContaDAO contaView = contaController.returnObjectById(idViewConta, vetorConta);
                                ClienteDAO clienteView = clienteController.returnObjectById(contaView.cliente, vetorCliente);
                                String internView = "   Cliente\n" + clienteView.nome + "\nSaldo: " + contaView.saldo + "\nData de Criação: " + contaView.dataCriacao;
                                if(clienteView.dataModificacao != null){
                                    internView += "\nÚltima Modificação: " + clienteView.dataModificacao;
                                }
                                JOptionPane.showMessageDialog(null, internView);
                            break;
                        }
                    } while(opConta != 4);
                    break;
            } 
        } while (op != 6);
    }
}
