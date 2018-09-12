package pkg01.geradorprova;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class GeradorProva {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        String disciplina = JOptionPane.showInputDialog("Digite o nome da disciplina: ");
        
        Prova prova = new Prova(disciplina);

        prova.setLocal(JOptionPane.showInputDialog("Digite o local da prova: "));
        
        prova.setData(JOptionPane.showInputDialog("Digite a data da prova: "));
       
        while(true){
            try{
                prova.setPeso(Integer.parseInt(JOptionPane.showInputDialog("Digite o peso da prova: ")));
                break;
            }catch(Exception a){
                JOptionPane.showMessageDialog(null, a.getMessage());
                JOptionPane.showMessageDialog(null, a"Digite novamente.");
                continue;
            }
        }
        
        String continuar;
        ArrayList<Questao> aux = new ArrayList<Questao>();
        do{
            String opcao;
            
            do{
                opcao = JOptionPane.showInputDialog("Digite D para Discursiva ou O pra Objetiva");
             
                if(opcao.compareTo("O")!=0 && opcao.compareTo("o")!=0 && 
                   opcao.compareTo("D")!=0 && opcao.compareTo("d")!=0)
                    JOptionPane.showInputDialog("Tipo de questao nao identificado.");
                
            }while(opcao.compareTo("O")!=0 && opcao.compareTo("o")!=0 && 
                   opcao.compareTo("D")!=0 && opcao.compareTo("d")!=0);

            if(opcao.compareTo("O")==0 || opcao.compareTo("o")==0){
                Objetiva aux2 = new Objetiva();
                
                System.out.println("Digite a questao objetiva: ");
                aux2.setPergunta(scan.nextLine());
                
                System.out.println("Digite o peso da questao: ");
                while(true){
                    try{
                        aux2.setPeso(Integer.parseInt(scan.nextLine()));
                        break;
                    }catch(Exception a){
                        System.out.println(a.getMessage());
                        System.out.println("Digite novamente.");
                        continue;
                    }
                }
                
                String[] alternativas = new String[5];
                for(int i=0; i<5; i++){
                    System.out.println("Digite a alternativa "+(i+1)+": ");
                    alternativas[i] = scan.nextLine();
                }
                aux2.setOpcoes(alternativas);
                
                System.out.println("Digite a alternativa correta: ");
                while(true){
                    try{
                        aux2.setRespostaCorreta((Integer.parseInt(scan.nextLine())+1));
                        if(aux2.getRespostaCorreta()<1 || aux2.getRespostaCorreta()>5)
                            throw new IllegalArgumentException();
                        break;
                    }catch(NumberFormatException a){
                        System.out.println(a.getMessage());
                        System.out.println("Digite novamente.");
                        continue;
                    }catch(IllegalArgumentException e){
                        System.out.println("Alternativa correta precisa estar entre 1 e 5.");
                        System.out.println("Digite novamente.");
                        continue;
                    }
                }
                
                aux.add(aux2);
            }else{
                Discursiva aux2 = new Discursiva();
                
                System.out.println("Digite a questao discursiva: ");
                aux2.setPergunta(scan.nextLine());
                
                System.out.println("Digite o peso da questao: ");
                while(true){
                    try{
                        aux2.setPeso(Integer.parseInt(scan.nextLine()));
                        break;
                    }catch(Exception a){
                        System.out.println(a.getMessage());
                        System.out.println("Digite novamente.");
                        continue;
                    }   
                }
                
                System.out.println("Digite o criterio de correcao: ");
                aux2.setCriteriosCorrecao(scan.nextLine());
                
                aux.add(aux2);
            }
            
            System.out.println("Deseja adicionar mais uma questao? S/N");
            continuar = scan.nextLine();
        }while(continuar.compareTo("S")==0 || continuar.compareTo("s")==0);
        prova.setQuestoes(aux);
        
        //System.out.println(prova.obtemProvaImpressa());
        System.out.println("\nDigite o nome que deseja salvar o arquivo: ");
        String nomeArquivo = scan.nextLine();
        try{
            File arquivo = new File(nomeArquivo+".txt");
            FileWriter escritor = new FileWriter(arquivo);
            PrintWriter saida = new PrintWriter(escritor);
            saida.println(prova.obtemProvaImpressa());
            saida.close();
        }catch(Exception e){
            System.out.println("Falha ao salvar em arquivo.");
        }
    }
}
