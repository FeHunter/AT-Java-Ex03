public class Conta {
    private int id;
    private String nome;
    private double saldo;

    public int getID (){
        return id;
    }

    public double getSaldo (){
        return saldo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSaldo(double saldo) {
        if (saldo > 0){
            this.saldo = saldo;
        }else {
            this.saldo = 0;
        }
    }

    public Conta (int id, String nome, double saldo){
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
    }
    public Conta (){}

    @Override
    public String toString() {
        return "Conta ID: " + id + ", Nome: " + nome + ", Saldo: $" + saldo;
    }

    public String formatarParaSalvarConta (){
        return id + "," + nome + "," + saldo;
    }
}
