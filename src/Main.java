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

    // ler saldo, id,  método

    static int verificarIdExistente (List<Conta> contas){
        int id = -1;
        boolean igual;
        do {
            igual = false;
            System.out.print("ID:");
            id = lerValorInteiro("Você deve digitar o Id da conta.");
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
        int id = lerIDExistente(contas);

        // Verificar tipo de operação
        int op = opDeAlterarSaldo();

        // Ler o valor a ser depositado ou sacado
        System.out.print("\nDigite o valor desejado:");
        double valor = lerValorDouble("Digite um valor valido");

        // Encontra a conta e guarda em uma variavel
        Conta conta = encontrarConta(contas, id);

        // Deposito
        if (op == 1){
            deposito(valor, conta);
        }
        // Saque
        if (op == 2) {
            saque(valor, conta);
        }
    }

    static int opDeAlterarSaldo (){
        int op;
        do {
            System.out.println("Escolha a operação: \n1 - Depositar\n2 - Sacar");
            op = lerValorInteiro("Escolha a operação: \n1 - Depositar\n2 - Sacar");
        }while (op < 1 || op > 2);
        return  op;
    }

    static void deposito (double valor, Conta conta){
        if (valor > 0){
            double novoSaldo = conta.getSaldo() + valor;
            conta.setSaldo(novoSaldo);
            System.out.println("Deposito efetuado com sucesso!");
        }else {
            System.out.println("Não é possivel depositar $0, tente novamente.");
        }
    }

    static void saque (double valor, Conta conta){
        if (conta.getSaldo() >= valor){
            double novoSaldo = conta.getSaldo() - valor;
            conta.setSaldo(novoSaldo);
            System.out.println("Saque efetuado com sucesso!");
        }else {
            System.out.println("Saldo insuficiente");
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
        int id = lerIDExistente(contas);

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

    // Valida se o ID digitado e de uma conta existente
    static int lerIDExistente (List<Conta> contas){
        int id = 0;
        // Ler id da conta e verificar se a conta existe.
        do {
            System.out.print("\nDigite o ID de uma conta existente: ");
            id = lerValorInteiro("Digite o ID de uma conta existente: ");
        }while (!verificarCotaExistente(contas, id));
        return  id;
    }

    static int lerValorInteiro (String mensagem){
        Scanner sc = new Scanner(System.in);
        int valor = -1;
        do {
            try {
                valor = sc.nextInt();
            }catch (Exception e){
                System.out.println(mensagem);
                sc.next(); // limpar entrada
            }
        }while (valor < 0);

        return valor;
    }

    static double lerValorDouble (String mensagem){
        Scanner sc = new Scanner(System.in);
        double valor = -1;
        do {
            try {
                valor = sc.nextInt();
            }catch (Exception e){
                System.out.println(mensagem);
                sc.next(); // limpar entrada
            }
        }while (valor < 0);

        return valor;
    }
}