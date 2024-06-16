import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        final String ARQUIVO = "C:\\Users\\Felipe\\Documents\\Programmer\\Faculdade\\Back-End\\Java\\AT\\AT Java Ex03\\src\\Contas.csv";
        Boolean fim = false;

        List<Conta> contas = new ArrayList<>();
        contas = lerArquivo(ARQUIVO);

        while (!fim){
            int op = menu();

            switch (op){
                case 4:
                    mostrarContas(contas);
                    break;
                case 5:
                    fim = true;
                    break;
            }
        }
    }

    static int menu (){
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu sistema de contas");
        System.out.println("1 - Incluir Conta");
        System.out.println("2 - Alterar Saldo");
        System.out.println("3 - Excluir Conta");
        System.out.println("4 - Consultar Conta");
        System.out.println("5 - Sair");
        int op = 0;
        try {
            do {
                op = sc.nextInt();
            }while (op < 1 || op > 5);
        }catch (Exception e){
            System.out.println("Você deve escolher uma das opções do menu");
        }
        return op;
    }

    static List<Conta> lerArquivo (String arquivo){
        List<Conta> adicionarContas = new ArrayList<>();
        File arq = new File(arquivo);
        try {
            BufferedReader br = new BufferedReader(new FileReader(arq));
            String linha = br.readLine();
            while (linha != null){
                String[] ler = linha.split(",");
                int id = Integer.parseInt(ler[0]);
                String nome = ler[1];
                double saldo = Double.parseDouble(ler[2]);
                adicionarContas.add( new Conta(id, nome, saldo) );
                linha = br.readLine();
            }
            return adicionarContas;
        }
        catch (IOException e){
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return adicionarContas;
    }

    static  void mostrarContas (List<Conta> contas){
        for (Conta conta : contas){
            System.out.println(conta.toString());
        }
    }
}