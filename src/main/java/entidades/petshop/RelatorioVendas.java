package entidades.petshop;

import persistencia.JsonRepository;
import java.util.ArrayList;
import java.util.List;

public class RelatorioVendas {
    private static final List<Venda> vendas = carregar();

    public static void registrarVenda(Venda venda) {
        vendas.add(venda);
        salvar();
    }

    public static void listarVendas() {
        if (vendas.isEmpty()) {
            System.out.println("📭 Nenhuma venda registrada.");
            return;
        }
        System.out.println("\n📊 RELATÓRIO DE VENDAS:");
        for (Venda venda : vendas) {
            System.out.println("🧾 " + venda);
        }
    }

    private static void salvar() {
        JsonRepository.salvarTodos(vendas, Venda.class);
    }

    private static List<Venda> carregar() {
        List<Venda> lista = JsonRepository.carregarTodos(Venda.class);
        return lista != null ? lista : new ArrayList<>();
    }
}
