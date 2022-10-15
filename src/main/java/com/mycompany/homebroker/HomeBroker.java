/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.crudController;
import dao.clienteDAO;
import java.util.Scanner;
import utils.crudUtils;

/**
 *
 * @author rapha
 */
public class HomeBroker {

    public static void main(String[] args) {
        crudController clienteController = new crudController();
        clienteDAO clienteAux = new clienteDAO();
        crudUtils utils = new crudUtils();
        int indice = 0;
        int op = 0;
        String aux = "";
        Scanner scan = new Scanner(System.in);
        
        clienteAux.newData("nome", "endereco", "cpf", "phone", "usuario", "senha", true);
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
                    
                    clienteAux.newData(name, adress, cpf, phone, user, pass, false);
                    clienteController.insert(clienteAux);
                    System.out.println("Criado");
                    break;
                    
                case 2:
                    System.out.println("Digite seu nome");
                    String nameUpdate = scan.nextLine();
                    System.out.println("Escolha um usuário");
                    String userUpdate = scan.nextLine();
                    System.out.println("Escolha uma senha");
                    String passUpdate = scan.nextLine();
                    System.out.println("Digite Seu Endereço: ");
                    String adressUpdate = scan.nextLine();
                    System.out.println("Digite Seu CPF");
                    String cpfUpdate = scan.nextLine();
                    System.out.println("Digite Seu Telefone");
                    String phoneUpdate = scan.nextLine();
                    clienteDAO newObj = new clienteDAO();
                    newObj.newData(nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false);
                    clienteController.update(clienteAux, newObj);
                    System.out.println("Atualizado");
                    break;
                    
                case 3:
                    clienteDAO[] view = new clienteDAO[5];
                    view = clienteController.acessarVetor();
                    for (clienteDAO dAO : view) {
                        System.out.println(dAO);
                    }
                    break;
            } 
            
        } while (op != 6);
    }
}
