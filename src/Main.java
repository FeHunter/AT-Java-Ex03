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
                case 1:
                    adicionarConta(contas);
                    break;
                case 2:
                    alterarSaldo(contas);
                    break;
                case 3:
                    excluirConta(contas);
                    break;
                case 4:
                    mostrarContas(contas);
                    break;
                case 5:
                    // Finalizar e salva as contas no arquivo
                    fim = true;
                    salvarDadosNoArquivo(ARQUIVO, contas);
                    break;
            }
        }
    }

    static int menu (){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nMenu sistema de contas");
        System.out.println("1 - Incluir Conta");
        System.out.println("2 - Alterar Saldo");
        System.out.println("3 - Excluir Conta");
        System.out.println("4 - Consultar Contas");
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

    static  void adicionarConta (List<Conta> contas){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nAdicionar Conta:");

        int id = verificarIdExistente(contas);
        // Mostra erro e retorna ao menu
        if (id < 0){
            System.out.println("Você deve inserir um número valido para o ID da conta");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.next();

        double saldo = -1;
        do {
            // Empedir saldo negativo.
            System.out.print("Saldo: ");
            try {
                saldo = sc.nextDouble();
            }catch (Exception e){
                System.out.println("Você deve digitar o saldo em um formato valido");
                break;
            }
        }while (saldo < 0);

        if (saldo >= 0){
            contas.add( new Conta(id, nome, saldo) );
        }
    }

    static int verificarIdExistente (List<Conta> contas){
        Scanner sc = new Scanner(System.in);
        int id = -1;
        boolean igual;
        do {
            igual = false;
            System.out.print("ID:");
            try {
                id = sc.nextInt();
            }catch (Exception e){
                break;
            }
            for (int i =0; i < contas.size(); i++){
                if (contas.get(i).getID() == id){
                    System.out.println("Id já existente");
                    igual = true;
                }
            }
        }while (id < 0 || igual);
        return  id;
    }

    static void salvarDadosNoArquivo (String arquivo, List<Conta> contas){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
            for (Conta conta : contas){
                bw.write(conta.formatarParaSalvarConta() + "\n");
            }
            bw.close();
        }catch (IOException e){
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    static  void mostrarContas (List<Conta> contas){
        for (Conta conta : contas){
            System.out.println(conta.toString());
        }
    }

    static  void alterarSaldo (List<Conta> contas){
        Scanner sc = new Scanner(System.in);
        int id = lerID(contas);

        // Verificar tipo de operação
        int op = 0;
        try {
            do {
                System.out.println("Escolha a operação: \n1 - Depositar\n2 - Sacar");
                op = sc.nextInt();
            }while (op < 1 || op > 2);
        }catch (Exception e){
            System.out.println("Entrada invalida, você deve escolher entre as opções 1 e 2");
            return;
        }

        // Ler o valor a ser depositado ou sacado
        double valor = -1;
        do {
            // Empedir saldo negativo.
            try {
                do {
                    System.out.print("Digite o valor: ");
                    valor = sc.nextDouble();
                }while (valor < 0);
            }catch (Exception e){
                System.out.println("Você deve digitar o saldo em um formato valido");
                break;
            }
        }while (valor < 0);
        // procua pela conta atraves do ID e atualizar o saldo, somente se o saldo for maior que zero.
        if (valor != -1){
            // Encontra a conta e guarda em uma variavel
            Conta conta = encontrarConta(contas, id);
            // Deposito
            if (op == 1){
                if (valor > 0){
                    double novoSaldo = conta.getSaldo() + valor;
                    conta.setSaldo(novoSaldo);
                    System.out.println("Deposito efetuado com sucesso!");
                }else {
                    System.out.println("Não é possivel depositar $0, tente novamente.");
                }
            }
            // Saque
            if (op == 2) {
                if (conta.getSaldo() >= valor){
                    double novoSaldo = conta.getSaldo() - valor;
                    conta.setSaldo(novoSaldo);
                    System.out.println("Saque efetuado com sucesso!");
                }else {
                    System.out.println("Saldo insuficiente");
                }
            }
        }
    }

    static boolean verificarCotaExistente (List<Conta> contas,int id){
        boolean existe = false;
        for (int i=0; i < contas.size(); i++){
            if(contas.get(i).getID() == id){
                existe = true;
                break;
            }
        }
        return existe;
    }

    static void excluirConta (List<Conta> contas){
        System.out.println("Digite o ID da conta a ser excluida:");
        System.out.print("ID: ");
        int id = lerID(contas);

        // encontrar conta pelo ID
        Conta conta = encontrarConta(contas, id);
        if (conta == null){
            System.out.println("ID da conta não localizado, verifique e tente novamente");
        }else if (conta.getSaldo() == 0){
            contas.remove(conta);
            System.out.println("A conta de ID " + id + " foi removida.");
        }else {
            System.out.println("A conta de ID " + id + " não pode ser removida, o saldo deve esta zerado.");
        }
    }

    static Conta encontrarConta (List<Conta> contas, int id){
        Conta conta = null;
        for (int i=0; i < contas.size(); i++){
            if (contas.get(i).getID() == id){
                conta = contas.get(i);
            }
        }
        return conta;
    }

    static int lerID (List<Conta> contas){
        Scanner sc = new Scanner(System.in);
        int id = 0;
        // Ler id da conta e verificar se a conta existe.
        do {
            try{
                System.out.println("Digite o ID de uma conta existente");
                System.out.print("ID: ");
                id = sc.nextInt();
            }catch (Exception e){
                System.out.println("Você deve inserir um número valido para o ID da conta.");
                break;
            }
        }while (!verificarCotaExistente(contas, id));
        return  id;
    }
}