package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import dao.ClienteDAO;
import dao.ContaDAO;
import dao.OperacoesContaDAO;
// import javafx.stage.WindowEvent;

public class PDFController {
    
    public void gerarPdf (ContaDAO conta,  Map<String, Set> vetores, Map<String, String> datas) { 
        Document doc = new Document();
        JFileChooser chooser = new JFileChooser();
        JFrame parentFrame = new JFrame();
        int option = chooser.showSaveDialog(parentFrame);
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File file = new File("");
        ContaController contaController = new ContaController();
        ClienteController clienteController = new ClienteController();
        SimpleDateFormat formatBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatExtrato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        NumberFormat number = NumberFormat.getCurrencyInstance();
        OperacoesContaController operacoesController = new OperacoesContaController();   
        Set<OperacoesContaDAO> vetor = operacoesController.relatorio(conta.id, datas.get("Inicio"), datas.get("Final"));

        ContaDAO contaPagador = null;
        ClienteDAO pagador = null;
        ClienteDAO titular = clienteController.getClienteByConta(conta.id, vetores.get("Conta"), vetores.get("Cliente"));
        if (option == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            try { 
                PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()+".pdf"));
                doc.open();
                Paragraph titulo = new Paragraph();
                titulo.add(titular.nome);
                titulo.setAlignment(1);
                doc.add(titulo);
                Paragraph tituloConta = new Paragraph();
                tituloConta.add("Conta: " + conta.id + "\n\n");
                tituloConta.setAlignment(1);
                doc.add(tituloConta);
                int count = 0;
                for (OperacoesContaDAO element : vetor) {

                    if(element.tipo == 5 || element.tipo == 3){
                        contaPagador = contaController.getContaUser(element.contaTransferencia, vetores.get("Conta"));
                        pagador = clienteController.returnObjectById(contaPagador.cliente, vetores.get("Cliente"));
                    }

                    if(count == 4) doc.newPage();
                    doc.add(new Paragraph((element.operacao == 2) ? "Saida\n" : "Entrada\n"));
                    doc.add(new Paragraph((element.tipo == 5) ? "Recebido de: " + pagador.nome + "\n" : ""));
                    doc.add(new Paragraph((element.tipo == 3) ? "Enviado para: " + pagador.nome + "\n" : ""));
                    doc.add(new Paragraph((element.tipo == 1) ? "Tipo: Depósito\n" : (element.tipo == 2) ? "Tipo: Saque\n" 
                        : (element.tipo == 3) ? "Tipo: Transferência\n" : (element.tipo == 4) ? "Tipo: Pagamento\n" : "Tipo: Recebimento\n"));
                    doc.add(new Paragraph("Data: " + formatExtrato.format(formatBanco.parse(element.dataCriacao))  + "\n"+
                        "Valor: " + number.format(element.valor)  + "\n" +
                        "Saldo Final: " + number.format(element.saldoFinal)));
                    doc.add(new Paragraph("\nDescrição: " + element.descricao + "\n------------------------------\n"));
                    count++;
                }
                JOptionPane.showMessageDialog(null, "Relatório Salvo");
            } catch (Exception err) {
                IOException err2 = new IOException();
                String message = "Erro ao salvar relatório";
                JOptionPane.showMessageDialog(null, message);
            } finally {
                parentFrame.dispose();
                doc.close();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum diretório selecionado");
        }
    }
}
