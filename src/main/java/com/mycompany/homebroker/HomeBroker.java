/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.ClienteController;
import controller.CobrancaDeTaxa;
import controller.ContaController;
import controller.InvestimentoController;
import controller.OperacoesContaController;
import controller.PrecoAtivosController;
import dao.AtivosDAO;
import dao.ClienteDAO;
import dao.ContaDAO;
import dao.InvestOrdemDAO;
import dao.InvestimentoDAO;
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
        PrecoAtivosController precoAtivos = new PrecoAtivosController();
        InvestimentoDAO[] vetorInvestimento = new InvestimentoDAO[5];
        InvestimentoController investimentoController = new InvestimentoController();
        CobrancaDeTaxa cobrancaDeTaxa = new CobrancaDeTaxa();
        OperacoesContaDAO[] vetorOperacoesConta = new OperacoesContaDAO[100];
        UtilsObj utils = new UtilsObj();
        OperacoesContaController operacoesContaController = new OperacoesContaController();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        GregorianCalendar calendario = new GregorianCalendar();

        BookDAO book = new BookDAO();
        book.newData(formatDate.format(calendario.getTime()));
        precoAtivos.atualizaPrecoAtivos(book);

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
        int idInvestimento = 0;
        
        boolean verificador = false;
        
        String verifySenha = "";
        String menuADM = "1 - Adicionar Usuario\n2 - Editar Usuario\n3 - Mostrar Cadastros\n4 - Excluir Usu??rio\n5 - Conta\n6 - Incrementar Dias\n\n0 - Sair\nDigite uma op????o";
        String menuCOM = "1 - Perfil\n2 - Conta\n3 - Ativos\n4 - Ordem de Compra\\Venda\n5 - Incrementar Dias\n0 - Sair\n\nDigite uma op????o";
        String menuADMConta = "1 - Visualizar Informa????es da Conta\n2 - Editar Conta\n\n0 - Voltar\nDigite Uma Op????o";
        String menuCOMPerfil = "1 - Visualizar Perfil\n2 - Editar Perfil\n\n0 - Voltar\nDigite uma op????o";
        String menuCOMConta = "Saldo: ------\n1 - Extrato\n2 - Tranfer??ncia\n3 - Dep??sito\n4 - Saque\n5 - Mostrar Saldo\n\n0 - Voltar\nDigite uma op????o";
        String auxMenu = menuCOMConta;
        
//CRIA????O DO USU??RIO ADM
        ClienteDAO clienteAdm = new ClienteDAO();
        clienteAdm.newData(idCliente, "adm", "adm", "adm", "adm", "adm", "adm123", true, format.format(calendario.getTime()), null);
        clienteController.insert(clienteAdm, vetorCliente);
        ContaDAO newContaAdm = new ContaDAO();
        newContaAdm.newData(++idConta, clienteAdm.id, 6500000.00, format.format(calendario.getTime()), null);
        contaController.insert(newContaAdm, vetorConta);
        ContaDAO newContaAdmBolsa = new ContaDAO();
        newContaAdmBolsa.newData(++idConta, clienteAdm.id, 1000000, format.format(calendario.getTime()), null);
        contaController.insert(newContaAdmBolsa, vetorConta);
        
//CRIAC??O USU??RIO PROVIS??RIO PARA TESTES
        idCliente++;
        ClienteDAO clienteUser = new ClienteDAO();
        clienteUser.newData(idCliente, "Igor Ramalho", "user", "user", "user", "user", "user", false, format.format(calendario.getTime()), null);
        clienteController.insert(clienteUser, vetorCliente);  
        ContaDAO newContaUser = new ContaDAO();
        newContaUser.newData(++idConta, clienteUser.id, 520000.00, format.format(calendario.getTime()), null);
        contaController.insert(newContaUser, vetorConta);
        InvestOrdemDAO[] investOrdem = new InvestOrdemDAO[100];
        InvestimentoDAO investimento = new InvestimentoDAO();
        investimento.newData(++idInvestimento, newContaUser.id, investOrdem, format.format(calendario.getTime()), null);
        investimentoController.insert(investimento, vetorInvestimento);
        
