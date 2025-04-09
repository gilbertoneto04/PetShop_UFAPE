package entidades.funcionarios;

import persistencia.JsonRepository;
import java.util.*;

public class Administrador extends Funcionario {
    private static final Map<String, Administrador> administradores = new HashMap<>();

    static {
        JsonRepository.carregarTodos(Administrador.class)
                .forEach(a -> administradores.put(a.getCpf(), a));
    }

    public Administrador(String nome, String cpf, String senha) {
        super(nome, cpf, senha, Cargos.ADMINISTRADOR);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("🔒 [ADMIN] Nome: " + nome + ", CPF: " + cpf + ", Cargo: " + cargo);
    }

    @Override
    public boolean cadastrar() {
        if (administradores.containsKey(cpf)) {
            System.out.println("CPF já cadastrado.");
            return false;
        }
        administradores.put(cpf, this);
        salvar();
        System.out.println("Administrador cadastrado com sucesso.");
        return true;
    }

    @Override
    public boolean atualizar() {
        if (!administradores.containsKey(cpf)) {
            System.out.println("Administrador não encontrado.");
            return false;
        }
        administradores.put(cpf, this);
        salvar();
        System.out.println("Administrador atualizado.");
        return true;
    }

    @Override
    public boolean remover() {
        if (administradores.remove(cpf) != null) {
            salvar();
            System.out.println("Administrador removido.");
            return true;
        }
        System.out.println("Administrador não encontrado.");
        return false;
    }

    @Override
    public void listar() {
        if (administradores.isEmpty()) {
            System.out.println("Nenhum administrador cadastrado.");
            return;
        }
        administradores.values().forEach(Administrador::exibirInformacoes);
    }

    private void salvar() {
        salvarDados(new ArrayList<>(administradores.values()), Administrador.class);
    }

    public static void removerPorCpf(String cpf) {
        Administrador a = administradores.get(cpf);
        if (a != null) a.remover();
        else System.out.println("Administrador não encontrado.");
    }

    public static List<Administrador> getListaAdministradores() {
        return new ArrayList<>(administradores.values());
    }

    // ========== Gerenciamento genérico de funcionários ==========

    public static void gerenciarFuncionario(String acao, Funcionario funcionario) {
        switch (acao.toLowerCase()) {
            case "cadastrar" -> funcionario.cadastrar();
            case "atualizar" -> funcionario.atualizar();
            case "remover" -> funcionario.remover();
            case "listar" -> funcionario.listar();
            default -> System.out.println("Ação inválida.");
        }
    }

    public static void listarPorCargo(Cargos cargo) {
        switch (cargo) {
            case ADMINISTRADOR -> new Administrador("", "", "").listar();
            case VENDEDOR -> new Vendedor("", "", "").listar();
            case VETERINARIO -> new Veterinario("", "", "").listar();
            default -> System.out.println("Cargo inválido.");
        }
    }

    public static void removerPorCargoECpf(Cargos cargo, String cpf) {
        switch (cargo) {
            case ADMINISTRADOR -> removerPorCpf(cpf);
            case VENDEDOR -> Vendedor.removerPorCpf(cpf);
            case VETERINARIO -> Veterinario.removerPorCpf(cpf);
            default -> System.out.println("Cargo inválido.");
        }
    }
}
