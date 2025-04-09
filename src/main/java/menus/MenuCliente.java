package menus;

import entidades.clientes.Cliente;
import entidades.clientes.Paciente;
import service.SolicitacaoServico;

import java.util.Scanner;

public class MenuCliente {

    public static void exibirMenu(Cliente clienteLogado, Scanner sc) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU CLIENTE =====");
            System.out.println("1. Gerenciar pets");
            System.out.println("2. Gerenciar dados pessoais");
            System.out.println("3. Solicitar serviço");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    menuPets(clienteLogado, sc);
                    break;
                case 2:
                    menuDadosPessoais(clienteLogado, sc);
                    break;
                case 3:
                    SolicitacaoServico.solicitarServico(clienteLogado.getCpf(), sc);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuPets(Cliente cliente, Scanner sc) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Pets ---");
            System.out.println("1. Cadastrar pet");
            System.out.println("2. Listar meus pets");
            System.out.println("3. Atualizar pet");
            System.out.println("4. Remover pet");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Espécie: ");
                    String especie = sc.nextLine();
                    System.out.print("Raça: ");
                    String raca = sc.nextLine();
                    Paciente.cadastrarPaciente(nome, especie, raca, cliente);
                    break;

                case 2:
                    Paciente.listarPorDono(cliente.getCpf());
                    break;

                case 3:
                    System.out.print("Nome do pet a atualizar: ");
                    String nomeAtual = sc.nextLine();

                    Paciente pacienteExistente = Paciente.buscarPacientePorNome(cliente.getCpf(), nomeAtual);

                    if (pacienteExistente == null) {
                        System.out.println("Pet não encontrado.");
                        break;
                    }

                    System.out.print("Novo nome (pressione Enter para manter \"" + pacienteExistente.getNome() + "\"): ");
                    String novoNome = sc.nextLine();
                    if (novoNome.isBlank()) novoNome = pacienteExistente.getNome();

                    System.out.print("Nova espécie (pressione Enter para manter \"" + pacienteExistente.getEspecie() + "\"): ");
                    String novaEspecie = sc.nextLine();
                    if (novaEspecie.isBlank()) novaEspecie = pacienteExistente.getEspecie();

                    System.out.print("Nova raça (pressione Enter para manter \"" + pacienteExistente.getRaca() + "\"): ");
                    String novaRaca = sc.nextLine();
                    if (novaRaca.isBlank()) novaRaca = pacienteExistente.getRaca();

                    Paciente atualizado = new Paciente(novoNome, novaEspecie, novaRaca, cliente);
                    Paciente.atualizarPaciente(cliente.getCpf(), nomeAtual, atualizado);
                    break;


                case 4:
                    System.out.print("Nome do pet a remover: ");
                    String nomeRemover = sc.nextLine();
                    Paciente.removerPaciente(cliente.getCpf(), nomeRemover);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuDadosPessoais(Cliente cliente, Scanner sc) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Dados Pessoais ---");
            System.out.println("1. Ver meus dados");
            System.out.println("2. Atualizar meus dados");
            System.out.println("3. Excluir minha conta");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    Cliente.listarInformacoes(cliente.getCpf());
                    break;

                case 2:
                    System.out.print("Novo nome (pressione Enter para manter \"" + cliente.getNome() + "\"): ");
                    String novoNome = sc.nextLine();
                    if (novoNome.isEmpty()) novoNome = cliente.getNome();

                    System.out.print("Novo telefone (pressione Enter para manter \"" + cliente.getTelefone() + "\"): ");
                    String novoTelefone = sc.nextLine();
                    if (novoTelefone.isEmpty()) novoTelefone = cliente.getTelefone();

                    System.out.print("Nova senha (pressione Enter para manter a atual): ");
                    String novaSenha = sc.nextLine();
                    if (novaSenha.isEmpty()) novaSenha = cliente.getSenha();

                    Cliente novoCliente = new Cliente(novoNome, novoTelefone, cliente.getCpf(), novaSenha);
                    Cliente.atualizarCliente(cliente.getCpf(), novoCliente);
                    cliente = novoCliente; // atualiza a instância atual
                    break;


                case 3:
                    System.out.print("Tem certeza que deseja remover sua conta? (s/n): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("s")) {
                        Cliente.removerCliente(cliente.getCpf());
                        opcao = 0; // sai do menu
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}

