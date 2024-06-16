import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int op = Menu();
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
            op = sc.nextInt();
        }catch (Exception e){
            System.out.println("Você deve escolher uma das opções do menu");
        }
        return op;
    }
}