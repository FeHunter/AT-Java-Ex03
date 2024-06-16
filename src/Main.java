import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Boolean fim = false;

        while (!fim){
            int op = Menu();

            switch (op){
                case 5:
                    fim = true;
                break;
            }
        }
    }

    static int Menu (){
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
}