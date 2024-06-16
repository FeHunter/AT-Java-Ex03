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

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Conta (int id, String nome, double saldo){
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "\nConta ID: " + id + ", Nome: " + nome + ", Saldo: $" + saldo;
    }

    public String formatarParaSalvarConta (){
        return id + "," + nome + "," + saldo;
    }
}
