package entidades.clientes;

import persistencia.JsonRepository;

import java.util.*;

public class Paciente {
	private String nome;
	private String especie;
	private String raca;
	private Cliente dono;

	private static final Map<String, Paciente> pacientesPorChave = new HashMap<>();

	static {
		List<Paciente> pacientes = JsonRepository.carregarTodos(Paciente.class);
		for (Paciente p : pacientes) {
			String chave = gerarChave(p.dono.getCpf(), p.nome);
			pacientesPorChave.put(chave, p);
		}
	}

	public Paciente(String nome, String especie, String raca, Cliente dono) {
		this.nome = nome;
		this.especie = especie;
		this.raca = raca;
		this.dono = dono;
	}

	private static String gerarChave(String cpfDono, String nomePaciente) {
		return cpfDono + ":" + nomePaciente.toLowerCase();
	}

	// ✅ Cadastrar novo paciente
	public static boolean cadastrarPaciente(String nome, String especie, String raca, Cliente dono) {
		String chave = gerarChave(dono.getCpf(), nome);
		if (pacientesPorChave.containsKey(chave)) {
			System.out.println("Paciente com esse nome já cadastrado para este dono.");
			return false;
		}

		Paciente paciente = new Paciente(nome, especie, raca, dono);
		pacientesPorChave.put(chave, paciente);
		JsonRepository.adicionar(paciente, Paciente.class);
		System.out.println("Paciente cadastrado com sucesso.");
		return true;
	}

	// ✅ Listar todos os pacientes
	public static void listarPacientes() {
		List<Paciente> pacientes = JsonRepository.carregarTodos(Paciente.class);
		if (pacientes.isEmpty()) {
			System.out.println("Nenhum paciente cadastrado.");
			return;
		}

		for (Paciente p : pacientes) {
			System.out.println("Nome: " + p.nome + ", Espécie: " + p.especie + ", Raça: " + p.raca + ", Dono: " + p.dono.getNome());
		}
	}

	// ✅ Listar pacientes por CPF do dono
	public static void listarPorDono(String cpfDono) {
		boolean encontrou = false;
		for (Paciente p : pacientesPorChave.values()) {
			if (p.dono.getCpf().equals(cpfDono)) {
				System.out.println("Nome: " + p.nome + ", Espécie: " + p.especie + ", Raça: " + p.raca);
				encontrou = true;
			}
		}

		if (!encontrou) {
			System.out.println("Nenhum paciente encontrado para este dono.");
		}
	}

	// ✅ Atualizar paciente
	public static boolean atualizarPaciente(String cpfDono, String nomePaciente, Paciente novoPaciente) {
		String chave = gerarChave(cpfDono, nomePaciente);
		if (!pacientesPorChave.containsKey(chave)) {
			System.out.println("Paciente não encontrado.");
			return false;
		}

		pacientesPorChave.put(chave, novoPaciente);

		// Atualiza lista completa e salva
		List<Paciente> pacientes = new ArrayList<>(pacientesPorChave.values());
		JsonRepository.salvarTodos(pacientes, Paciente.class);
		System.out.println("Paciente atualizado com sucesso.");
		return true;
	}

	// ✅ Remover paciente
	public static boolean removerPaciente(String cpfDono, String nomePaciente) {
		String chave = gerarChave(cpfDono, nomePaciente);
		if (!pacientesPorChave.containsKey(chave)) {
			System.out.println("Paciente não encontrado.");
			return false;
		}

		pacientesPorChave.remove(chave);
		List<Paciente> pacientes = new ArrayList<>(pacientesPorChave.values());
		JsonRepository.salvarTodos(pacientes, Paciente.class);
		System.out.println("Paciente removido com sucesso.");
		return true;
	}

	// 🔍 Buscar paciente por nome e CPF do dono
	public static Paciente buscarPacientePorNome(String cpfDono, String nomePaciente) {
		String chave = gerarChave(cpfDono, nomePaciente);
		return pacientesPorChave.get(chave);
	}

	// Getters e Setters
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getEspecie() { return especie; }
	public void setEspecie(String especie) { this.especie = especie; }

	public String getRaca() { return raca; }
	public void setRaca(String raca) { this.raca = raca; }

	public Cliente getDono() { return dono; }
	public void setDono(Cliente dono) { this.dono = dono; }
}
