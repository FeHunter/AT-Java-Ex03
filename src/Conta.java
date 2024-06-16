public class Conta {
    private int id;
    private String nome;
    private double saldo;

    public int getID (){
        return id;
    }
    public String getNome (){
        return nome;
    }
    public double getSaldo (){
        return saldo;
    }

    public Conta (int id, String nome, double saldo){
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
    }

}
