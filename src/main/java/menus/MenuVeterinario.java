package menus;

import entidades.clientes.Paciente;
import entidades.funcionarios.Veterinario;
import entidades.petshop.Consulta;
import entidades.petshop.Exame;
import entidades.petshop.StatusProcedimento;
import persistencia.JsonRepository;

import java.util.List;
import java.util.Scanner;

public class MenuVeterinario {

    public static void exibirMenu(Scanner sc, Veterinario veterinarioLogado) {
        int opcao;
        do {
            System.out.println("\n===== MENU VETERINÁRIO =====");
            System.out.println("1. Listar todos os pacientes");
            System.out.println("2. Listar minhas consultas");
            System.out.println("3. Listar meus exames");
            System.out.println("4. Alterar status de uma consulta");
            System.out.println("5. Alterar status de um exame");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro(sc);
            switch (opcao) {
                case 1 -> Paciente.listarPacientes();
                case 2 -> listarConsultas(veterinarioLogado);
                case 3 -> listarExames(veterinarioLogado);
                case 4 -> alterarStatusConsulta(sc, veterinarioLogado);
                case 5 -> alterarStatusExame(sc, veterinarioLogado);
                case 0 -> System.out.println("Saindo do menu veterinário...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void listarConsultas(Veterinario veterinario) {
        List<Consulta> consultas = JsonRepository.carregarTodos(Consulta.class);
        System.out.println("\n--- Minhas Consultas ---");
        for (Consulta c : consultas) {
            if (c.getVeterinario().getCpf().equals(veterinario.getCpf())) {
                System.out.println("Paciente: " + c.getPaciente().getNome() +
                        " | Status: " + c.getStatus());
            }
        }
    }

    private static void listarExames(Veterinario veterinario) {
        List<Exame> exames = JsonRepository.carregarTodos(Exame.class);
        System.out.println("\n--- Meus Exames ---");
        for (Exame e : exames) {
            if (e.getVeterinario().getCpf().equals(veterinario.getCpf())) {
                System.out.println("Paciente: " + e.getPaciente().getNome() +
                        " | Status: " + e.getStatus());
            }
        }
    }

    private static void alterarStatusConsulta(Scanner sc, Veterinario veterinario) {
        List<Consulta> consultas = JsonRepository.carregarTodos(Consulta.class);
        System.out.print("Digite o nome do paciente da consulta: ");
        String nomePaciente = sc.nextLine();

        for (Consulta c : consultas) {
            if (c.getVeterinario().getCpf().equals(veterinario.getCpf()) &&
                    c.getPaciente().getNome().equalsIgnoreCase(nomePaciente)) {

                System.out.println("Status atual: " + c.getStatus());
                System.out.println("Escolha o novo status:");
                for (StatusProcedimento status : StatusProcedimento.values()) {
                    System.out.println("- " + status);
                }
                String novoStatus = sc.nextLine().toUpperCase();

                try {
                    c.setStatus(StatusProcedimento.valueOf(novoStatus));
                    JsonRepository.salvarTodos(consultas, Consulta.class);
                    System.out.println("Status atualizado com sucesso!");
                    return;
                } catch (IllegalArgumentException e) {
                    System.out.println("Status inválido.");
                    return;
                }
            }
        }
        System.out.println("Consulta não encontrada.");
    }

    private static void alterarStatusExame(Scanner sc, Veterinario veterinario) {
        List<Exame> exames = JsonRepository.carregarTodos(Exame.class);
        System.out.print("Digite o nome do paciente do exame: ");
        String nomePaciente = sc.nextLine();

        for (Exame e : exames) {
            if (e.getVeterinario().getCpf().equals(veterinario.getCpf()) &&
                    e.getPaciente().getNome().equalsIgnoreCase(nomePaciente)) {

                System.out.println("Status atual: " + e.getStatus());
                System.out.println("Escolha o novo status:");
                for (StatusProcedimento status : StatusProcedimento.values()) {
                    System.out.println("- " + status);
                }
                String novoStatus = sc.nextLine().toUpperCase();

                try {
                    e.setStatus(StatusProcedimento.valueOf(novoStatus));
                    JsonRepository.salvarTodos(exames, Exame.class);
                    System.out.println("Status atualizado com sucesso!");
                    return;
                } catch (IllegalArgumentException e2) {
                    System.out.println("Status inválido.");
                    return;
                }
            }
        }
        System.out.println("Exame não encontrado.");
    }

    private static int lerInteiro(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }
}
