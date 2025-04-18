package entidades.petshop;

import persistencia.JsonRepository;

import java.util.ArrayList;
import java.util.List;

public class Loja {
    private List<Produto> produtos;

    public Loja() {
        this.produtos = carregarProdutos();
    }

    // === Operações ===

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        salvarProdutos();
        System.out.println("✅ Produto adicionado: " + produto);
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("📭 Nenhum produto disponível.");
            return;
        }

        System.out.println("📦 Produtos disponíveis:");
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println((i + 1) + ". " + produtos.get(i));
        }
    }

    public void venderProduto(String nomeProduto, int quantidade) {
        Produto produto = produtos.stream()
            .filter(p -> p.getNome().equalsIgnoreCase(nomeProduto))
            .findFirst()
            .orElse(null);
    
        if (produto != null) {
            if (produto.getQuantidade() >= quantidade) {
                produto.setQuantidade(produto.getQuantidade() - quantidade);
                salvarProdutos();
                System.out.println("💸 Venda realizada: " + quantidade + "x " + produto.getNome());
    
                // Registrar no relatório de vendas
                Venda venda = new Venda(produto.getNome(), produto.getPreco(), quantidade);
                RelatorioVendas.registrarVenda(venda);
            } else {
                System.out.println("❌ Estoque insuficiente.");
            }
        } else {
            System.out.println("❌ Produto não encontrado.");
        }
    }
    // === Persistência ===

    private List<Produto> carregarProdutos() {
        List<Produto> lista = JsonRepository.carregarTodos(Produto.class);
        return lista != null ? lista : new ArrayList<>();
    }

    private void salvarProdutos() {
        JsonRepository.salvarTodos(produtos, Produto.class);
    }

    // Getter se precisar acessar os produtos de fora
    public List<Produto> getProdutos() {
        return produtos;
    }
}
