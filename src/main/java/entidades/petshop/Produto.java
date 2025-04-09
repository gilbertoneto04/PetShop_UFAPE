package entidades.petshop;

public class Produto {
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }
    public Produto(String nome, double preco) {
        this(nome, preco, 1);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public void decrementarQuantidade(int quantidadeVendida) {
        this.quantidade -= quantidadeVendida;
    }

    @Override
    public String toString() {
        return nome + " - R$" + preco + " - Estoque: " + quantidade;
    }
}
