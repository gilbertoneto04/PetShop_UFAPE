package menus;

import entidades.clientes.Cliente;
import entidades.clientes.Paciente;
import entidades.funcionarios.Veterinario;
import entidades.petshop.Agendamento;
import entidades.petshop.Loja;
import entidades.petshop.Produto;
import entidades.petshop.RelatorioVendas;
import entidades.petshop.Servico;
import util.InputHelper;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuVendedor {

    public static void exibirMenu(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n===== MENU VENDEDOR =====");
            System.out.println("1. Menu da Loja");
            System.out.println("2. Menu de Agendamentos");
            System.out.println("3. Menu de Pacientes");
            System.out.println("4. Ver relatório de vendas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = InputHelper.lerInteiro(sc);
            switch (opcao) {
                case 1 -> menuLoja(sc);
                case 2 -> menuAgendamentos(sc);
                case 3 -> menuPacientes(sc);
                case 4 -> RelatorioVendas.listarVendas();
                case 0 -> System.out.println("Saindo do menu vendedor...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuLoja(Scanner sc) {
        Loja loja = new Loja();
        int opcao;
        do {
            System.out.println("\n--- MENU LOJA ---");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Listar produtos");
            System.out.println("3. Vender produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = InputHelper.lerInteiro(sc);
            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome do produto: ");
                    String nome = sc.nextLine();
                    System.out.print("Preço do produto: ");
                    double preco = InputHelper.lerDouble(sc);
                    System.out.print("Quantidade em estoque: ");
                    int quantidade = InputHelper.lerInteiro(sc);
                    Produto produto = new Produto(nome, preco, quantidade);
                    loja.adicionarProduto(produto);
                }
                case 2 -> loja.listarProdutos();
                case 3 -> {
                    System.out.print("Nome do produto a vender: ");
                    String nome = sc.nextLine();
                    System.out.print("Quantidade a vender: ");
                    int quantidade = InputHelper.lerInteiro(sc);
                    loja.venderProduto(nome, quantidade);
                }
                case 0 -> System.out.println("Voltando ao menu anterior...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuAgendamentos(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- MENU AGENDAMENTOS ---");
            System.out.println("1. Criar novo agendamento");
            System.out.println("2. Listar agendamentos");
            System.out.println("3. Remover agendamento");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = InputHelper.lerInteiro(sc);
            switch (opcao) {
                case 1 -> {
                    System.out.println("CPF do cliente:");
                    String cpfDono = sc.nextLine();
                    System.out.println("Nome do paciente:");
                    String nomePaciente = sc.nextLine();
                    Paciente paciente = Paciente.buscarPacientePorNome(cpfDono, nomePaciente);

                    if (paciente == null) {
                        System.out.println("Paciente não encontrado.");
                        return;
                    }

                    System.out.println("CPF do veterinário:");
                    String cpfVet = sc.nextLine();
                    Veterinario vet = Veterinario.getVeterinarioPorCpf(cpfVet);
                    if (vet == null) {
                        System.out.println("Veterinário não encontrado.");
                        return;
                    }

                    System.out.println("ID do serviço:");
                    int idServico = sc.nextInt();
                    Servico servico = Servico.buscarPorId(idServico);
                    if (servico == null) {
                        System.out.println("Serviço não encontrado.");
                        return;
                    }

                    System.out.println("Data e hora (AAAA-MM-DDTHH:MM): ");
                    LocalDateTime dataHora = InputHelper.lerDataHora(sc);

                    Agendamento agendamento = new Agendamento(dataHora, vet, servico, paciente);
                    agendamento.cadastrar();
                }
                case 2 -> Agendamento.listarTodos();
                case 3 -> {
                    System.out.println("CPF do veterinário:");
                    String cpfVet = sc.nextLine();
                    System.out.println("Data e hora (AAAA-MM-DDTHH:MM): ");
                    LocalDateTime dataHora = InputHelper.lerDataHora(sc);

                    Veterinario vet = Veterinario.getVeterinarioPorCpf(cpfVet);
                    if (vet == null) {
                        System.out.println("Veterinário não encontrado.");
                        return;
                    }

                    Agendamento agendamento = new Agendamento(dataHora, vet, null, null);
                    agendamento.remover();
                }
                case 0 -> System.out.println("Voltando ao menu anterior...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuPacientes(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- MENU PACIENTES ---");
            System.out.println("1. Cadastrar paciente");
            System.out.println("2. Listar todos");
            System.out.println("3. Atualizar paciente");
            System.out.println("4. Remover paciente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = InputHelper.lerInteiro(sc);
            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome do paciente: ");
                    String nome = sc.nextLine();
                    System.out.print("Espécie: ");
                    String especie = sc.nextLine();
                    System.out.print("Raça: ");
                    String raca = sc.nextLine();
                    System.out.print("CPF do dono: ");
                    String cpf = sc.nextLine();
                    Cliente dono = Cliente.getClientePorCpf(cpf);
                    if (dono != null) {
                        Paciente.cadastrarPaciente(nome, especie, raca, dono);
                    } else {
                        System.out.println("Dono não encontrado.");
                    }
                }
                case 2 -> Paciente.listarPacientes();
                case 3 -> {
                    System.out.print("CPF do dono: ");
                    String cpf = sc.nextLine();
                    System.out.print("Nome do paciente a atualizar: ");
                    String nomeAntigo = sc.nextLine();
                    System.out.print("Novo nome: ");
                    String nomeNovo = sc.nextLine();
                    System.out.print("Nova espécie: ");
                    String especie = sc.nextLine();
                    System.out.print("Nova raça: ");
                    String raca = sc.nextLine();
                    Cliente dono = Cliente.getClientePorCpf(cpf);
                    if (dono != null) {
                        Paciente novo = new Paciente(nomeNovo, especie, raca, dono);
                        Paciente.atualizarPaciente(cpf, nomeAntigo, novo);
                    } else {
                        System.out.println("Dono não encontrado.");
                    }
                }
                case 4 -> {
                    System.out.print("CPF do dono: ");
                    String cpf = sc.nextLine();
                    System.out.print("Nome do paciente a remover: ");
                    String nome = sc.nextLine();
                    Paciente.removerPaciente(cpf, nome);
                }
                case 0 -> System.out.println("Voltando ao menu anterior...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
