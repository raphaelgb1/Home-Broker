/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.ClienteController;
import controller.ContaController;
import dao.ClienteDAO;
import dao.ContaDAO;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        int op = 0;
        int opUser = 0;
        int opView = 0;
        int opDelete = 0;
        int opUpdate = 0;
        int verify = 0;
        
        int idCliente = 1;
        int idConta = 0;
        
        String verifySenha = "";
        String menu = "1 - Adicionar Usuario\n2 - Editar Usuario\n3 - Mostrar Cadastros\n4 - Excluir Usuário\n5 - Conta\n6 - Sair\n\nDigite uma opção";
        String menuConta = "1 - Visualizar Informações da Conta\n2 - Editar Conta\n3 - Excluir Conta\n0 - Voltar\n\nDigite Uma Opção";
        
//CRIAÇÃO DO USUÁRIO ADM
        ClienteDAO clienteAdm = new ClienteDAO();
        clienteAdm.newData(idCliente, "adm", "adm", "adm", "adm", "adm", "adm123", true, format.format(new Date()), null);
        clienteController.insert(clienteAdm, vetorCliente);
        ContaDAO newContaAdm = new ContaDAO();
        newContaAdm.newData(++idConta, clienteAdm.id, 100000.00, format.format(new Date()), null);
        contaController.insert(newContaAdm, vetorConta);
        