//CRIAC??O USU??RIO PROVIS??RIO PARA TESTES
        idCliente++;
        ClienteDAO clienteUser2 = new ClienteDAO();
        clienteUser2.newData(idCliente, "Raphael Gonzaga", "asd", "asd", "asd", "asd", "asd", false, format.format(calendario.getTime()), null);
        clienteController.insert(clienteUser2, vetorCliente);  
        ContaDAO newContaUser2 = new ContaDAO();
        newContaUser2.newData(++idConta, clienteUser2.id, 520000.00, format.format(calendario.getTime()), null);
        contaController.insert(newContaUser2, vetorConta);
        InvestOrdemDAO[] investOrdem2 = new InvestOrdemDAO[100];
        InvestimentoDAO investimento2 = new InvestimentoDAO();
        investimento2.newData(++idInvestimento, newContaUser2.id, investOrdem2, format.format(calendario.getTime()), null);
        investimentoController.insert(investimento2, vetorInvestimento);
  
        try {
            do {
                
            //AUTENTICA????O DE SESS??O
            String userName = JOptionPane.showInputDialog("Bem vindo ao HOME BROKER\n\n0 - Sair\nPara come??ar, insira seu Usu??rio");
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
                        //MENU DO USU??RIO ADMINISTRADOR
                        JOptionPane.showMessageDialog(null, "Bem vindo " + user.nome + "!");
                        do{ 
                            //ROTINA DE COBRAN??A DE TAXA DE MANUTEN????O
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
                                    idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, idOperacoesConta, vetorOperacoesConta, calendario);
                                    JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manuten????o da conta");
                                }
                            }          
       
                            op =  Integer.parseInt(JOptionPane.showInputDialog(dataAtualStr + "\n" + user.nome + "\n\n"+menuADM));
                            switch (op){
                                case 0:
                                    op = Integer.parseInt(JOptionPane.showInputDialog("Voc?? Quer Sair?\n 1 - Sim\n 2 - N??o"));
                                    op = (op == 1) ? 0 : 1;
                                break;
                                case 1://ADICIONAR USU??RIO
                                    if(utils.vetorLength(vetorCliente) != 5) {   
                                        String name     = JOptionPane.showInputDialog("Digite um nome");
                                        String login    = JOptionPane.showInputDialog("Escolha o usu??rio");
                                        String pass     = JOptionPane.showInputDialog("Escolha uma senha");
                                        String adress   = JOptionPane.showInputDialog("Digite o Endere??o");
                                        String cpf      = JOptionPane.showInputDialog("Digite o CPF");
                                        String phone    = JOptionPane.showInputDialog("Digite o Telefone");
                                        String creation = format.format(calendario.getTime());
                                        ClienteDAO newClient = new ClienteDAO();
                                        newClient.newData(++idCliente, name, adress, cpf, phone, login, pass, false, creation, null);
                                        if (clienteController.insert(newClient, vetorCliente)) {
                                            //CRIA????O DE CONTA
                                            ContaDAO newConta = new ContaDAO();

                                            //TRANFERENCIA 500K PARA NOVA CONTA
                                            double resultAdm = operacoesContaController.depositoSaque(contaAdm.saldo, 500000, false);
                                            boolean resultaAdmUpdate = contaAdm.setSaldo(resultAdm);
                                            
                                            double resultUser = operacoesContaController.depositoSaque(0, 500000, true);
                                            newConta.newData(++idConta, idCliente, resultUser, creation, null);                                            
                                            boolean resultaUserInsert = contaController.insert(newConta, vetorConta); 

                                            InvestOrdemDAO[] newInvestOrdem = new InvestOrdemDAO[100];
                                            InvestimentoDAO newInvest = new InvestimentoDAO();
                                            newInvest.newData(++idInvestimento, newConta.id, newInvestOrdem, format.format(calendario.getTime()), null);
                                            investimentoController.insert(newInvest, vetorInvestimento);
                                            
                                            if(resultaUserInsert && resultaAdmUpdate){
                                                //DEPOSITO AUTOMATICO DE 20K
                                                double deposito = operacoesContaController.depositoSaque(newConta.saldo, 20000, true);
                                                newConta.setSaldo(deposito);
                                                OperacoesContaDAO opDeposito = new OperacoesContaDAO();
                                                opDeposito.newData(++idOperacoesConta, newConta.id, contaAdm.id, 2, newConta.saldo,1, "Dep??sito Autom??tico", 20000, format.format(calendario.getTime()), null);
                                                operacoesContaController.insert(opDeposito, vetorOperacoesConta);
                                                idOperacoesConta = operacoesContaController.newOperation(contaAdm, newConta, vetorOperacoesConta, idOperacoesConta, "Transfer??ncia B??nus", calendario, 500000, resultUser, false);
                                                
                                                JOptionPane.showMessageDialog (null, "Usu??rio e Conta Criados");
                                            } else {
                                                JOptionPane.showMessageDialog (null, "Ocorreu um erro durante a cria????o da Conta");
                                            }
                                        }  else {
                                            JOptionPane.showMessageDialog (null, "Ocorreu um erro durante a cria????o do Usu??rio");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog (null, "M??ximo de Usu??rios Cadastrados, exclua um usu??rio");
                                    }

                                    break;

                                case 2://EDITAR USU??RIO
                                    do {
                                        opUpdate = 0;
                                        if(utils.verifyObjectIsVoid(vetorCliente) == 0){
                                            String auxMenuClienteUpdate = "";
                                            for (ClienteDAO element : vetorCliente) {
                                                if(element != null){
                                                    auxMenuClienteUpdate += "Cliente: " + element.nome + " - Id: " + element.id + "\n";
                                                }                      
                                            }
                                            int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteUpdate + "\n0 - Voltar\nDigite o Id do Usu??rio"));
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
                                                        String userUpdate   = JOptionPane.showInputDialog("Usu??rio: "  + clienteUdpdate.login +"\n\nDigite novo Usu??rio");
                                                        String passUpdate   = JOptionPane.showInputDialog("Senha: "    + clienteUdpdate.senha +"\n\nEscolha nova Senha");
                                                        String adressUpdate = JOptionPane.showInputDialog("Endere??o: " + clienteUdpdate.endereco +"\n\nDigite novo Endere??o");
                                                        String cpfUpdate    = JOptionPane.showInputDialog("CPF: "      + clienteUdpdate.CPF +"\n\nDigite novo CPF");
                                                        String phoneUpdate  = JOptionPane.showInputDialog("Telefone: " + clienteUdpdate.telefone +"\n\nDigite novo Telefone");
                                                        String update       = format.format(calendario.getTime());
                                                        ClienteDAO newObj   = new ClienteDAO();
                                                        newObj.newData(idUpdate, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false, clienteUdpdate.dataCriacao, update);

                                                        if(clienteController.update(newObj, vetorCliente, indice)){
                                                           JOptionPane.showMessageDialog(null,"Atualizado");
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a atualiza????o"); 
                                                        }
                                                        opUpdate = 1;
                                                    }  else {
                                                        JOptionPane.showMessageDialog(null,"Senha Inv??lida");
                                                    }
                                                }   
                                            } else {
                                                JOptionPane.showMessageDialog(null,"Nenhum cliente encontrado");
                                            }

                                        } else {
                                                JOptionPane.showMessageDialog(null, "N??o h?? clientes cadastrados");
                                                opUpdate = 1;
                                        }
                                    } while(opUpdate != 1);

                                    break;

                                case 3://MOSTRAR USU??RIOS

                                    do{
                                        String auxMenuClienteView = "";
                                        verify = clienteController.verifyHaveOnlyAdm(vetorCliente);
                                        if(verify > 1) {

                                            for (ClienteDAO element : vetorCliente) {
                                                if(element != null){
                                                    auxMenuClienteView += "Id: " + element.id + " -  Cliente: " + element.nome + "\n";
                                                }                      
                                            }

                                            int idViewCliente = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteView + "\n0 - Voltar\nDigite uma op????o"));
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
                                                                        +"\nData de Cria????o: "+clienteView.dataCriacao;
                                                clienteViewText += (clienteView.dataModificacao == null) ? "\nData de Modifica????o: Sem Modifica????o" 
                                                        : "\nData de Modifica????o: " + clienteView.dataModificacao;
                                                clienteViewText += (clienteView.adm == false) ? "\nAdministrador: N??o" : "\nAdministrador: Sim";
                                                JOptionPane.showMessageDialog(null,clienteViewText);          
                                            } else {
                                                JOptionPane.showMessageDialog(null,"Nenhum cliente encontrado");
                                            }
                                        } else if(verify == 1) {
                                            JOptionPane.showMessageDialog(null,"N??o h?? clientes cadastrados");
                                        } else {
                                            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao buscar clientes");
                                        }
                                    }while(opView != 0);

                                    break;

                                case 4://EXCLUIR USU??RIOS (COM EXECESS??O DO ADMINISTRADOR)
                                    do {
                                        verify = clienteController.verifyHaveOnlyAdm(vetorCliente);
                                        if(verify > 1) {         
                                            String auxMenuClienteDel = "";
                                            for (ClienteDAO element : vetorCliente) {
                                                if(element != null && element.adm == false){
                                                    auxMenuClienteDel += "Id: " + element.id + "-  Cliente: " + element.nome + "\n";
                                                }                      
                                            }
                                            int idDelete = Integer.parseInt(JOptionPane.showInputDialog(auxMenuClienteDel + "\n0 - Voltar\nDigite o Id do Usu??rio"));
                                            if(idDelete == 1){
                                                JOptionPane.showMessageDialog(null, "N??o ?? poss??vel excluir usu??rio Administrador");
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
                                                           JOptionPane.showMessageDialog(null,"Usu??rio Exclu??do");
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a exclus??o"); 
                                                        }
                                                        opDelete = 1;
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,"Senha Inv??lida");
                                                    }
                                            } else {
                                                if(indice == -1) {
                                                    JOptionPane.showMessageDialog(null,"Usu??rio n??o encontrado");
                                                } else {
                                                    JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a atualiza????o"); 
                                                }
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null,"N??o h?? clientes cadastrados");
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
                                            int idViewConta = Integer.parseInt(JOptionPane.showInputDialog(auxMenuContaView + "\n0 - Voltar\nDigite uma op????o"));
                                            ClienteDAO clienteViewConta = clienteController.returnObjectById(idViewConta, vetorCliente);
                                            if(idViewConta == 0){
                                                    opConta = 4;
                                                    break;
                                                } 
                                            if(clienteViewConta != null){
                                                    int internConta = 0;
                                                    ContaDAO contaView;
                                                    if(clienteViewConta.id == 1){
                                                        idViewConta = Integer.parseInt(JOptionPane.showInputDialog("1 - Conta ADM\n2 - Bolsa\n\nDigite uma op????o"));
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
                                                            case 1://VISUALIZAR INFORMA????ES DA CONTA
                                                                String internView = "Cliente: " + clienteViewConta.nome + "\nSaldo: " + contaView.saldo + "\nData de Cria????o: " + contaView.dataCriacao;
                                                                internView += (clienteViewConta.dataModificacao != null) ?  "\n??ltima Modifica????o: " + clienteViewConta.dataModificacao : "";
                                                                JOptionPane.showMessageDialog(null,internView);
                                                            break;

                                                            case 2://EXTRATO
                                                                int opExtrato = 1;
                                                                String extrato = "";
                                                                do{
                                                                    String dataInicial = JOptionPane.showInputDialog("Digite a data inicial?\n Ex: 01/01/2022\n\n0 - Voltar\nDigite uma op????o");
                                                                    String dataFinal = JOptionPane.showInputDialog("Digite a data final?\n Ex: 02/01/2022\n\n0 - Voltar\nDigite uma op????o");
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
                                                                                extrato += (element.tipo == 1) ? "Tipo: Dep??sito\n" : (element.tipo == 2) ? "Tipo: Saque\n" 
                                                                                        : (element.tipo == 3) ? "Tipo: Transfer??ncia\n" : (element.tipo == 4) ? "Tipo: Pagamento\n" : "Tipo: Recebimento\n";
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
                                                                    int updateConta = Integer.parseInt(JOptionPane.showInputDialog("1 - Adicionar Saldo\n2 - Remover Saldo\n\n0 - Voltar\nDigite Uma Op????o"));
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
                                                                    JOptionPane.showMessageDialog(null,"Senha Inv??lida");
                                                                }
                                                            break;
                                                        }
                                                } while(internConta != 0);
                                                }  else {
                                                    JOptionPane.showMessageDialog(null,"N??o foi encontrado nenhum cliente");
                                                }
                                        }while(opConta != 4); 
                                    } else if(verify == 1) {
                                        JOptionPane.showMessageDialog(null,"N??o h?? clientes cadastrados");
                                    } else {
                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao buscar clientes");
                                    }
                                break;
                                
                                case 6://INCREMENTAR DIAS
                                    int incrementDays = Integer.parseInt(JOptionPane.showInputDialog("Quantos dias deseja incrementar\n"
                                            + "\n1 - 1 Dia\n2 - 7 Dias\n3 - 15 Dias\n4 - 30\n\n0 - Voltar\nDigite uma op????o"));
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
                                        precoAtivos.atualizaPrecoAtivos(book);
                                        precoAtivos.atualizaTotalInvestido(vetorConta, vetorInvestimento, book.getAtivos());
                                        if(calendario.get(calendario.DAY_OF_MONTH) == 15) {
                                            idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, idOperacoesConta, vetorOperacoesConta, calendario);
                                            JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manuten????o da conta");
                                        }
                                     }
                                break;
                                case 7://dividendos
                                     
                                break;
                            } 
                        } while (op != 0);
                        JOptionPane.showMessageDialog(null, "Sess??o Encerrada");
                        opUser = 1;

                    } else {
                           
                        //MENU DO USU??RIO COMUM
                        ContaDAO conta = contaController.returnContaByCliente(user.id, vetorConta);
                        InvestimentoDAO contaInvest = investimentoController.returnObjectByConta(conta.id, vetorInvestimento);
                        // double investimentoInicial = 0;
                        JOptionPane.showMessageDialog(null, "Bem vindo " + user.nome + "!");
                        do{
                            String dataAtualStr = formatDate.format(calendario.getTime());
                            
                            //ROTINA DE COBRAN??A DE TAXA DE MANUTEN????O
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
                                    idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, idOperacoesConta, vetorOperacoesConta, calendario);
                                    JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manuten????o da conta");
                                }
                            }
                            
                            //ROTINA ATUALIZA????O DE LUCRO
                            // precoAtivos.atualizaPrecoAtivos(book);
                            // precoAtivos.calculaLucro(vetorConta, vetorInvestimento, book.getAtivos());

                            if(verificador){
                                menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Tranfer??ncia\n3 - Dep??sito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma op????o";
                            }
                            
                            double totalInvestido = 0;
                            op =  Integer.parseInt(JOptionPane.showInputDialog(dataAtualStr + "\n" + user.nome + "\n\n"+menuCOM));
                            switch (op){
                                case 0:
                                    op = Integer.parseInt(JOptionPane.showInputDialog("Voc?? Quer Sair?\n 1 - Sim\n 2 - N??o"));
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
                                                                        +"\nData de Cria????o: "+user.dataCriacao;
                                                userClienteView += (user.dataModificacao == null) ? "\nData de Modifica????o: Sem Modifica????o" 
                                                        : "\nData de Modifica????o: " + user.dataModificacao;
                                                userClienteView += (user.adm == false) ? "\nAdministrador: N??o" : "\nAdministrador: Sim";
                                                JOptionPane.showMessageDialog(null,userClienteView);
                                            break;

                                            case 2://EDITAR PERFIL
                                                verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                    
                                                    String nameUpdate   = JOptionPane.showInputDialog("Nome: "     + user.nome + "\n\nDigite novo Nome");
                                                    String userUpdate   = JOptionPane.showInputDialog("Usu??rio: "  + user.login +"\n\nDigite novo Usu??rio");
                                                    String passUpdate   = JOptionPane.showInputDialog("Senha: "    + user.senha +"\n\nEscolha nova Senha");
                                                    String adressUpdate = JOptionPane.showInputDialog("Endere??o: " + user.endereco +"\n\nDigite novo Endere??o");
                                                    String cpfUpdate    = JOptionPane.showInputDialog("CPF: "      + user.CPF +"\n\nDigite novo CPF");
                                                    String phoneUpdate  = JOptionPane.showInputDialog("Telefone: " + user.telefone +"\n\nDigite novo Telefone");
                                                    String update       = format.format(calendario.getTime());
                                                    user.newData(user.id, nameUpdate, adressUpdate, cpfUpdate, phoneUpdate, userUpdate, passUpdate, false, user.dataCriacao, update);
                                                    JOptionPane.showMessageDialog(null,"Atualizado");
                                                } else {
                                                    JOptionPane.showMessageDialog(null,"Senha inv??lida"); 
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
                                            JOptionPane.showMessageDialog(null,"Digite uma op????o v??lida"); 
                                        } else {
                                            switch (opCOMConta) {
                                                case 0:
                                                break;
                                                case 1://EXTRATO
                                                    int opExtrato = 1;
                                                    String extrato = "";
                                                    do{
                                                        String dataInicial = JOptionPane.showInputDialog("Digite a data inicial?\n Ex: 01/01/2022\n\n0 - Voltar\nDigite uma op????o");
                                                        String dataFinal = JOptionPane.showInputDialog("Digite a data final?\n Ex: 02/01/2022\n\n0 - Voltar\nDigite uma op????o");
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
                                                                    extrato += (element.tipo == 1) ? "Tipo: Dep??sito\n" : (element.tipo == 2) ? "Tipo: Saque\n" 
                                                                            : (element.tipo == 3) ? "Tipo: Transfer??ncia\n" : (element.tipo == 4) ? "Tipo: Pagamento\n" : "Tipo: Recebimento\n";
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

                                                case 2://TRANSFER??NCIA
                                                    int opTransferencia = Integer.parseInt(JOptionPane.showInputDialog("Para qual conta voc?? deseja transferir? Digite apenas n??meros\n\n0 - Voltar\nDigite uma op????o")); 
                                                    do{
                                                        if(opTransferencia == 0){
                                                            break;
                                                        }
                                                        ContaDAO contaDE = new ContaDAO();
                                                        contaDE = contaController.returnObjectById(opTransferencia, vetorConta);
                                                        if(contaDE == null) {
                                                            JOptionPane.showMessageDialog(null, "N??o h?? nenhuma conta associada");
                                                            break;
                                                        } else if(contaDE.cliente == user.id) {
                                                            JOptionPane.showMessageDialog(null, "Selecione uma conta diferente da sua");
                                                            break;
                                                        }

                                                        int valorTransferencia = Integer.parseInt(JOptionPane.showInputDialog("Quanto deseja transferir?\n\n0 - Voltar\nDigite uma op????o"));
                                                        verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                        if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA

                                                            double resultUser = operacoesContaController.depositoSaque(conta.saldo, valorTransferencia, false);
                                                            double resultDE = operacoesContaController.depositoSaque(contaDE.saldo, valorTransferencia, true);
                                                            
                                                            if(resultUser != -1 && resultDE != -1){
                                                                if(resultUser > 0){

                                                                    String descricao = JOptionPane.showInputDialog("Adicione uma descri????o (Opcional");     
                                                                    boolean contaResult = conta.setSaldo(resultUser);
                                                                    boolean contaResultDE = contaDE.setSaldo(resultDE);

                                                                    if(contaResult && contaResultDE) {
                                                                        if(verificador){
                                                                            menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Pagamento\n3 - Tranfer??ncia\n4 - Dep??sito\n5 - Saque\n6 - Ocultar Saldo\n\n0 - Voltar\nDigite uma op????o";
                                                                        }
                                                                        idOperacoesConta = operacoesContaController.newOperation(conta, contaDE, vetorOperacoesConta, idOperacoesConta, descricao, calendario, valorTransferencia, resultDE, false);

                                                                        JOptionPane.showMessageDialog(null,"Transfer??ncia realizada");    
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar transfer??ncia"); 
                                                                    }  
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,"Saldo insuficiente");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao calcular saldo"); 
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Senha inv??lida");
                                                        }
                                                        opTransferencia = 0;
                                                    }while(opTransferencia != 0);
                                                break;

                                                case 3://DEP??SITO
                                                    int opDeposito = Integer.parseInt(JOptionPane.showInputDialog("Quanto deseja depositar?\n\n0 - Voltar\nDigite uma op????o")); 
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
                                                                        menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Pagamento\n3 - Tranfer??ncia\n4 - Dep??sito\n5 - Saque\n6 - Ocultar Saldo\n\n0 - Voltar\nDigite uma op????o";
                                                                    }
                                                                    String descricao = JOptionPane.showInputDialog("Adicione uma descri????o (Opcional");
                                                                    OperacoesContaDAO depositoOperacao = new OperacoesContaDAO();
                                                                    depositoOperacao.newData(++idOperacoesConta, conta.id, -1, 2, conta.saldo,1, descricao, opDeposito, format.format(calendario.getTime()), null);
                                                                    if(operacoesContaController.insert(depositoOperacao, vetorOperacoesConta)){ 
                                                                        JOptionPane.showMessageDialog(null,"Dep??sito realizado");    
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar opera????o");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar dep??sito"); 
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao calcular saldo"); 
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Senha inv??lida");
                                                        }
                                                        opDeposito = 0;
                                                    }while(opDeposito != 0);
                                                break;

                                                case 4://SAQUE
                                                    int opSaque = Integer.parseInt(JOptionPane.showInputDialog("Quanto deseja sacar?\n\n0 - Voltar\nDigite uma op????o")); 
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
                                                                            menuCOMConta = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Pagamento\n3 - Tranfer??ncia\n4 - Dep??sito\n5 - Saque\n6 - Ocultar Saldo\n\n0 - Voltar\nDigite uma op????o";
                                                                        }
                                                                        String descricao = JOptionPane.showInputDialog("Adicione uma descri????o (Opcional");
                                                                        OperacoesContaDAO saqueOperacao = new OperacoesContaDAO();
                                                                        saqueOperacao.newData(++idOperacoesConta, conta.id, -1, 1, conta.saldo, 2, descricao, opSaque, format.format(calendario.getTime()), null);
                                                                        if(operacoesContaController.insert(saqueOperacao, vetorOperacoesConta)){ 
                                                                            JOptionPane.showMessageDialog(null,"Saque realizado");    
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar opera????o");
                                                                            }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,"Ocorreu um erro ao registrar saque"); 
                                                                    }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Senha inv??lida"); 
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,"Saldo insuficiente");
                                                        }
                                                        opSaque = 0;
                                                    }while(opSaque != 0);
                                                break;

                                                case 5://MOSTRAR SALDO
                                                    verificador = (verificador == true) ? false : true;
                                                    String auxSaldo = "Saldo: " + conta.saldo + "\n1 - Extrato\n2 - Tranfer??ncia\n3 - Dep??sito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma op????o";
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

                                    //CRIAR AQUI A TELA MEUS ATIVOS
                                        JOptionPane.showMessageDialog(null, book.Ativos_book());
                                    break;

                                case 4://ORDEM****************************************
                                    //CRIAR AQUI O BOOKING DE OFERTAS
                                    OrdemDAO Ordem = new OrdemDAO(idOrdem++);
                                    int tipo_Ordem;
                                    int id_Ordem = Integer.parseInt(JOptionPane.showInputDialog(book.Ativos_book() + "\n\nQual \"ID\" do ativo:"));
                                    AtivosDAO newBook = book.getAtivo(id_Ordem).returnClone();
                                    Ordem.setAtivo(newBook);
                                    
                                    do {
                                        tipo_Ordem = Integer.parseInt(JOptionPane.showInputDialog("Qual tipo de Ordem:\n"
                                        + "\n0 - Ordem 0\n1 - Compra\n2 - Venda \n"));
                                    } while (tipo_Ordem < 0 || tipo_Ordem > 2);
                                    Ordem.settipo_ordem(tipo_Ordem);
                                    Ordem.getAtivo().setPre??o_inicial(Double.parseDouble(JOptionPane.showInputDialog("Pre??o para " + (tipo_Ordem == 2 ? "Venda" : "Compra"))));
                                    Ordem.getAtivo().setQuantidade(Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Ativos a compra")));
                                    Ordem.getAtivo().setDataCriacao(format.format(calendario.getTime()));
                                    Ordem.getAtivo().setDataModificacao(format.format(calendario.getTime()));
                                    Ordem.setPendente(false);
                                    
                                    AtivosDAO ativo = Ordem.getAtivo();
                                    double valor = ativo.pre??o_inicial*ativo.Quantidade;
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
                                                String descricao = JOptionPane.showInputDialog("Adicione uma descri????o (Opcional");
                                                idOperacoesConta = operacoesContaController.newOperation(conta, bolsa, vetorOperacoesConta, idOperacoesConta, descricao, calendario, valor, resultBolsa, true);                     
                                                JOptionPane.showMessageDialog(null, "Ordem de compra efetuada");

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Erro ao enviada Ordem ");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Erro ao atualizar conta");
                                        };
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Saldo insuficiente");
                                    }
                                    
                                    ContaDAO newCOnta = conta.returnClone();
                                    Ordem.setConta(newCOnta);
                                    InvestOrdemDAO investOr = new InvestOrdemDAO();
                                    investOr.newData(Ordem);
                                    contaInvest.setInvestimentoOrdem(investOr);
                                    
                                break;
                                
                                case 5://INCREMENTAR DIAS
                                    int incrementDays = Integer.parseInt(JOptionPane.showInputDialog("Quantos dias deseja incrementar\n"
                                            + "\n1 - 1 Dia\n2 - 7 Dias\n3 - 15 Dias\n4 - 30\n\n0 - Voltar\nDigite uma op????o"));
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
                                        precoAtivos.atualizaPrecoAtivos(book);
                                        precoAtivos.atualizaTotalInvestido(vetorConta, vetorInvestimento, book.getAtivos());
                                        if(calendario.get(calendario.DAY_OF_MONTH) == 15) {
                                            idOperacoesConta = cobrancaDeTaxa.cobrarTaxa(vetorConta, idOperacoesConta, vetorOperacoesConta, calendario);
                                            JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manuten????o da conta");
                                        }
                                     }
                                break;
                            } 
                        } while (op != 0);
                        JOptionPane.showMessageDialog(null, "Sess??o Encerrada");
                        opUser = 1;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Senha Inv??lida");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usu??rio n??o foi encontrado");
            }
            menuCOMConta = auxMenu;
            opUser = 0;
            } while(opUser != 1);
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err);
        }
        
    }
}
