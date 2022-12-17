/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.homebroker;

import controller.ClienteController;
import controller.CobrancaDeTaxa;
import controller.ContaController;
import controller.DBConnectionController;
import controller.OperacoesContaController;
import controller.PDFController;
import dao.ClienteDAO;
import dao.ContaDAO;
import dao.NewBookDAO;
import dao.NewOrdenDAO;
import dao.OperacoesContaDAO;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

// import javax.lang.model.util.ElementScanner14;
import javax.swing.JOptionPane;
/**
 *
 * @author rapha
 */
public class HomeBroker {

    public static void main(String[] args) {

        ClienteController clienteController = new ClienteController();
        ContaController contaController = new ContaController();
        OperacoesContaController operacoesContaController = new OperacoesContaController();
        Set<ClienteDAO> vetorCliente = new LinkedHashSet<>();
        Set<ContaDAO> vetorConta = new LinkedHashSet<>();
        PDFController pdfController = new PDFController();
        NewBookDAO book = new NewBookDAO();
        Set<OperacoesContaDAO> vetorOperacoes = new LinkedHashSet<>();
        Map<String,String> objUpdate = new LinkedHashMap<String,String>();
        CobrancaDeTaxa cobrancaDeTaxa = new CobrancaDeTaxa();
        OperacoesContaDAO[] vetorOperacoesConta = new OperacoesContaDAO[100];
        DBConnectionController dbConn = new DBConnectionController();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatExtrato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar calendario = new GregorianCalendar();
        NumberFormat number = NumberFormat.getCurrencyInstance();    
    
        int op = 0;
        int opUser = 0;
        int opView = 0;
        int opDelete = 0;
        int opUpdate = 0;
        int verify = 0;  
        int idOperacoesConta = 0;       
        boolean verificador = false;
        
        String verifySenha = "";
        String menuADM = "1 - Adicionar Usuario\n2 - Editar Usuario\n3 - Mostrar Cadastros\n4 - Excluir Usuário\n5 - Conta\n6 - Incrementar Dias\n7 - Dividendo\n8 - Ordem de Compra\\Venda\n\n0 - Sair\nDigite uma opção";
        String menuCOM = "1 - Perfil\n2 - Conta\n3 - Ativos\n4 - Ordem de Compra\\Venda\n5 - Incrementar Dias\n6 - Listar ativos Meus\n7 - Listar ativos Bolsa\n\n0 - Sair\n\nDigite uma opção";
        String menuADMConta = "1 - Visualizar Informações da Conta\n2 - Extrato\n3 - Editar Conta\n\n0 - Voltar\nDigite Uma Opção";
        String menuCOMPerfil = "1 - Visualizar Perfil\n2 - Editar Perfil\n\n0 - Voltar\nDigite uma opção";
        String menuCOMConta = "Saldo: ------\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Mostrar Saldo\n\n0 - Voltar\nDigite uma opção";
        String auxMenu = menuCOMConta;

        vetorCliente = clienteController.search();
        vetorConta = contaController.search();
        
  
        try {
            do {      
            //AUTENTICAÇÃO DE SESSÃO
            String userName = JOptionPane.showInputDialog("Bem vindo ao HOME BROKER\n\n0 - Sair\nPara começar, insira seu Usuário");
            if(userName.hashCode() == "0".hashCode()){
                opUser = 1;
                break;
            }
            ClienteDAO user = clienteController.verifyUserName(vetorCliente, userName);
            if(user != null){
                String password = JOptionPane.showInputDialog("Insira sua senha");
                if(user.senha.hashCode() == password.hashCode()){

                    ContaDAO contaAdm = contaController.getContaAdm(vetorConta);
                    ContaDAO bolsa = contaController.getContaBolsa(vetorConta);
                    
                    if(user.adm) { 
                        //MENU DO USUÁRIO ADMINISTRADOR
                        JOptionPane.showMessageDialog(null, "Bem vindo " + user.nome + "!");
                        do{ 
                            //ROTINA DE COBRANÇA DE TAXA DE MANUTENÇÃO
                            String dataAtualStr = formatDate.format(calendario.getTime());
                            if(calendario.get(calendario.DAY_OF_MONTH) == 15) {
                                cobrancaDeTaxa.cobrarTaxa(calendario, vetorConta);
                                JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção " + formatDate.format(calendario.getTime()));     

                            }
       
                            op =  Integer.parseInt(JOptionPane.showInputDialog(dataAtualStr + "\n" + user.nome + "\n\n"+menuADM));
                            switch (op){
                                case 0:
                                    op = Integer.parseInt(JOptionPane.showInputDialog("Você Quer Sair?\n 1 - Sim\n 2 - Não"));
                                    op = (op == 1) ? 0 : 1;
                                break;

                                case 1://ADICIONAR USUÁRIO
                                    if(vetorCliente.size() <= 5) {   
                                        objUpdate.put("NOME", JOptionPane.showInputDialog("Digite um nome"));
                                        objUpdate.put("LOGIN", JOptionPane.showInputDialog("Escolha o usuário"));
                                        objUpdate.put("SENHA", JOptionPane.showInputDialog("Escolha uma senha"));
                                        objUpdate.put("ENDERECO", JOptionPane.showInputDialog("igite o Endereço"));
                                        objUpdate.put("CPF", JOptionPane.showInputDialog("Digite o CPF"));
                                        objUpdate.put("TELEFONE", JOptionPane.showInputDialog("Digite o Telefone"));
                                        objUpdate.put("DTCRIACAO", formatBanco.format(calendario.getTime()));
                                        int idClient = dbConn.insert("CLIENTE", objUpdate);
                                        vetorCliente = clienteController.search();

                                        //CRIAÇÃO DE CONTA
                                        objUpdate.put("IDCLIENTE", Integer.toString(idClient));
                                        int idCont = dbConn.insert("CONTA", objUpdate);
                                        vetorConta = contaController.search();
                                        ContaDAO contaUp = contaController.getContaUser(idCont, vetorConta);

                                        //TRANFERENCIA 500K PARA NOVA CONTA
                                        operacoesContaController.depositoSaque(contaAdm, contaUp, 500000, false, 3, "Bônus de Cadastro", calendario);
                                        operacoesContaController.depositoSaque(contaUp, contaAdm, 500000, true, 3, "Bônus de Cadastro", calendario);
                                        
                                        //DEPOSITO AUTOMATICO DE 20K
                                        operacoesContaController.depositoSaque(contaUp, contaAdm, 20000, true, 1, "Depósito Automático", calendario);

                                        JOptionPane.showMessageDialog (null, "Usuário e Conta Criados");    
                                    } else {
                                        JOptionPane.showMessageDialog (null, "Máximo de Usuários Cadastrados, exclua um usuário");
                                    }     
                                break;

                                case 2://EDITAR USUÁRIO
                                    do {
                                        opUpdate = 0;
                                        if(!vetorCliente.isEmpty()){
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
                    
                                                        objUpdate.put("NOME", JOptionPane.showInputDialog("Nome: "     + clienteUdpdate.nome + "\n\nDigite novo Nome"));
                                                        objUpdate.put("LOGIN", JOptionPane.showInputDialog("Usuário: "  + clienteUdpdate.login +"\n\nDigite novo Usuário"));
                                                        objUpdate.put("SENHA", JOptionPane.showInputDialog("Senha: "    + clienteUdpdate.senha +"\n\nEscolha nova Senha"));
                                                        objUpdate.put("ENDERECO", JOptionPane.showInputDialog("Endereço: " + clienteUdpdate.endereco +"\n\nDigite novo Endereço"));
                                                        objUpdate.put("CPF", JOptionPane.showInputDialog("CPF: "      + clienteUdpdate.CPF +"\n\nDigite novo CPF"));
                                                        objUpdate.put("TELEFONE", JOptionPane.showInputDialog("Telefone: " + clienteUdpdate.telefone +"\n\nDigite novo Telefone"));
                                                        objUpdate.put("DTMODIFICACAO", formatBanco.format(calendario.getTime()));
                                                        dbConn.update("CLIENTE", "IDCLIENTE", clienteUdpdate.id, objUpdate);
                                                        vetorCliente = clienteController.search();
                                                        user = clienteController.verifyUserName(vetorCliente, userName);
                                                        JOptionPane.showMessageDialog(null,"Perfil Atualizado");
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

                                case 3://MOSTRAR USUÁRIOS

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
                                                        : "\nData de Modificação: " + formatExtrato.format(formatBanco.parse(clienteView.dataModificacao));
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

                                            if(clienteController.verify(idDelete, vetorCliente)) {
                                                    verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                                    if(verifySenha.hashCode() == user.senha.hashCode()){
                                                        
                                                        ContaDAO contaDel = contaController.getContaByCliente(idDelete, vetorConta);
                                                        dbConn.delete("CONTA", "IDCONTA", contaDel.id);
                                                        dbConn.delete("CLIENTE", "IDCLIENTE", idDelete);
                                                        vetorCliente = clienteController.search();
                                                        vetorConta = contaController.search();
                                                        JOptionPane.showMessageDialog(null,"Usuário Excluído");
                                                        
                                                        opDelete = 1;
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,"Senha Inválida");
                                                    }
                                            } else {

                                                JOptionPane.showMessageDialog(null,"Usuário não encontrado");
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
                                                        contaView = contaController.getContaByCliente(clienteViewConta.id, vetorConta);
                                                    }
                                                    opConta = Integer.parseInt(JOptionPane.showInputDialog(menuADMConta));             
                                                do {
                                                        internConta = 1;
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
                                                                int opOffset = 0;
                                                                int pag = 1;
                                                                String dataInicial = JOptionPane.showInputDialog("Digite a data inicial?\n Ex: 01/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                                String dataFinal = JOptionPane.showInputDialog("Digite a data final?\n Ex: 02/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                                do{
                                                                    String extrato = "";
                                                                    if(dataInicial.hashCode() == "0".hashCode() || dataFinal.hashCode() == "0".hashCode()) {
                                                                        opExtrato = 0;
                                                                        break;
                                                                    }
                                                                    Date dtinicio = format.parse(dataInicial + " 00:00:00");
                                                                    Date dtfinal =  format.parse(dataFinal + " 23:59:59");
                                                                    if(dataInicial.hashCode() == dataFinal.hashCode()) {
                                                                        GregorianCalendar aux = new GregorianCalendar();
                                                                        aux.setTime(dtfinal);
                                                                        aux.add(GregorianCalendar.DAY_OF_MONTH, 1);
                                                                        dtfinal = aux.getTime();
                                                                    } 

                                                                    vetorOperacoes = operacoesContaController.search(contaView.id, opOffset, formatBanco.format(dtinicio), formatBanco.format(dtfinal));
                                                                    ClienteDAO pagador = new ClienteDAO();
                                                                    ContaDAO contaPagador = new ContaDAO();
                                                                    for (OperacoesContaDAO element : vetorOperacoes) {
                                                                        if(element.tipo == 5 || element.tipo == 3){
                                                                            contaPagador = contaController.getContaUser(element.contaTransferencia, vetorConta);
                                                                            pagador = clienteController.returnObjectById(contaPagador.cliente, vetorCliente);
                                                                        }

                                                                        extrato += (element.operacao == 2) ? "Saida\n" : "Entrada\n";
                                                                        extrato += (element.tipo == 5) ? "Recebido de: " + pagador.nome + "\n" : "";
                                                                        extrato += (element.tipo == 3) ? "Enviado para: " + pagador.nome + "\n" : "";
                                                                        extrato += (element.tipo == 1) ? "Tipo: Depósito\n" : (element.tipo == 2) ? "Tipo: Saque\n" 
                                                                                : (element.tipo == 3) ? "Tipo: Transferência\n" : (element.tipo == 4) ? "Tipo: Pagamento\n" : "Tipo: Recebimento\n";
                                                                        extrato += "Data: " + formatExtrato.format(formatBanco.parse(element.dataCriacao))  + "\n"+
                                                                                    "Valor: " + number.format(element.valor)  + "\n" +
                                                                                "Saldo Final: " + number.format(element.saldoFinal) + "\n------------------------------\n";
                                                                    }
                                                                    
                                                                    Map<String, Set> vetores = new LinkedHashMap<String, Set>();
                                                                    vetores.put("Conta", vetorConta);
                                                                    vetores.put("Cliente", vetorCliente);

                                                                    Map<String, String> datas = new LinkedHashMap<String, String>();
                                                                    datas.put("Inicio", formatBanco.format(dtinicio));
                                                                    datas.put("Final", formatBanco.format(dtfinal));
                                                                    if(vetorOperacoes.size() > 4 && opOffset == 0) {
                                                                        int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n1 - Próximo\n0 - Voltar\nPágina: " + pag));
                                                                        if(aux == 1) {
                                                                            opOffset+=5;
                                                                            pag++;
                                                                            continue;
                                                                        } else if (aux == 0) {
                                                                            opExtrato = 0;
                                                                            break;
                                                                        } else if (aux == 9) {
                                                                            pdfController.gerarPdf(contaView, vetores, datas);
                                                                        }
                                                                    } else if (vetorOperacoes.size() > 4 && opOffset >= 1) {
                                                                        int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n1 - Próximo\n2 - Anterior\n0 - Voltar\nPágina: " + pag));
                                                                        if(aux == 1) {
                                                                            opOffset+=5;
                                                                            pag++;
                                                                            continue;
                                                                        } else if (aux == 0) {
                                                                            opExtrato = 0;
                                                                            break;
                                                                        } else if (aux == 2) {
                                                                            opOffset-=5;
                                                                            pag--;
                                                                            continue;
                                                                        } else if (aux == 9) {
                                                                            pdfController.gerarPdf(contaView, vetores, datas);
                                                                        }
                                                                    } else if (vetorOperacoes.size() <= 4 && opOffset >= 1){
                                                                        if(vetorOperacoes.size() == 0) extrato = "Não há mais resultados";
                                                                        int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n2 - Anterior\n0 - Voltar\nPágina: " + pag));
                                                                        if(aux == 2) {
                                                                            opOffset-=5;
                                                                            pag--;
                                                                            continue;
                                                                        } else if (aux == 0) {
                                                                            opExtrato = 0;
                                                                            break;
                                                                        } else if (aux == 9) {
                                                                            pdfController.gerarPdf(contaView, vetores, datas);
                                                                        }
                                                                    } else {
                                                                        if(vetorOperacoes.size() == 0) {
                                                                            extrato = "Nenhuma movimentação encontrada";
                                                                            JOptionPane.showMessageDialog(null, extrato);
                                                                            break;
                                                                        }
                                                                        int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n0 - Voltar\nPágina: " + pag));
                                                                        if (aux == 0) {
                                                                            opExtrato = 0;
                                                                            break;
                                                                        } else if (aux == 9) {
                                                                            pdfController.gerarPdf(contaView, vetores, datas);
                                                                        }
                                                                    }
                                                                } while (opExtrato != 0);
                                                            break;

                                                            case 3://EDITAR CONTA
                                                                verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                                                if(verifySenha.hashCode() == user.senha.hashCode()){
                                                                    int updateConta = Integer.parseInt(JOptionPane.showInputDialog("1 - Adicionar Saldo\n2 - Remover Saldo\n\n0 - Voltar\nDigite Uma Opção"));
                                                                    do {
                                                                        double movimentConta = 0.0;
                                                                        String text = " ";
                                                                        switch (updateConta) {
                                                                            case 0:
                                                                                break;
                                                                            case 1:
                                                                                movimentConta = Double.parseDouble(JOptionPane.showInputDialog("1 - Quanto deseja Adicionar?\n\n0 - Cancelar"));
                                                                                if(movimentConta == 0.0){
                                                                                    break;
                                                                                }
                                                                                contaView.setSaldo(contaView.saldo + movimentConta);
                                                                                objUpdate.put("IDCONTA", Integer.toString(contaView.id));                                        
                                                                                objUpdate.put("IDCONTADEST", Integer.toString(contaView.id));                                        
                                                                                objUpdate.put("OPERACAO", Integer.toString(1));                                        
                                                                                objUpdate.put("TIPO", Integer.toString(1));                                        
                                                                                objUpdate.put("SALDOFINAL", Double.toString(contaView.saldo + movimentConta));
                                                                                objUpdate.put("VALOROP", Double.toString(movimentConta));
                                                                                objUpdate.put("DESCRICAO", "Depósito de Cadastro");
                                                                                objUpdate.put("DTCRIACAO", formatBanco.format(calendario.getTime()));  
                                                                                dbConn.insert("OPERACOES", objUpdate);
                                                                                text = "Saldo Adicionado";
                                                                                break;
                                                                            case 2:
                                                                                movimentConta = Double.parseDouble(JOptionPane.showInputDialog("1 - Quanto deseja Remover?"));
                                                                                if(movimentConta == 0.0){
                                                                                    break;
                                                                                }
                                                                                contaView.saldo -= movimentConta;
                                                                                contaView.newData(contaView.id, contaView.cliente, contaView.saldo, contaView.dataCriacao, contaView.dataModificacao);
                                                                                if((contaView.saldo - movimentConta) >= 0) {
                                                                                    contaView.setSaldo(contaView.saldo - movimentConta);
                                                                                    text = "Saldo Removido";
                                                                                } else {
                                                                                    JOptionPane.showMessageDialog(null,"Não possui saldo suficiente");
                                                                                };
                                                                                break;
                                                                        }
                                                                        if(updateConta > 0) {
                                                                            objUpdate.put("SALDO", Double.toString(contaView.saldo));
                                                                            int result = dbConn.update("CONTA", "IDCONTA", contaView.id, objUpdate);
                                                                            text = (result == 0) ? "Ocorreu um erro durante a operação" : text;
                                                                            JOptionPane.showMessageDialog(null,text);
                                                                            updateConta = 0;
                                                                        }
                                                                    } while(updateConta != 0);
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,"Senha Inválida");
                                                                }
                                                            break;
                                                        }
                                                        internConta = 0;
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
                                            cobrancaDeTaxa.cobrarTaxa(calendario, vetorConta);
                                            JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção " + formatDate.format(calendario.getTime()));  
                                        }
                                    }

                                break;

                                case 7://dividendos
                                    // int id_Ordem = Integer.parseInt(JOptionPane.showInputDialog(book.Ativos_book() + "\n\nQual \"ID\" do ativo:"));
                                    do {
                                        opUpdate = 0;
                                        if(vetorCliente.size() != 0){
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
                                            ClienteDAO clienteUdpdate = clienteController.returnObjectById(idUpdate, vetorCliente);//CLIENTE
                                            ContaDAO contaUdpdate = contaController.getContaByCliente(clienteUdpdate.id, vetorConta);//CONTA CLIENTE
                                            if(clienteUdpdate != null) {
                                                verifySenha = JOptionPane.showInputDialog("Confirme a senha do Administrador");
                                                if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                    int id_ativo = Integer.parseInt(JOptionPane.showInputDialog(book.Ativos_book() + "\n\nQual \"ID\" do ativo:"));
                                                    Double preco_dividendo = Double.parseDouble(JOptionPane.showInputDialog("Qual valor do dividendo"));
                                                    Double dividendo = book.get_quantidadeordenscompra(contaUdpdate.id,id_ativo) * preco_dividendo; //Valor do dividendo
                                                    JOptionPane.showMessageDialog(null,"R$ " + dividendo);// pode apagar essa linha
                                                    
                                                    //DEVOLVE O SALDO CALCULADO
                                                    operacoesContaController.depositoSaque(contaAdm, contaUdpdate, dividendo, false, 4, "", calendario);
                                                    operacoesContaController.depositoSaque(contaUdpdate, contaAdm, dividendo, true, 5, "", calendario);

                                                    JOptionPane.showMessageDialog(null,"Dividendo Depositado");

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

                                case 8://ORDEM****************************************
                                    //CRIAR AQUI O BOOKING DE OFERTAS
                                    NewOrdenDAO Ordem = new NewOrdenDAO(contaAdm.id);
                                    Ordem.setIDATIVO(Integer.parseInt(JOptionPane.showInputDialog(book.Ativos_book() + "\n\nQual \"ID\" do ativo:")));
                                    do {
                                        Ordem.setTIPOORDEN(Integer.parseInt(JOptionPane.showInputDialog("Qual tipo de Ordem:\n"
                                        + "\n0 - Ordem 0\n1 - Compra\n2 - Venda \n")));
                                    } while (Ordem.getTIPOORDEN() < 0 || Ordem.getTIPOORDEN() > 2);
                                    if(Ordem.getTIPOORDEN() == 2 && book.get_quantidadeordenscompra(Ordem.getIDATIVO(), Ordem.getIDCONTA()) == 0 && contaAdm.id != 1){
                                        do {
                                            Ordem.setTIPOORDEN(Integer.parseInt(JOptionPane.showInputDialog("Você nao pode vender ações que não tem.\n0 - sair\n1 - Ordem 0\n2 - Compra ")));
                                        } while (Ordem.getTIPOORDEN() < 0 || Ordem.getTIPOORDEN() > 2);
                                        if (Ordem.getTIPOORDEN() == 0) {
                                            break;
                                        }
                                    }
                                    if (Ordem.getTIPOORDEN() == 0 && book.verificaOrdem_0(Ordem.getIDCONTA(), Ordem.getIDATIVO())) {
                                        do {
                                            Ordem.setTIPOORDEN(Integer.parseInt(JOptionPane.showInputDialog("Você nao pode fazer duas Ordem 0 para o mesmo ativo.\n0 - sair\n2 - Compra ")));
                                        } while (Ordem.getTIPOORDEN() != 0 && Ordem.getTIPOORDEN() != 2);
                                        if (Ordem.getTIPOORDEN() == 0) {
                                            break;
                                        }
                                    }
                                    if(Ordem.getTIPOORDEN() != 0)
                                        Ordem.setVALOR(Double.parseDouble(JOptionPane.showInputDialog("Preço para " + (Ordem.getTIPOORDEN() == 2 ? "Venda" : "Compra"))));
                                    else
                                        Ordem.setVALOR(0.00);
                                    do {
                                        Ordem.setQUANT(Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Ativos " + (Ordem.getTIPOORDEN() == 2 ? "a Vender"+ "(de 0 a" + book.get_quantidadeordenscompra(Ordem.getIDCONTA(), Ordem.getIDATIVO())+")" : "a comprar"))));    
                                    } while (Ordem.getTIPOORDEN() == 2 && (Ordem.getQUANT() > 0 && Ordem.getQUANT() >= book.get_quantidadeordenscompra(Ordem.getIDCONTA(), Ordem.getIDATIVO())) && contaAdm.id != 1);
                                    Ordem.setDATAORDEN(format.format(calendario.getTime()));
                                    
                                    double valor = Ordem.getVALOR() * Ordem.getQUANT();
                                    ContaDAO pagador = contaAdm ;
                                    ContaDAO recebedor = bolsa ;
                                    if(Ordem.getTIPOORDEN() == 2) {
                                        pagador = bolsa;
                                        recebedor = contaAdm;
                                    }
                                    
                                    
                                    if(book.Cadastro_Ordem(Ordem)) {
                                        String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");
                                        
                                        operacoesContaController.depositoSaque(pagador, recebedor, valor, false, 4, descricao, calendario);
                                        operacoesContaController.depositoSaque(recebedor, pagador, valor, true, 5, descricao, calendario); 
                                        JOptionPane.showMessageDialog(null, "Ordem de compra efetuada");

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Erro ao enviada Ordem ");
                                    }
                                

                                break;
                            } 
                        } while (op != 0);
                        JOptionPane.showMessageDialog(null, "Sessão Encerrada");
                        opUser = 1;

                    } else {
                           
                        //MENU DO USUÁRIO COMUM
                        ContaDAO conta = contaController.getContaByCliente(user.id, vetorConta);
                        // double investimentoInicial = 0;
                        JOptionPane.showMessageDialog(null, "Bem vindo " + user.nome + "!");
                        do{
                            String dataAtualStr = formatDate.format(calendario.getTime());
                            
                            //ROTINA DE COBRANÇA DE TAXA DE MANUTENÇÃO
                            if(calendario.get(calendario.DAY_OF_MONTH) == 15) {
                                cobrancaDeTaxa.cobrarTaxa(calendario, vetorConta);
                                JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção " + formatDate.format(calendario.getTime()));       

                            }
                        
                            if(verificador){
                                menuCOMConta = "Saldo: " + number.format(conta.saldo) + "\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
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
                                                        : "\nData de Modificação: " + formatExtrato.format(formatBanco.parse(user.dataModificacao));
                                                userClienteView += (user.adm == false) ? "\nAdministrador: Não" : "\nAdministrador: Sim";
                                                JOptionPane.showMessageDialog(null,userClienteView);
                                            break;

                                            case 2://EDITAR PERFIL
                                                verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                    
                                                    objUpdate.put("NOME", JOptionPane.showInputDialog("Nome: "     + user.nome + "\n\nDigite novo Nome"));
                                                    objUpdate.put("LOGIN", JOptionPane.showInputDialog("Usuário: "  + user.login +"\n\nDigite novo Usuário"));
                                                    objUpdate.put("SENHA", JOptionPane.showInputDialog("Senha: "    + user.senha +"\n\nEscolha nova Senha"));
                                                    objUpdate.put("ENDERECO", JOptionPane.showInputDialog("Endereço: " + user.endereco +"\n\nDigite novo Endereço"));
                                                    objUpdate.put("CPF", JOptionPane.showInputDialog("CPF: "      + user.CPF +"\n\nDigite novo CPF"));
                                                    objUpdate.put("TELEFONE", JOptionPane.showInputDialog("Telefone: " + user.telefone +"\n\nDigite novo Telefone"));
                                                    objUpdate.put("DTMODIFICACAO", formatBanco.format(calendario.getTime()));
                                                    dbConn.update("CLIENTE", "IDCLIENTE", user.id, objUpdate);
                                                    vetorCliente = clienteController.search();
                                                    user = clienteController.verifyUserName(vetorCliente, userName);
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
                                                    int opOffset = 0;
                                                    int pag = 1;
                                                    String dataInicial = JOptionPane.showInputDialog("Digite a data inicial?\n Ex: 01/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                    String dataFinal = JOptionPane.showInputDialog("Digite a data final?\n Ex: 02/01/2022\n\n0 - Voltar\nDigite uma opção");
                                                    do{
                                                        String extrato = "";
                                                        if(dataInicial.hashCode() == "0".hashCode() || dataFinal.hashCode() == "0".hashCode()) {
                                                            opExtrato = 0;
                                                            break;
                                                        }
                                                        Date dtinicio = format.parse(dataInicial + " 00:00:00");
                                                        Date dtfinal =  format.parse(dataFinal + " 23:59:59");
                                                        if(dataInicial.hashCode() == dataFinal.hashCode()) {
                                                            GregorianCalendar aux = new GregorianCalendar();
                                                            aux.setTime(dtfinal);
                                                            aux.add(GregorianCalendar.DAY_OF_MONTH, 1);
                                                            dtfinal = aux.getTime();
                                                        } 

                                                        vetorOperacoes = operacoesContaController.search(conta.id, opOffset, formatBanco.format(dtinicio), formatBanco.format(dtfinal));
                                                        ClienteDAO pagador = new ClienteDAO();
                                                        ContaDAO contaPagador = new ContaDAO();
                                                        for (OperacoesContaDAO element : vetorOperacoes) {
                                                            if(element.tipo == 5 || element.tipo == 3){
                                                                contaPagador = contaController.getContaUser(element.contaTransferencia, vetorConta);
                                                                pagador = clienteController.returnObjectById(contaPagador.cliente, vetorCliente);
                                                            }

                                                            extrato += (element.operacao == 2) ? "Saida\n" : "Entrada\n";
                                                            extrato += (element.tipo == 5) ? "Recebido de: " + pagador.nome + "\n" : "";
                                                            extrato += (element.tipo == 3) ? "Enviado para: " + pagador.nome + "\n" : "";
                                                            extrato += (element.tipo == 1) ? "Tipo: Depósito\n" : (element.tipo == 2) ? "Tipo: Saque\n" 
                                                                    : (element.tipo == 3) ? "Tipo: Transferência\n" : (element.tipo == 4) ? "Tipo: Pagamento\n" : "Tipo: Recebimento\n";
                                                            extrato += "Data: " + formatExtrato.format(formatBanco.parse(element.dataCriacao)) + "\n"+
                                                                        "Valor: " + number.format(element.valor)  + "\n" +
                                                                    "Saldo Final: " + number.format(element.saldoFinal) + "\n------------------------------\n";
                                                        }
                                                        
                                                        Map<String, Set> vetores = new LinkedHashMap<String, Set>();
                                                        vetores.put("Conta", vetorConta);
                                                        vetores.put("Cliente", vetorCliente);

                                                        Map<String, String> datas = new LinkedHashMap<String, String>();
                                                        datas.put("Inicio", formatBanco.format(dtinicio));
                                                        datas.put("Final", formatBanco.format(dtfinal));
                                                        if(vetorOperacoes.size() > 4 && opOffset == 0) {
                                                            int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n1 - Próximo\n0 - Voltar\nPágina: " + pag));
                                                            if(aux == 1) {
                                                                opOffset+=5;
                                                                pag++;
                                                                continue;
                                                            } else if (aux == 0) {
                                                                opExtrato = 0;
                                                                break;
                                                            } else if (aux == 9) {
                                                                pdfController.gerarPdf(conta, vetores, datas);
                                                            }
                                                        } else if (vetorOperacoes.size() > 4 && opOffset >= 1) {
                                                            int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n1 - Próximo\n2 - Anterior\n0 - Voltar\nPágina: " + pag));
                                                            if(aux == 1) {
                                                                opOffset+=5;
                                                                pag++;
                                                                continue;
                                                            } else if (aux == 0) {
                                                                opExtrato = 0;
                                                                break;
                                                            } else if (aux == 2) {
                                                                opOffset-=5;
                                                                pag--;
                                                                continue;
                                                            } else if (aux == 9) {
                                                                pdfController.gerarPdf(conta, vetores, datas);
                                                            }
                                                        } else if (vetorOperacoes.size() <= 4 && opOffset >= 1){
                                                            if(vetorOperacoes.size() == 0) extrato = "Não há mais resultados";
                                                            int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n2 - Anterior\n0 - Voltar\nPágina: " + pag));
                                                            if(aux == 2) {
                                                                opOffset-=5;
                                                                pag--;
                                                                continue;
                                                            } else if (aux == 0) {
                                                                opExtrato = 0;
                                                                break;
                                                            } else if (aux == 9) {
                                                                pdfController.gerarPdf(conta, vetores, datas);
                                                            }
                                                        } else {
                                                            if(vetorOperacoes.size() == 0) {
                                                                extrato = "Nenhuma movimentação encontrada";
                                                                JOptionPane.showMessageDialog(null, extrato);
                                                                break;
                                                            }
                                                            int aux = Integer.parseInt(JOptionPane.showInputDialog(extrato + "\n\n9 - Salvar Extrato\n0 - Voltar\nPágina: " + pag));
                                                            if (aux == 0) {
                                                                opExtrato = 0;
                                                                break;
                                                            } else if (aux == 9) {
                                                                pdfController.gerarPdf(conta, vetores, datas);
                                                            }
                                                        }
                                                    } while (opExtrato != 0);
                                                break;

                                                case 2://TRANSFERÊNCIA
                                                    int opTransferencia = Integer.parseInt(JOptionPane.showInputDialog("Para qual conta você deseja transferir? Digite apenas números\n\n0 - Voltar\nDigite uma opção")); 
                                                    do{
                                                        if(opTransferencia == 0){
                                                            break;
                                                        }
                                                        ContaDAO contaDE = contaController.getContaUser(opTransferencia, vetorConta);
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
                                                            
                                                            String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");     
                                                            operacoesContaController.depositoSaque(conta, contaDE, valorTransferencia, false, 3, descricao, calendario);
                                                            operacoesContaController.depositoSaque(contaDE, conta, valorTransferencia, true, 3, descricao, calendario);

                                                            if(verificador){
                                                                    menuCOMConta = "Saldo: " + number.format(conta.saldo) + "\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
                                                            }
                                                            JOptionPane.showMessageDialog(null,"Transferência realizada");    
                                                                    
                                                         
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
                                                            String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional)");
                                                            operacoesContaController.depositoSaque(conta, conta, opDeposito, true, 1, descricao, calendario);
                                                            JOptionPane.showMessageDialog(null,"Depósito realizado");  

                                                            if(verificador){
                                                                menuCOMConta = "Saldo: " + number.format(conta.saldo) + "\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
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
                                                        
                                                        verifySenha = JOptionPane.showInputDialog("Confirme sua senha");
                                                        if(verifySenha.hashCode() == user.senha.hashCode()){//VERIFICAR SENHA
                                                                
                                                                String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");
                                                                operacoesContaController.depositoSaque(conta, conta, opSaque, false, 2, descricao, calendario);
                                                            
                                                                JOptionPane.showMessageDialog(null,"Saque realizado"); 
                                                                
                                                                if(verificador){
                                                                    menuCOMConta = "Saldo: " + number.format(conta.saldo) + "\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
                                                                }   
                                                            } else {
                                                                JOptionPane.showMessageDialog(null,"Senha inválida"); 
                                                            }
                                                        opSaque = 0;
                                                    }while(opSaque != 0);
                                                break;

                                                case 5://MOSTRAR SALDO
                                                    verificador = (verificador == true) ? false : true;
                                                    String auxSaldo = "Saldo: " + number.format(conta.saldo) + "\n1 - Extrato\n2 - Tranferência\n3 - Depósito\n4 - Saque\n5 - Ocultar Saldo\n\n0 - Voltar\nDigite uma opção";
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
                                    NewOrdenDAO Ordem = new NewOrdenDAO(conta.id);
                                    Ordem.setIDATIVO(Integer.parseInt(JOptionPane.showInputDialog(book.Ativos_book() + "\n\nQual \"ID\" do ativo:")));
                                    
                                    do {
                                        Ordem.setTIPOORDEN(Integer.parseInt(JOptionPane.showInputDialog("Qual tipo de Ordem:\n"
                                        + "\n0 - Ordem 0\n1 - Compra\n2 - Venda \n")));
                                    } while (Ordem.getTIPOORDEN() < 0 || Ordem.getTIPOORDEN() > 2);
                                    if(Ordem.getTIPOORDEN() == 2 && book.get_quantidadeordenscompra(conta.id,Ordem.getIDATIVO()) == 0 && conta.id != 0){
                                        do {
                                            Ordem.setTIPOORDEN(Integer.parseInt(JOptionPane.showInputDialog("Você nao pode vender ações que não tem.\n0 - sair\n1 - Ordem 0\n2 - Compra ")));
                                        } while (Ordem.getTIPOORDEN() < 0 || Ordem.getTIPOORDEN() > 2);
                                        if (Ordem.getTIPOORDEN() == 0) {
                                            break;
                                        }
                                    }
                                    if (Ordem.getTIPOORDEN() == 0 && book.verificaOrdem_0(conta.id, Ordem.getIDATIVO())) {
                                        do {
                                            Ordem.setTIPOORDEN(Integer.parseInt(JOptionPane.showInputDialog("Você nao pode fazer duas Ordem 0 para o mesmo ativo.\n0 - sair\n2 - Compra ")));
                                        } while (Ordem.getTIPOORDEN() != 0 && Ordem.getTIPOORDEN() != 2);
                                        if (Ordem.getTIPOORDEN() == 0) {
                                            break;
                                        }
                                    }
                                    if(Ordem.getTIPOORDEN() != 0)
                                        Ordem.setVALOR(Double.parseDouble(JOptionPane.showInputDialog("Preço para " + (Ordem.getTIPOORDEN() == 2 ? "Venda" : "Compra"))));
                                    else
                                        Ordem.setVALOR(0.00);
                                    do {
                                        Ordem.setQUANT(Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Ativos " + (Ordem.getTIPOORDEN() == 2 ? "a Vender"+ "(de 0 a" + book.get_quantidadeordenscompra(Ordem.getIDCONTA(),Ordem.getIDATIVO())+")" : "a comprar"))));    
                                    } while (Ordem.getTIPOORDEN() == 2 && (Ordem.getQUANT() > 0 && Ordem.getQUANT() >= book.get_quantidadeordenscompra(Ordem.getIDCONTA(),Ordem.getIDATIVO())) && conta.id != 0);
                                    Ordem.setDATAORDEN(format.format(calendario.getTime()));
                                    
                                    double valor = Ordem.getQUANT()*Ordem.getVALOR();
                                    ContaDAO pagador = conta ;
                                    ContaDAO recebedor = bolsa ;
                                    if(Ordem.getTIPOORDEN() == 2) {
                                        pagador = bolsa;
                                        recebedor = conta;
                                    }

                                    
                                    if(book.Cadastro_Ordem(Ordem)) {
                                        String descricao = JOptionPane.showInputDialog("Adicione uma descrição (Opcional");
                                        
                                        operacoesContaController.depositoSaque(recebedor, pagador, valor, true, 5, descricao, calendario);
                                        operacoesContaController.depositoSaque(pagador, recebedor, valor, false, 4, descricao, calendario);
                                        JOptionPane.showMessageDialog(null, "Ordem de compra efetuada");

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Erro ao enviada Ordem ");
            
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
                                        if(calendario.get(calendario.DAY_OF_MONTH)== 15) {
                                            cobrancaDeTaxa.cobrarTaxa(calendario, vetorConta);
                                            JOptionPane.showMessageDialog(null, "Foi debitado a taxa de manutenção " + formatDate.format(calendario.getTime()));  
                                        }
                                    }
                                    
                                break;
                                case 6://liteus
                                    String Meus_ativos = book.getMeu_ativo(conta.id);
                                    if(Meus_ativos == "")
                                        JOptionPane.showMessageDialog(null,"Você não tem ativos");
                                    else
                                        JOptionPane.showMessageDialog(null, Meus_ativos);
                                break;
                                case 7://listar ativos bolsa
                                    String Book_ativos = book.getBook_ativo();
                                    if(Book_ativos == "")
                                        JOptionPane.showMessageDialog(null,"Não a ativos, nem para compra ou venda");
                                    else
                                        JOptionPane.showMessageDialog(null, Book_ativos);
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
            JOptionPane.showMessageDialog(null, "Erro, por favor, abrir chamado");
        }
        
    }
}
