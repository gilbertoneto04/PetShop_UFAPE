package menus;

import entidades.funcionarios.Administrador;
import entidades.funcionarios.Vendedor;
import entidades.funcionarios.Veterinario;
import entidades.petshop.Servico;
import entidades.petshop.TipoServico;
import persistencia.JsonRepository;

import java.util.*;

public class MenuAdmin {

    public static void exibirMenu(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n===== MENU ADMINISTRADOR =====");
            System.out.println("1. Gerenciar funcionários");
            System.out.println("2. Gerenciar serviços");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro(sc);
            switch (opcao) {
                case 1 -> menuFuncionarios(sc);
                case 2 -> menuServicos(sc);
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuFuncionarios(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Funcionários ---");
            System.out.println("1. Cadastrar funcionário");
            System.out.println("2. Listar funcionários");
            System.out.println("3. Atualizar funcionário");
            System.out.println("4. Remover funcionário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro(sc);
            switch (opcao) {
                case 1 -> cadastrarFuncionario(sc);
                case 2 -> listarFuncionarios();
                case 3 -> atualizarFuncionario(sc);
                case 4 -> removerFuncionario(sc);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarFuncionario(Scanner sc) {
        String tipo = lerTexto(sc, "Tipo (admin/vendedor/veterinario): ").toLowerCase();
        String nome = lerTexto(sc, "Nome: ");
        String cpf = lerTexto(sc, "CPF: ");
        String senha = lerTexto(sc, "Senha: ");

        switch (tipo) {
            case "admin" -> new Administrador(nome, cpf, senha).cadastrar();
            case "vendedor" -> new Vendedor(nome, cpf, senha).cadastrar();
            case "veterinario" -> new Veterinario(nome, cpf, senha).cadastrar();
            default -> System.out.println("Tipo inválido.");
        }
    }

    private static void listarFuncionarios() {
        System.out.println("\n--- Administradores ---");
        new Administrador("0", "0", "0").listar();
        System.out.println("\n--- Vendedores ---");
        new Vendedor("0", "0", "0").listar();
        System.out.println("\n--- Veterinários ---");
        new Veterinario("0", "0", "0").listar();
    }

    private static void atualizarFuncionario(Scanner sc) {
        String tipo = lerTexto(sc, "Tipo (admin/vendedor/veterinario): ").toLowerCase();
        String cpf = lerTexto(sc, "CPF do funcionário: ");
        String novoNome = lerTexto(sc, "Novo nome: ");
        String novaSenha = lerTexto(sc, "Nova senha: ");

        switch (tipo) {
            case "admin" -> new Administrador(novoNome, cpf, novaSenha).atualizar();
            case "vendedor" -> new Vendedor(novoNome, cpf, novaSenha).atualizar();
            case "veterinario" -> new Veterinario(novoNome, cpf, novaSenha).atualizar();
            default -> System.out.println("Tipo inválido.");
        }
    }

    private static void removerFuncionario(Scanner sc) {
        String tipo = lerTexto(sc, "Tipo (admin/vendedor/veterinario): ").toLowerCase();
        String cpf = lerTexto(sc, "CPF do funcionário: ");

        switch (tipo) {
            case "admin" -> Administrador.removerPorCpf(cpf);
            case "vendedor" -> Vendedor.removerPorCpf(cpf);
            case "veterinario" -> Veterinario.removerPorCpf(cpf);
            default -> System.out.println("Tipo inválido.");
        }
    }

    private static void menuServicos(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Serviços ---");
            System.out.println("1. Cadastrar serviço");
            System.out.println("2. Listar serviços");
            System.out.println("3. Atualizar serviço");
            System.out.println("4. Remover serviço");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro(sc);
            List<Servico> lista = JsonRepository.carregarTodos(Servico.class);

            // Atualiza o mapa de serviços em memória (caso tenha sido reiniciado)
            for (Servico s : lista) {
                Servico.buscarPorId(s.getId()); // adiciona no mapa se não tiver
            }

            switch (opcao) {
                case 1 -> {
                    String descricao = lerTexto(sc, "Descrição: ");
                    double preco = lerDouble(sc, "Preço: ");
                    TipoServico tipo = TipoServico.valueOf(lerTexto(sc, "Tipo (VENDA, CONSULTA, EXAME): ").toUpperCase());

                    Servico novo = new Servico(descricao, preco, tipo);
                    lista.add(novo);
                    JsonRepository.salvarTodos(lista, Servico.class);

                    System.out.println("Serviço cadastrado com sucesso. ID: " + novo.getId());
                }

                case 2 -> {
                    if (lista.isEmpty()) System.out.println("Nenhum serviço cadastrado.");
                    else lista.forEach(System.out::println);
                }

                case 3 -> {
                    int id = lerInteiro(sc, "ID do serviço a atualizar: ");
                    Servico servico = Servico.buscarPorId(id);
                    if (servico != null) {
                        String novaDesc = lerTexto(sc, "Nova descrição: ");
                        double novoPreco = lerDouble(sc, "Novo preço: ");
                        TipoServico novoTipo = TipoServico.valueOf(lerTexto(sc, "Novo tipo (VENDA, CONSULTA, EXAME): ").toUpperCase());

                        servico.setDescricao(novaDesc);
                        servico.setPreco(novoPreco);
                        servico.setTipo(novoTipo);

                        JsonRepository.salvarTodos(lista, Servico.class);
                        System.out.println("Serviço atualizado com sucesso.");
                    } else {
                        System.out.println("Serviço com ID não encontrado.");
                    }
                }

                case 4 -> {
                    int id = lerInteiro(sc, "ID do serviço a remover: ");
                    Servico servico = Servico.buscarPorId(id);
                    if (servico != null) {
                        lista.removeIf(s -> s.getId() == id);
                        JsonRepository.salvarTodos(lista, Servico.class);
                        System.out.println("Serviço removido com sucesso.");
                    } else {
                        System.out.println("Serviço com ID não encontrado.");
                    }
                }

                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }


    // Métodos utilitários pra leitura
    private static int lerInteiro(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static int lerInteiro(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        return lerInteiro(sc);
    }

    private static double lerDouble(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static String lerTexto(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine();
    }
}