//CRIACÃO USUÁRIO PROVISÓRIO PARA TESTES
        idCliente++;
        ClienteDAO clienteUser = new ClienteDAO();
        clienteUser.newData(idCliente, "user", "user", "user", "user", "user", "user", false, format.format(new Date()), null);
        clienteController.insert(clienteUser, vetorCliente);  
        ContaDAO newContaUser = new ContaDAO();
        newContaUser.newData(++idConta, clienteUser.id, 520000.00, format.format(new Date()), null);
        contaController.insert(newContaUser, vetorConta);
        
        
        
        do {
            //AUTENTICAÇÃO DE SESSÃO
            String userName = JOptionPane.showInputDialog("BEM VINDO AO HOME BROKER\n\n0 - Sair\nPara começar, insira seu Usuário");
            if(userName.hashCode() == "0".hashCode()){
                opUser = 1;
                break;
            }
            ClienteDAO user =clienteController.verifyUserName(vetorCliente, userName);
            if(user != null){
                String password = JOptionPane.showInputDialog("Insira sua senha");
                if(user.senha.hashCode() == password.hashCode()){
                    
                    
                    JOptionPane.showMessageDialog(null, "Bem vindo " + user.nome + "!");
                    //MENU DO USUÁRIO ADMINISTRADOR
                    do{
                        op =  Integer.parseInt(JOptionPane.showInputDialog(user.nome + "\n"+menu));
                        switch (op){
                            case 1://ADICIONAR USUÁRIO
                                if(utils.vetorLength(vetorCliente) != 5) {   
                                    String name   = JOptionPane.showInputDialog("Digite um nome");
                                    String login = JOptionPane.showInputDialog("Escolha o usuário");
                                    String pass    = JOptionPane.showInputDialog("Escolha uma senha");
                                    String adress  = JOptionPane.showInputDialog("Digite o Endereço");
                                    String cpf   = JOptionPane.showInputDialog("Digite o CPF");
                                    String phone   = JOptionPane.showInputDialog("Digite o Telefone");
                                    String creation = format.format(new Date());
                                    ClienteDAO newClient = new ClienteDAO();
                                    newClient.newData(++idCliente, name, adress, cpf, phone, login, pass, false, creation, null);
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

                            case 2://EDITAR USUÁRIO
                                do {
                                    opUpdate = 0;
                                    if(utils.verifyObjectIsVoid(vetorCliente) == 0){
                                        String auxMenuClienteUpdate = "";
                                        for (ClienteDAO element : vetorCliente) {
                                            if(element != null){
                                                auxMenuClienteUpdate += "Cliente: " + element.nome + " - Id: " + element.id + "\n";
                                            }                      
                                        }
                                        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteUpdate + "\n0 - Voltar\nDigite o Id do Usuário"));
                                        if (idUpdate == 0) {
                                            opUpdate = 1;
                                            break;
                                        }
                                        ClienteDAO clienteUdpdate = clienteController.returnObjectById(idUpdate, vetorCliente);
                                        if(clienteUdpdate != null) {                    
                                            if(clienteUdpdate.id >= 0) {
                                                verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                                if(verifySenha.hashCode() == vetorCliente[0].senha.hashCode()){//VERIFICAR SENHA
                                                    int indice = clienteController.returnIndex(clienteUdpdate.id, vetorCliente); 
                                                    String nameUpdate   = JOptionPane.showInputDialog("Nome: "     + clienteUdpdate.nome + "\n\nDigite novo Nome");
                                                    String userUpdate   = JOptionPane.showInputDialog("Usuário: "  + clienteUdpdate.nome +"\n\nDigite novo Usuário");
                                                    String passUpdate   = JOptionPane.showInputDialog("Senha: "    + clienteUdpdate.nome +"\n\nEscolha nova Senha");
                                                    String adressUpdate = JOptionPane.showInputDialog("Endereço: " + clienteUdpdate.nome +"\n\nDigite novo Endereço");
                                                    String cpfUpdate    = JOptionPane.showInputDialog("CPF: "      + clienteUdpdate.nome +"\n\nDigite novo CPF");
                                                    String phoneUpdate  = JOptionPane.showInputDialog("Telefone: " + clienteUdpdate.nome +"\n\nDigite novo Telefone");
                                                    String update         = format.format(new Date());
                                                    ClienteDAO newObj   = new ClienteDAO();
                                                    newObj.newData(idUpdate, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false, clienteUdpdate.dataCriacao, update);

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
                                            JOptionPane.showMessageDialog(null,"Nenhum cliente encontrado");
                                        }

                                    } else {
                                            JOptionPane.showMessageDialog(null, "Não há clientes cadastrados");
                                            opUpdate = 1;
                                    }
                                } while(opUpdate != 1);

                                break;

                            case 3://MOSTRAR USUÁRIOS (COM EXECESSÃO DO ADMINISTRADOR)

                                do{

                                    String auxMenuClienteView = "";
                                    verify = clienteController.verifyHaveOnlyAdm(vetorCliente);
                                    if(verify > 1) {

                                        for (ClienteDAO element : vetorCliente) {
                                            if(element != null){
                                                auxMenuClienteView += "Id: " + element.id + " -  Cliente: " + element.nome + "\n";
                                            }                      
                                        }

                                        int idViewCliente = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteView + "\n0 - Voltar\nDigite uma opção"));
                                        if (idViewCliente == 0) {
                                                opView = 1;
                                                break;
                                            }
                                        ClienteDAO clienteView = clienteController.returnObjectById(idViewCliente, vetorCliente);
                                        if(clienteView != null){
                                            String clienteViewText = "Id: "+clienteView.id 
                                                                    +"\nNome: "+clienteView.nome
                                                                    +"\nLogin: "+clienteView.login
                                                                    +"\nSenha: "+clienteView.senha
                                                                    +"\nCPF: "+clienteView.CPF
                                                                    +"\nTelefone: "+clienteView.telefone
                                                                    +"\nData de Criação: "+clienteView.dataCriacao;
                                            clienteViewText += (clienteView.dataModificacao == null) ? "\nData de Modificação: Sem Modificação" 
                                                    : "\nData de Modificação: " + clienteView.dataModificacao;
                                            clienteViewText += (clienteView.adm == false) ? "\nAdministrador: Não" : "\nAdministrador: Sim";
                                            JOptionPane.showMessageDialog(null,clienteViewText);          
                                        } else {
                                            JOptionPane.showMessageDialog(null,"Nenhum cliente encontrado");
                                        }
                                    } else if(verify == 1) {
                                        JOptionPane.showMessageDialog(null,"Não há clientes cadastrados");
                                    } else {
                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao buscar clientes");
                                    }
                                }while(opView != 0);

                                break;

                            case 4://EXCLUIR USUÁRIOS (COM EXECESSÃO DO ADMINISTRADOR)
                                do {
                                    verify = clienteController.verifyHaveOnlyAdm(vetorCliente);
                                    if(verify > 1) {         
                                        String auxMenuClienteDel = "";
                                        for (ClienteDAO element : vetorCliente) {
                                            if(element != null && element.adm == false){
                                                auxMenuClienteDel += "Id: " + element.id + "-  Cliente: " + element.nome + "\n";
                                            }                      
                                        }
                                        int idDelete = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteDel + "\n0 - Voltar\nDigite o Id do Usuário"));
                                        if(idDelete == 1){
                                            JOptionPane.showMessageDialog(null, "Não é possível excluir usuário Administrador");
                                            break;
                                        } else if (idDelete == 0) {
                                            opDelete = 1;
                                            break;
                                        }

                                        int indice = clienteController.returnIndex(idDelete, vetorCliente);
                                        if(indice >= 0) {
                                                verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                                if(verifySenha.hashCode() == vetorCliente[0].senha.hashCode()){
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
                                            } else {
                                                JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a atualização"); 
                                            }
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null,"Não há clientes cadastrados");
                                        opDelete = 1;
                                    }
                                } while(opDelete != 1);
                                break;

                             case 5://MENU DE CONTA
                                int opConta = 0;
                                verify = clienteController.verifyHaveOnlyAdm(vetorCliente);
                                if(verify > 1) {   
                                    String auxMenuContaView = "";
                                    for (ClienteDAO element : vetorCliente) {
                                        if(element != null){
                                            auxMenuContaView += "Id: " + element.id + "-  Cliente: " + element.nome + "\n";
                                        }                      
                                    }
                                    do {
                                        int idViewConta = Integer.parseInt(JOptionPane.showInputDialog(auxMenuContaView + "\n0 - Voltar\nDigite uma opção"));
                                        ClienteDAO clienteViewConta = clienteController.returnObjectById(idViewConta, vetorCliente);
                                        if(idViewConta == 0){
                                                opConta = 4;
                                                break;
                                            } 
                                        if(clienteViewConta != null){
                                                int internConta = 0;
                                                opConta = Integer.parseInt(JOptionPane.showInputDialog(menuConta));
                                                ContaDAO contaView = contaController.returnContaByCliente(clienteViewConta.id, vetorConta);
                                            do {
                                                    switch (opConta){
                                                        case 0:
                                                            internConta = 0;
                                                            break;
                                                        case 1://VISUALIZAR INFORMAÇÕES DA CONTA
                                                            String internView = "Cliente: " + clienteViewConta.nome + "\nSaldo: " + contaView.saldo + "\nData de Criação: " + contaView.dataCriacao;
                                                            internView += (clienteViewConta.dataModificacao != null) ?  "\nÚltima Modificação: " + clienteViewConta.dataModificacao : "";
                                                            JOptionPane.showMessageDialog(null,internView);
                                                        break;

                                                        case 2://EDITAR CONTA
                                                            verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                                            if(verifySenha.hashCode() == vetorCliente[0].senha.hashCode()){
                                                                int updateConta = Integer.parseInt(JOptionPane.showInputDialog("1 - Adicionar Saldo\n2 - Remover Saldo\n\n0 - Voltar\nDigite Uma Opção"));
                                                                do {
                                                                    double movimentConta = 0.0;
                                                                    int indice = 0;
                                                                    switch (updateConta) {
                                                                        case 1:
                                                                            indice = contaController.returnIndex(contaView.id, vetorConta);
                                                                            movimentConta = Double.parseDouble(JOptionPane.showInputDialog("1 - Quanto deseja Adicionar?\n\n0 - Cancelar"));
                                                                            if(movimentConta == 0.0){
                                                                                break;
                                                                            }
                                                                            contaView.saldo += movimentConta;
                                                                            contaView.newData(contaView.id, contaView.cliente, contaView.saldo, contaView.dataCriacao, contaView.dataModificacao);
                                                                            if(contaController.update(contaView, vetorConta, indice)) {
                                                                                JOptionPane.showMessageDialog(null,"Saldo Adicionado");
                                                                            } else {
                                                                                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao adicionar saldo");
                                                                            };
                                                                            break;
                                                                        case 2:
                                                                            indice = contaController.returnIndex(contaView.id, vetorConta);
                                                                            movimentConta = Double.parseDouble(JOptionPane.showInputDialog("1 - Quanto deseja Remover?"));
                                                                            if(movimentConta == 0.0){
                                                                                break;
                                                                            }
                                                                            contaView.saldo -= movimentConta;
                                                                            contaView.newData(contaView.id, contaView.cliente, contaView.saldo, contaView.dataCriacao, contaView.dataModificacao);
                                                                            if(contaController.update(contaView, vetorConta, indice)) {
                                                                                JOptionPane.showMessageDialog(null,"Saldo Removido");
                                                                            } else {
                                                                                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao remover saldo");
                                                                            };
                                                                            break;
                                                                    }
                                                                    updateConta = 0;
                                                                }while(updateConta != 0);
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Senha Inválida");
                                                            }
                                                        break;
                                                    }
                                            } while(internConta != 0);
                                            }  else {
                                                JOptionPane.showMessageDialog(null,"Não foi encontrado nenhum cliente");
                                            }
                                    }while(opConta != 4); 
                                } else if(verify == 1) {
                                    JOptionPane.showMessageDialog(null,"Não há clientes cadastrados");
                                } else {
                                    JOptionPane.showMessageDialog(null,"Ocorreu um erro ao buscar clientes");
                                }
                            break;

                            case 6:
                                op = Integer.parseInt(JOptionPane.showInputDialog("Você Quer Sair?\n 1 - Sim\n 2 - Não"));
                                op = (op == 1) ? 6 : 1;
                            break;
                        } 
                    } while (op != 6);
                    JOptionPane.showMessageDialog(null, "Sessão Encerrada");
                    opUser = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Senha Inválida");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não foi encontrado");
            }
        } while(opUser != 1);
    }
}
