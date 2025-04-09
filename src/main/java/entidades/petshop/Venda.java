package entidades.petshop;

import java.time.LocalDateTime;

public class Venda {
    private String nomeProduto;
    private double precoUnitario;
    private int quantidadeVendida;
    private LocalDateTime dataHora;

    public Venda(String nomeProduto, double precoUnitario, int quantidadeVendida) {
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidadeVendida = quantidadeVendida;
        this.dataHora = LocalDateTime.now();
    }

    public String getNomeProduto() { return nomeProduto; }
    public double getPrecoUnitario() { return precoUnitario; }
    public int getQuantidadeVendida() { return quantidadeVendida; }
    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() {
        return String.format("%s - %d x R$%.2f em %s",
                nomeProduto, quantidadeVendida, precoUnitario, dataHora.toString().replace("T", " "));
    }
}
