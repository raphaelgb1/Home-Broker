/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.crudController;
import dao.clienteDAO;
import java.util.Scanner;

/**
 *
 * @author rapha
 */
public class HomeBroker {

    public static void main(String[] args) {
        crudController clienteController = new crudController();
        clienteDAO clienteAux = new clienteDAO();
        int op = 0;
        int opUpdate = 0;
        int id = 1;
        String aux = "";
        Scanner scan = new Scanner(System.in);
        
        clienteAux.newData(id, "nome", "endereco", "cpf", "phone", "usuario", "senha", true);
        clienteController.insert(clienteAux);

                
        aux += "1 - Adicionar Usuario\n";
        aux += "2 - Editar Usuario\n";
        aux += "3 - Mostrar Cadastros";
        do{
            System.out.println("Digite uma opcao");
            System.out.println(aux);
            op =  Integer.parseInt(scan.nextLine());
            switch (op){
                case 1:
                    id++;
                    System.out.println("Digite seu nome");
                    String name = scan.nextLine();
                    System.out.println("Escolha um usuário");
                    String user = scan.nextLine();
                    System.out.println("Escolha uma senha");
                    String pass = scan.nextLine();
                    System.out.println("Digite Seu Endereço: ");
                    String adress = scan.nextLine();
                    System.out.println("Digite Seu CPF");
                    String cpf = scan.nextLine();
                    System.out.println("Digite Seu Telefone");
                    String phone = scan.nextLine();
                    clienteDAO newClient = new clienteDAO();
                    newClient.newData(id, name, adress, cpf, phone, user, pass, false);
                    clienteController.insert(newClient);
                    System.out.println("Criado");
                    break;
                    
                case 2:
                    do {
                        System.out.println("Digite o id do Usuário");
                        int idUpdate = scan.nextInt();
                        int indice = clienteController.returnId(idUpdate);
                        if(indice != -1) {
                            System.out.println("Digite o nome");
                            scan.nextLine();
                            String nameUpdate = scan.nextLine();
                            System.out.println("Escolha o usuário");
                            String userUpdate = scan.nextLine();
                            System.out.println("Escolha a senha");
                            String passUpdate = scan.nextLine();
                            System.out.println("Digite o Endereço: ");
                            String adressUpdate = scan.nextLine();
                            System.out.println("Digite o CPF");
                            String cpfUpdate = scan.nextLine();
                            System.out.println("Digite o Telefone");
                            String phoneUpdate = scan.nextLine();
                            clienteDAO newObj = new clienteDAO();
                            newObj.newData(idUpdate, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false);
                            if(clienteController.update(newObj, indice)){
                               System.out.println("Atualizado");
                            } else {
                                System.out.println("Ocorreu um erro durante a atualização"); 
                            }
                            opUpdate = 1;
                        } else {
                            System.out.println("Usuário não encontrado");
                            System.out.println("\nDeseja Voltar? \n1 - Sim \n2 - Não");
                            opUpdate = scan.nextInt();
                        }
                    } while(opUpdate != 1);
                    
                    break;
                    
                case 3:
                    clienteDAO[] view = new clienteDAO[5];
                    view = clienteController.acessarVetor();
                    for (clienteDAO dAO : view) {
                        if(dAO != null){
                            System.out.println(dAO.nome);
                            System.out.println(dAO.id);
                        }                      
                    }
                    break;
            } 
            
        } while (op != 6);
    }
}
