package entidades.petshop;

import persistencia.JsonRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servico {
	private static int contadorId = 1;
	private static final Map<Integer, Servico> servicos = new HashMap<>();

	private int id;
	private String descricao;
	private double preco;
	private TipoServico tipo;

	static {
		List<Servico> lista = JsonRepository.carregarTodos(Servico.class);

		int maiorId = 0;

		for (Servico s : lista) {
			servicos.put(s.getId(), s);
			if (s.getId() > maiorId) {
				maiorId = s.getId();
			}
		}

		contadorId = maiorId + 1;
	}


	public Servico(String descricao, double preco, TipoServico tipo) {
		this.id = contadorId++;
		this.descricao = descricao;
		this.preco = preco;
		this.tipo = tipo;
		adicionarServico(this); // Já registra o serviço ao ser criado
	}

	// Adiciona o serviço no "repositório" em memória
	private static void adicionarServico(Servico servico) {
		servicos.put(servico.getId(), servico);
	}

	// Buscar serviço por ID
	public static Servico buscarPorId(int id) {
		return servicos.get(id);
	}

	// Listar todos os serviços cadastrados
	public static void listarServicos() {
		if (servicos.isEmpty()) {
			System.out.println("Nenhum serviço cadastrado.");
			return;
		}

		for (Servico s : servicos.values()) {
			System.out.println(s);
		}
	}

	@Override
	public String toString() {
		return "[ID: " + id + "] [" + tipo + "] " + descricao + " - R$" + String.format("%.2f", preco);
	}

	// Getters e Setters
	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public TipoServico getTipo() {
		return tipo;
	}

	public void setTipo(TipoServico tipo) {
		this.tipo = tipo;
	}
}
