/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.ClienteController;
import controller.CobrancaDeTaxa;
import controller.ContaController;
import controller.OperacoesContaController;
import dao.AtivosDAO;
import dao.ClienteDAO;
import dao.ContaDAO;
import dao.OperacoesContaDAO;
import dao.OrdemDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

// import javax.lang.model.util.ElementScanner14;
import javax.swing.JOptionPane;
import utils.UtilsObj;
import dao.BookDAO;
/**
 *
 * @author rapha
 */
public class HomeBroker {

    public static void main(String[] args) {

        ClienteController clienteController = new ClienteController();
        ContaController contaController = new ContaController();
        ClienteDAO[] vetorCliente = new ClienteDAO[5];
        ContaDAO[] vetorConta = new ContaDAO[5];
        CobrancaDeTaxa cobrancaDeTaxa = new CobrancaDeTaxa();
        OperacoesContaDAO[] vetorOperacoesConta = new OperacoesContaDAO[100];
        UtilsObj utils = new UtilsObj();
        OperacoesContaController operacoesContaController = new OperacoesContaController();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        GregorianCalendar calendario = new GregorianCalendar();

        BookDAO book = new BookDAO();
        book.newData(formatDate.format(calendario.getTime()));

        int op = 0;
        int opUser = 0;
        int opView = 0;
        int opDelete = 0;
        int opUpdate = 0;
        int verify = 0;
        int idOrdem = 0;
        
        int idCliente = 1;
        int idConta = 0;
        int idOperacoesConta = 0;
        
        boolean verificador = false;
        
        String verifySenha = "";
        String menuADM = "1 - Adicionar Usuario\n2 - Editar Usuario\n3 - Mostrar Cadastros\n4 - Excluir Usuário\n5 - Conta\n6 - Incrementar Dias\n\n0 - Sair\nDigite uma opção";
        String menuCOM = "1 - Perfil\n2 - Conta\n3 - Ativos\n4 - Ordem de Compra\\Venda\n5 - Incrementar Dias\n0 - Sair\n\nDigite uma opção";
        String menuADMConta = "1 - Visualizar Informações da Conta\n2 - Editar Conta\n\n0 - Voltar\nDigite Uma Opção";
        String menuCOMPerfil = "1 - Visualizar Perfil\n2 - Editar Perfil\n\n0 - Voltar\nDigite uma opção";
        String menuCOMConta = "Saldo: ------\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Mostrar Saldo\n\n0 - Voltar\nDigite uma opção";
        String auxMenu = menuCOMConta;
        
//CRIAÇÃO DO USUÁRIO ADM
        ClienteDAO clienteAdm = new ClienteDAO();
        clienteAdm.newData(idCliente, "adm", "adm", "adm", "adm", "adm", "adm123", true, format.format(calendario.getTime()), null);
        clienteController.insert(clienteAdm, vetorCliente);
        ContaDAO newContaAdm = new ContaDAO();
        newContaAdm.newData(++idConta, clienteAdm.id, 6500000.00, format.format(calendario.getTime()), null);
        contaController.insert(newContaAdm, vetorConta);
        ContaDAO newContaAdmBolsa = new ContaDAO();
        newContaAdmBolsa.newData(++idConta, clienteAdm.id, 1000000, format.format(calendario.getTime()), null);
        contaController.insert(newContaAdmBolsa, vetorConta);
        
//CRIACÃO USUÁRIO PROVISÓRIO PARA TESTES
        idCliente++;
        ClienteDAO clienteUser = new ClienteDAO();
        clienteUser.newData(idCliente, "Igor Ramalho", "user", "user", "user", "user", "user", false, format.format(calendario.getTime()), null);
        clienteController.insert(clienteUser, vetorCliente);  
        ContaDAO newContaUser = new ContaDAO();
        newContaUser.newData(++idConta, clienteUser.id, 520000.00, format.format(calendario.getTime()), null);
        contaController.insert(newContaUser, vetorConta);
        
//CRIACÃO USUÁRIO PROVISÓRIO PARA TESTES
        idCliente++;
        ClienteDAO clienteUser2 = new ClienteDAO();
        clienteUser2.newData(idCliente, "Raphael Gonzaga", "asd", "asd", "asd", "asd", "asd", false, format.format(calendario.getTime()), null);
        clienteController.insert(clienteUser2, vetorCliente);  
        ContaDAO newContaUser2 = new ContaDAO();
        newContaUser2.newData(++idConta, clienteUser2.id, 520000.00, format.format(calendario.getTime()), null);
        contaController.insert(newContaUser2, vetorConta);
  
        try {
            do {
            //AUTENTICAÇÃO DE SESSÃO
            String userName = JOptionPane.showInputDialog("Bem vindo ao HOME BROKER\n\n0 - Sair\nPara começar, insira seu Usuário");
            if(userName.hashCode() == "0".hashCode()){
                opUser = 1;
                break;
            }
            ClienteDAO user =clienteController.verifyUserName(vetorCliente, userName);
            if(user != null){
                String password = JOptionPane.showInputDialog("Insira sua senha");
                if(user.senha.hashCode() == password.hashCode()){

                    ContaDAO contaAdm = vetorConta[0];
                    ContaDAO bolsa = vetorConta[1];
                    
                    if(user.adm) {   
                        //MENU DO USUÁRIO ADMINISTRADOR
                        JOptionPane.showMessageDialog(null, "Bem vindo " + user.nome + "!");
                        do{ 
                            //ROTINA DE COBRANÇA DE TAXA DE MANUTENÇÃO
                            String dataAtualStr = formatDate.format(calendario.getTime());
                            if(calendario.get(calendario.DAY_OF_MONTH)== 15) {
                                boolean verifyPayment = true;
                                for (OperacoesContaDAO element : vetorOperacoesConta) {
                                    if(element != null){
                                        Date auxDate = format.parse(element.dataCriacao);
                                        if(formatDate.format(auxDate).hashCode() == formatDate.format(calendario.getTime()).hashCode() 
                                            && element.tipo == 4 && element.valor == 20.0
                                            && element.contaTransferencia == contaAdm.id){
                                            verifyPayment = false;
                                            break;
                                        }
                                    }
                                }
                                if(verifyPayment){
                                    idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, null, idOperacoesConta, vetorOperacoesConta, calendario);
                                    JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção da conta");
                                }
                            }          
       
                            op =  Integer.parseInt(JOptionPane.showInputDialog(dataAtualStr + "\n" + user.nome + "\n\n"+menuADM));
                            switch (op){
                                case 0:
                                    op = Integer.parseInt(JOptionPane.showInputDialog("Você Quer Sair?\n 1 - Sim\n 2 - Não"));
                                    op = (op == 1) ? 0 : 1;
                                break;
                                case 1://ADICIONAR USUÁRIO
                                    if(utils.vetorLength(vetorCliente) != 5) {   
                                        String name     = JOptionPane.showInputDialog("Digite um nome");
                                        String login    = JOptionPane.showInputDialog("Escolha o usuário");
                                        String pass     = JOptionPane.showInputDialog("Escolha uma senha");
                                        String adress   = JOptionPane.showInputDialog("Digite o Endereço");
                                        String cpf      = JOptionPane.showInputDialog("Digite o CPF");
                                        String phone    = JOptionPane.showInputDialog("Digite o Telefone");
                                        String creation = format.format(calendario.getTime());
                                        ClienteDAO newClient = new ClienteDAO();
                                        newClient.newData(++idCliente, name, adress, cpf, phone, login, pass, false, creation, null);
                                        if (clienteController.insert(newClient, vetorCliente)) {
                                            //CRIAÇÃO DE CONTA
                                            ContaDAO newConta = new ContaDAO();

                                            //TRANFERENCIA 500K PARA NOVA CONTA
                                            double resultAdm = operacoesContaController.depositoSaque(contaAdm.saldo, 500000, false);
                                            boolean resultaAdmUpdate = contaAdm.setSaldo(resultAdm);
                                            
                                            double resultUser = operacoesContaController.depositoSaque(0, 500000, true);
                                            newConta.newData(++idConta, idCliente, resultUser, creation, null);                                            
                                            boolean resultaUserInsert = contaController.insert(newConta, vetorConta); 
                                            
                                            if(resultaUserInsert && resultaAdmUpdate){
                                                //DEPOSITO AUTOMATICO DE 20K
                                                double deposito = operacoesContaController.depositoSaque(newConta.saldo, 20000, true);
                                                newConta.setSaldo(deposito);
                                                OperacoesContaDAO opDeposito = new OperacoesContaDAO();
                                                opDeposito.newData(++idOperacoesConta, newConta.id, contaAdm.id, 2, newConta.saldo,1, "Depósito Automático", 20000, format.format(calendario.getTime()), null);
                                                operacoesContaController.insert(opDeposito, vetorOperacoesConta);
                                                
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
                                                    if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                        int indice = clienteController.returnIndex(clienteUdpdate.id, vetorCliente); 
                                                        String nameUpdate   = JOptionPane.showInputDialog("Nome: "     + clienteUdpdate.nome + "\n\nDigite novo Nome");
                                                        String userUpdate   = JOptionPane.showInputDialog("Usuário: "  + clienteUdpdate.login +"\n\nDigite novo Usuário");
                                                        String passUpdate   = JOptionPane.showInputDialog("Senha: "    + clienteUdpdate.senha +"\n\nEscolha nova Senha");
                                                        String adressUpdate = JOptionPane.showInputDialog("Endereço: " + clienteUdpdate.endereco +"\n\nDigite novo Endereço");
                                                        String cpfUpdate    = JOptionPane.showInputDialog("CPF: "      + clienteUdpdate.CPF +"\n\nDigite novo CPF");
                                                        String phoneUpdate  = JOptionPane.showInputDialog("Telefone: " + clienteUdpdate.telefone +"\n\nDigite novo Telefone");
                                                        String update       = format.format(calendario.getTime());
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
                                                    if(verifySenha.hashCode() == user.senha.hashCode()){
                                                        
                                                        ContaDAO contaDel = contaController.returnContaByCliente(idDelete, vetorConta);
                                                        int indiceConta = contaController.returnIndex(contaDel.id, vetorConta);

                                                        if(contaController.delete(indiceConta, vetorConta) && clienteController.delete(indice, vetorCliente)){
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
                                                    ContaDAO contaView;
                                                    if(clienteViewConta.id == 1){
                                                        idViewConta = Integer.parseInt(JOptionPane.showInputDialog("1 - Conta ADM\n2 - Bolsa\n\nDigite uma opção"));
                                                        if(idViewConta == 0){
                                                            opConta = 4;
                                                            break;
                                                        } 
                                                        contaView = (idViewConta == 1) ? contaAdm : bolsa;
                                                    } else {
                                                        contaView = contaController.returnContaByCliente(clienteViewConta.id, vetorConta);
                                                    }
                                                    opConta = Integer.parseInt(JOptionPane.showInputDialog(menuADMConta));             
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

                                                            case 2://EXTRATO
                                                                int opExtrato = 1;
                                                                String extrato = "";
                                                                do{
                                                                    String dataInicial = JOptionPane.showInputDialog("Digite a data inicial?\n Ex: 01/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                                    String dataFinal = JOptionPane.showInputDialog("Digite a data final?\n Ex: 02/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                                    if(dataInicial.hashCode() == "0".hashCode() || dataFinal.hashCode() == "0".hashCode()) {
                                                                        opExtrato = 0;
                                                                        break;
                                                                    }
                                                                    Date dtinicio = format.parse(dataInicial + " 00:00:00");
                                                                    Date dtfinal = format.parse(dataFinal + " 23:59:59");
                                                                    ClienteDAO pagador = new ClienteDAO();
                                                                    ContaDAO contaPagador = new ContaDAO();
                                                                    for (OperacoesContaDAO element : vetorOperacoesConta) {
                                                                        if(element != null  && element.conta == contaView.id){
                                                                            if(element.tipo == 5 || element.tipo == 3){
                                                                                contaPagador = contaController.returnObjectById(element.contaTransferencia, vetorConta);
                                                                                pagador = clienteController.returnObjectById(contaPagador.cliente, vetorCliente);
                                                                            }
                                                                            Date dtExtrato = format.parse(element.dataCriacao);
                                                                            if(dtExtrato.before(dtfinal) && dtExtrato.after(dtinicio)){
                                                                                extrato += (element.operacao == 1) ? "Saida\n" : "Entrada\n";
                                                                                extrato += (element.tipo == 5) ? "Recebido de: " + pagador.nome + "\n" : "";
                                                                                extrato += (element.tipo == 3) ? "Enviado para: " + pagador.nome + "\n" : "";
                                                                                extrato += (element.tipo == 1) ? "Tipo: Depósito\n" : (element.tipo == 2) ? "Tipo: Saque\n" 
                                                                                        : (element.tipo == 3) ? "Tipo: Transferência\n" : (element.tipo == 4) ? "Tipo: Pagamento\n" : "Tipo: Recebimento\n";
                                                                                extrato += "Data: " + element.dataCriacao + "\n"+
                                                                                            "Valor: " + element.valor + "\n" +
                                                                                        "Saldo Final: R$" + element.saldoFinal + "\n------------------------------\n";
                                                                            }
                                                                        }
                                                                    }
                                                                    JOptionPane.showMessageDialog(null, extrato);
                                                                    opExtrato = 0;
                                                                } while (opExtrato != 0);
                                                            break;

                                                            case 3://EDITAR CONTA
                                                                verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                                                if(verifySenha.hashCode() == user.senha.hashCode()){
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
                                
                                case 6://INCREMENTAR DIAS
                                    int incrementDays = Integer.parseInt(JOptionPane.showInputDialog("Quantos dias deseja incrementar\n"
                                            + "\n1 - 1 Dia\n2 - 7 Dias\n3 - 15 Dias\n4 - 30\n\n0 - Voltar\nDigite uma opção"));
                                     switch (incrementDays) {
                                        case 2:
                                            incrementDays = 7;
                                        break;
                                        case 3:
                                             incrementDays = 15;
                                        break;
                                        
                                        case 4:
                                             incrementDays = 30;
                                        break;
                                     }
                                    for(int x = 0; x < incrementDays; x++){
                                        calendario.add(GregorianCalendar.DAY_OF_MONTH, 1);
                                        if(calendario.get(calendario.DAY_OF_MONTH)== 15) {
                                            idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, null, idOperacoesConta, vetorOperacoesConta, calendario);
                                            JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção da conta");
                                        }
                                     }
                                break;
                                case 7://dividendos
                                     
                                break;
                            } 
                        } while (op != 0);
                        JOptionPane.showMessageDialog(null, "Sessão Encerrada");
                        opUser = 1;

                    } else {
                           
                        //MENU DO USUÁRIO COMUM
                        ContaDAO conta = contaController.returnContaByCliente(user.id, vetorConta);
                        JOptionPane.showMessageDialog(null, "Bem vindo " + user.nome + "!");
                        do{
                            String dataAtualStr = formatDate.format(calendario.getTime());
                            
                            //ROTINA DE COBRANÇA DE TAXA DE MANUTENÇÃO
                            if(calendario.get(calendario.DAY_OF_MONTH)== 15) {
                                boolean verifyPayment = true;
                                for (OperacoesContaDAO element : vetorOperacoesConta) {
                                    if(element != null){
                                        Date auxDate = format.parse(element.dataCriacao);
                                        if(formatDate.format(auxDate).hashCode() == formatDate.format(calendario.getTime()).hashCode() 
                                            && element.tipo == 4 && element.valor == 20.0 && element.conta == conta.id
                                            && element.contaTransferencia == contaAdm.id){
                                            verifyPayment = false;
                                            break;
                                        }
                                    }
                                }
                                if(verifyPayment){
                                    idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, null, idOperacoesConta, vetorOperacoesConta, calendario);
                                    JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção da conta");
                                }
                            }
                            
                            if(verificador){
                                menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
                            }
                            
                            op =  Integer.parseInt(JOptionPane.showInputDialog(dataAtualStr + "\n" + user.nome + "\n\n"+menuCOM));
                            switch (op){
                                case 0:
                                    op = Integer.parseInt(JOptionPane.showInputDialog("Você Quer Sair?\n 1 - Sim\n 2 - Não"));
                                    op = (op == 1) ? 0 : 1;
                                break;
                                case 1://PERFIL
                                    int opPerfil = 1;
                                    do {
                                        opPerfil = Integer.parseInt(JOptionPane.showInputDialog(menuCOMPerfil));
                                        switch (opPerfil) {
                                            case 0:
                                            break;
                                            case 1://VISUALIZAR PERFIL
                                                String userClienteView = "Id: "+user.id 
                                                                        +"\nNome: "+user.nome
                                                                        +"\nLogin: "+user.login
                                                                        +"\nSenha: "+user.senha
                                                                        +"\nCPF: "+user.CPF
                                                                        +"\nTelefone: "+user.telefone
                                                                        +"\nData de Criação: "+user.dataCriacao;
                                                userClienteView += (user.dataModificacao == null) ? "\nData de Modificação: Sem Modificação" 
                                                        : "\nData de Modificação: " + user.dataModificacao;
                                                userClienteView += (user.adm == false) ? "\nAdministrador: Não" : "\nAdministrador: Sim";
                                                JOptionPane.showMessageDialog(null,userClienteView);
                                            break;

                                            case 2://EDITAR PERFIL
                                                verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                    
                                                    String nameUpdate   = JOptionPane.showInputDialog("Nome: "     + user.nome + "\n\nDigite novo Nome");
                                                    String userUpdate   = JOptionPane.showInputDialog("Usuário: "  + user.login +"\n\nDigite novo Usuário");
                                                    String passUpdate   = JOptionPane.showInputDialog("Senha: "    + user.senha +"\n\nEscolha nova Senha");
                                                    String adressUpdate = JOptionPane.showInputDialog("Endereço: " + user.endereco +"\n\nDigite novo Endereço");
                                                    String cpfUpdate    = JOptionPane.showInputDialog("CPF: "      + user.CPF +"\n\nDigite novo CPF");
                                                    String phoneUpdate  = JOptionPane.showInputDialog("Telefone: " + user.telefone +"\n\nDigite novo Telefone");
                                                    String update       = format.format(calendario.getTime());
                                                    user.newData(user.id, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false, user.dataCriacao, update);
                                                    JOptionPane.showMessageDialog(null,"Atualizado");
                                                } else {
                                                    JOptionPane.showMessageDialog(null,"Senha inválida"); 
                                                }
                                            break;
                                        }
                                    } while (opPerfil != 0);
                                    break;

                                case 2://CONTA
                                    int opCOMConta = 1;
                                    do {               
                                        opCOMConta = Integer.parseInt(JOptionPane.showInputDialog(menuCOMConta));
                                        if(opCOMConta < 0 || opCOMConta > 6){
                                            JOptionPane.showMessageDialog(null,"Digite uma opção válida"); 
                                        } else {
                                            switch (opCOMConta) {
                                                case 0:
                                                break;
                                                case 1://EXTRATO
                                                    int opExtrato = 1;
                                                    String extrato = "";
                                                    do{
                                                        String dataInicial = JOptionPane.showInputDialog("Digite a data inicial?\n Ex: 01/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                        String dataFinal = JOptionPane.showInputDialog("Digite a data final?\n Ex: 02/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                        if(dataInicial.hashCode() == "0".hashCode() || dataFinal.hashCode() == "0".hashCode()) {
                                                            opExtrato = 0;
                                                            break;
                                                        }
                                                        Date dtinicio = format.parse(dataInicial + " 00:00:00");
                                                        Date dtfinal = format.parse(dataFinal + " 23:59:59");
                                                        ClienteDAO pagador = new ClienteDAO();
                                                        ContaDAO contaPagador = new ContaDAO();
                                                        for (OperacoesContaDAO element : vetorOperacoesConta) {
                                                            if(element != null  && element.conta == conta.id){
                                                                if(element.tipo == 5 || element.tipo == 3){
                                                                    contaPagador = contaController.returnObjectById(element.contaTransferencia, vetorConta);
                                                                    pagador = clienteController.returnObjectById(contaPagador.cliente, vetorCliente);
                                                                }
                                                                Date dtExtrato = format.parse(element.dataCriacao);
                                                                if(dtExtrato.before(dtfinal) && dtExtrato.after(dtinicio)){
                                                                    extrato += (element.operacao == 1) ? "Saida\n" : "Entrada\n";
                                                                    extrato += (element.tipo == 5) ? "Recebido de: " + pagador.nome + "\n" : "";
                                                                    extrato += (element.tipo == 3) ? "Enviado para: " + pagador.nome + "\n" : "";
                                                                    extrato += (element.tipo == 1) ? "Tipo: Depósito\n" : (element.tipo == 2) ? "Tipo: Saque\n" 
                                                                            : (element.tipo == 3) ? "Tipo: Transferência\n" : (element.tipo == 4) ? "Tipo: Pagamento\n" : "Tipo: Recebimento\n";
                                                                    extrato += "Data: " + element.dataCriacao + "\n"+
                                                                                "Valor: " + element.valor + "\n" +
                                                                               "Saldo Final: R$" + element.saldoFinal + "\n------------------------------\n";
                                                                }
                                                            }
                                                        }
                                                        JOptionPane.showMessageDialog(null, extrato);
                                                        opExtrato = 0;
                                                    } while (opExtrato != 0);
                                                break;

                                                case 2://TRANSFERÊNCIA
                                                    int opTransferencia = Integer.parseInt(JOptionPane.showInputDialog("Para qual conta você deseja transferir? Digite apenas números\n\n0 - Voltar\nDigite uma opção")); 
                                                    do{
                                                        if(opTransferencia == 0){
                                                            break;
                                                        }
                                                        ContaDAO contaDE = new ContaDAO();
                                                        contaDE = contaController.returnObjectById(opTransferencia, vetorConta);
                                                        if(contaDE == null) {
                                                            JOptionPane.showMessageDialog(null, "Não há nenhuma conta associada");
                                                            break;
                                                        } else if(contaDE.cliente == user.id) {
                                                            JOptionPane.showMessageDialog(null, "Selecione uma conta diferente da sua");
                                                            break;
                                                        }

                                                        int valorTransferencia = Integer.parseInt(JOptionPane.showInputDialog("Quanto deseja transferir?\n\n0 - Voltar\nDigite uma opção"));
                                                        verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                        if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA

                                                            double resultUser = operacoesContaController.depositoSaque(conta.saldo, valorTransferencia, false);
                                                            double resultDE = operacoesContaController.depositoSaque(contaDE.saldo, valorTransferencia, true);
                                                            
                                                            if(resultUser != -1 && resultDE != -1){
                                                                if(resultUser > 0){

                                                                    String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");     
                                                                    boolean contaResult = conta.setSaldo(resultUser);
                                                                    boolean contaResultDE = contaDE.setSaldo(resultDE);

                                                                    if(contaResult && contaResultDE) {
                                                                        if(verificador){
                                                                            menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Pagamento\n3 - Tranferência\n4 - Depósito\n5 - Saque\n6 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
                                                                        }
                                                                        idOperacoesConta = operacoesContaController.newOperation(conta, contaDE, vetorOperacoesConta, idOperacoesConta, descricao, calendario, valorTransferencia, resultDE, false);

                                                                        JOptionPane.showMessageDialog(null,"Transferência realizada");    
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar transferência"); 
                                                                    }  
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,"Saldo insuficiente");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao calcular saldo"); 
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Senha inválida");
                                                        }
                                                        opTransferencia = 0;
                                                    }while(opTransferencia != 0);
                                                break;

                                                case 3://DEPÓSITO
                                                    int opDeposito = Integer.parseInt(JOptionPane.showInputDialog("Quanto deseja depositar?\n\n0 - Voltar\nDigite uma opção")); 
                                                    do{
                                                        if(opDeposito == 0){
                                                            break;
                                                        }
                                                        verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                        if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                            double result = operacoesContaController.depositoSaque(conta.saldo, opDeposito, true);
                                                            if(result != -1){
                                                                if(conta.setSaldo(result)) {
                                                                    if(verificador){
                                                                        menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Pagamento\n3 - Tranferência\n4 - Depósito\n5 - Saque\n6 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
                                                                    }
                                                                    String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");
                                                                    OperacoesContaDAO depositoOperacao = new OperacoesContaDAO();
                                                                    depositoOperacao.newData(++idOperacoesConta, conta.id, -1, 2, conta.saldo,1, descricao, opDeposito, format.format(calendario.getTime()), null);
                                                                    if(operacoesContaController.insert(depositoOperacao, vetorOperacoesConta)){ 
                                                                        JOptionPane.showMessageDialog(null,"Depósito realizado");    
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar operação");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar depósito"); 
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao calcular saldo"); 
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Senha inválida");
                                                        }
                                                        opDeposito = 0;
                                                    }while(opDeposito != 0);
                                                break;

                                                case 4://SAQUE
                                                    int opSaque = Integer.parseInt(JOptionPane.showInputDialog("Quanto deseja sacar?\n\n0 - Voltar\nDigite uma opção")); 
                                                    do{
                                                        if(opSaque == 0){
                                                            break;
                                                        }
                                                        double result = operacoesContaController.depositoSaque(conta.saldo, opSaque, false);
                                                        if(result >= 0){
                                                                verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                                if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                                    if(conta.setSaldo(result)) {
                                                                        if(verificador){
                                                                            menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Pagamento\n3 - Tranferência\n4 - Depósito\n5 - Saque\n6 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
                                                                        }
                                                                        String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");
                                                                        OperacoesContaDAO saqueOperacao = new OperacoesContaDAO();
                                                                        saqueOperacao.newData(++idOperacoesConta, conta.id, -1, 1, conta.saldo, 2, descricao, opSaque, format.format(calendario.getTime()), null);
                                                                        if(operacoesContaController.insert(saqueOperacao, vetorOperacoesConta)){ 
                                                                            JOptionPane.showMessageDialog(null,"Saque realizado");    
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar operação");
                                                                            }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar saque"); 
                                                                    }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Senha inválida"); 
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Saldo insuficiente");
                                                        }
                                                        opSaque = 0;
                                                    }while(opSaque != 0);
                                                break;

                                                case 5://MOSTRAR SALDO
                                                    verificador = (verificador == true) ? false : true;
                                                    String auxSaldo = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
                                                    if(verificador){
                                                        menuCOMConta = auxSaldo;
                                                    } else {
                                                        menuCOMConta = auxMenu;
                                                    }

                                                break;
                                            }                                            
                                        }

                                    } while(opCOMConta != 0);

                                    break;

                                case 3://ATIVOS****************************************
                                        JOptionPane.showMessageDialog(null, book.Ativos_book());
                                    break;

                                case 4://ORDEM****************************************
                                    OrdemDAO Ordem = new OrdemDAO(idOrdem++);
                                    int tipo_Ordem;
                                    int qtd;
                                    int id_Ordem = Integer.parseInt(JOptionPane.showInputDialog(book.Ativos_book() + "\n\nQual \"ID\" do ativo:"));
                                    // Ordem.setId(idOrdem++);
                                    Ordem.setAtivo(book.getAtivo(id_Ordem));
                                    Ordem.setConta(conta);
                                    do {
                                        tipo_Ordem = Integer.parseInt(JOptionPane.showInputDialog("Qual tipo de Ordem:\n"
                                        + "\n0 - Ordem 0\n1 - Compra\n2 - Venda \n"));
                                    } while (tipo_Ordem < 0 || tipo_Ordem > 2);
                                    Ordem.settipo_ordem(tipo_Ordem);
                                    Ordem.getAtivo().setPreço_inicial(Double.parseDouble(JOptionPane.showInputDialog("Preço para " + (tipo_Ordem == 2 ? "Venda" : "Compra"))));
                                    Ordem.getAtivo().setQuantidade(Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Ativos a compra")));
                                    Ordem.getAtivo().setDataCriacao(format.format(calendario.getTime()));
                                    Ordem.getAtivo().setDataModificacao(format.format(calendario.getTime()));
                                    Ordem.setPendente(false);
                                    
                                    AtivosDAO ativo = Ordem.getAtivo();
                                    double valor = ativo.preço_inicial*ativo.Quantidade;
                                    double resultUser = operacoesContaController.depositoSaque(conta.saldo, valor, false);
                                    double resultBolsa = operacoesContaController.depositoSaque(bolsa.saldo, valor, true);

                                    // if(tipo_Ordem == 2) {
                                    //     pagador = bolsa;
                                    //     recebedor = conta;
                                    //     resultUser = operacoesContaController.depositoSaque(conta.saldo, valor, true);
                                    //     resultBolsa = operacoesContaController.depositoSaque(bolsa.saldo, valor, false);
                                    // }

                                    if(resultUser > 0 && tipo_Ordem == 0) {
                                        if(conta.setSaldo(resultUser) && bolsa.setSaldo(resultBolsa)) {
                                            if(book.Cadastro_Ordem(Ordem)) {
                                                String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");
                                                idOperacoesConta = operacoesContaController.newOperation(conta, bolsa, vetorOperacoesConta, idOperacoesConta, descricao, calendario, valor, resultBolsa, true);                     
                                                JOptionPane.showMessageDialog(null, "Ordem de compra efetuada");

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Erro ao enviada Ordem ");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Erro ao atualizar conta");
                                        };
                                    } else if(resultUser > 0 && tipo_Ordem != 0) {
                                       Ordem.setPendente(true);
                                       JOptionPane.showMessageDialog(null, "Ordem enviada com sucesso, será processada em até 2 dias");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Saldo insuficiente");
                                    }
                                    
                                break;
                                
                                case 5://INCREMENTAR DIAS
                                    int incrementDays = Integer.parseInt(JOptionPane.showInputDialog("Quantos dias deseja incrementar\n"
                                            + "\n1 - 1 Dia\n2 - 7 Dias\n3 - 15 Dias\n4 - 30\n\n0 - Voltar\nDigite uma opção"));
                                     switch (incrementDays) {
                                        case 2:
                                            incrementDays = 7;
                                        break;
                                        case 3:
                                             incrementDays = 15;
                                        break;
                                        
                                        case 4:
                                             incrementDays = 30;
                                        break;
                                     }
                                     
                                     for(int x = 0; x < incrementDays; x++){
                                        calendario.add(GregorianCalendar.DAY_OF_MONTH, 1);
                                        if(calendario.get(calendario.DAY_OF_MONTH) == 15) {
                                            idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, conta, idOperacoesConta, vetorOperacoesConta, calendario);
                                            JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção da conta");
                                        }
                                     }
                                break;
                            } 
                        } while (op != 0);
                        JOptionPane.showMessageDialog(null, "Sessão Encerrada");
                        opUser = 1;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Senha Inválida");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não foi encontrado");
            }
            menuCOMConta = auxMenu;
            opUser = 0;
            } while(opUser != 1);
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err);
        }
        
    }
}
