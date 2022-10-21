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
        String menu = "1 - Adicionar Usuario\n2 - Editar Usuario\n3 - Mostrar Cadastros\n4 - Excluir Usuário\n5 - Conta\n6 - Sair\n\nDigite uma opção";
        String menuConta = "1 - Visualizar Informações da Conta\n2 - Editar Conta\n3 - Excluir Conta\n4 - Voltar\n\nDigite Uma Opção";
        //CRIAÇÃO DO USUÁRIO ADM
        Date creationAdm       = new Date();    
        ClienteDAO clienteAux = new ClienteDAO();
        clienteAux.newData(idCliente, "adm", "adm", "adm", "adm", "adm", "adm123", true, creationAdm, null);
        clienteController.insert(clienteAux, vetorCliente);
        
        //MENU DO USUÁRIO ADMINISTRADOR
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
                        if(clienteController.verifyObjectIsVoid(vetorCliente) == 0){
                            String auxMenuClienteUpdate = "";
                            for (ClienteDAO element : vetorCliente) {
                                if(element != null){
                                    auxMenuClienteUpdate += "Cliente: " + element.nome + " - Id: " + element.id;
                                }                      
                            }
                            int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteUpdate + "\n\nDigite o Id do Usuário"));
                            ClienteDAO clienteUdpdate = clienteController.returnObjectById(idUpdate, vetorCliente);
                            int indice = clienteController.returnIndex(idUpdate, vetorCliente);
                            if(clienteUdpdate.id >= 0) {
                                String verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                if(verifySenha.equals(vetorCliente[0].senha)){
                                    String nameUpdate   = JOptionPane.showInputDialog("Nome: "     + clienteUdpdate.nome + "\n\nDigite novo Nome");
                                    String userUpdate   = JOptionPane.showInputDialog("Usuário: "  + clienteUdpdate.nome +"\n\nDigite novo Usuário");
                                    String passUpdate   = JOptionPane.showInputDialog("Senha: "    + clienteUdpdate.nome +"\n\nEscolha nova Senha");
                                    String adressUpdate = JOptionPane.showInputDialog("Endereço: " + clienteUdpdate.nome +"\n\nDigite novo Endereço");
                                    String cpfUpdate    = JOptionPane.showInputDialog("CPF: "      + clienteUdpdate.nome +"\n\nDigite novo CPF");
                                    String phoneUpdate  = JOptionPane.showInputDialog("Telefone: " + clienteUdpdate.nome +"\n\nDigite novo Telefone");
                                    Date update         = new Date();
                                    ClienteDAO newObj   = new ClienteDAO();
                                    newObj.newData(idUpdate, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false, null, update);
                                    if(clienteController.update(newObj, vetorCliente, indice)){
                                       JOptionPane.showMessageDialog(null,"Atualizado");
                                    } else {
                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a atualização"); 
                                    }
                                    opUpdate = 1;
                                }  else {
                                    JOptionPane.showMessageDialog(null,"Senha Inválida");
                                }
                            }
                        
                        } else {
                                JOptionPane.showMessageDialog(null, "Não há clientes cadastrados");
                                opUpdate = 1;
                        }
                    } while(opUpdate != 1);
                    
                    break;
                    
                case 3:
                    String auxMenuClienteView = "";
                        for (ClienteDAO element : vetorCliente) {
                            if(element != null && element.adm == false){
                                auxMenuClienteView += "Id: " + element.id + "-  Cliente: " + element.nome + "\n";
                            }                      
                        }
                    int idViewCliente = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteView));
                    ClienteDAO clienteView = clienteController.returnObjectById(idViewCliente, vetorCliente);
                    JOptionPane.showMessageDialog(null,"Id: "+clienteView.id +"Nome: "+clienteView.nome);

                    break;
                    
                case 4:
                    do {
                        int idDelete = Integer.parseInt(JOptionPane.showInputDialog("Digite o Id do Usuário"));
                        int indice = clienteController.returnIndex(idDelete, vetorCliente);
                        if(indice >= 0) {
                            String verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                if(verifySenha.equals(vetorCliente[0].senha)){
                                    if(clienteController.delete(indice, vetorCliente)){
                                       JOptionPane.showMessageDialog(null,"Usuário Excluído");
                                    } else {
                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a exclusão"); 
                                    }
                                    opDelete = 1;
                                } else {
                                    JOptionPane.showMessageDialog(null,"Senha Inválida");
                                }
                        } else {
                            if(indice == -1) {
                                JOptionPane.showMessageDialog(null,"Usuário não encontrado");
                                opUpdate = scan.nextInt();
                            } else {
                                JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a atualização"); 
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
                                ClienteDAO clienteViewConta = clienteController.returnObjectById(contaView.cliente, vetorCliente);
                                String internView = "   Cliente\n" + clienteViewConta.nome + "\nSaldo: " + contaView.saldo + "\nData de Criação: " + contaView.dataCriacao;
                                internView += (clienteViewConta.dataModificacao != null) ?  "\nÚltima Modificação: " + clienteViewConta.dataModificacao : "";
                                
                            break;
                        }
                    } while(opConta != 4);
                    break;
            } 
        } while (op != 6);
        JOptionPane.showMessageDialog(null, "Usuário Deslogado");
    }
}
