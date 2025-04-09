package service;

import entidades.petshop.Servico;
import persistencia.JsonRepository;
import java.util.*;

public class SolicitacaoServico {
    private String cpfCliente;
    private Servico servico;
    private StatusSolicitacao status;

    private static final List<SolicitacaoServico> solicitacoes = new ArrayList<>();

    static {
        List<SolicitacaoServico> carregadas = JsonRepository.carregarTodos(SolicitacaoServico.class);
        if (carregadas != null) {
            solicitacoes.addAll(carregadas);
        }
    }

    public SolicitacaoServico(String cpfCliente, Servico servico) {
        this.cpfCliente = cpfCliente;
        this.servico = servico;
        this.status = StatusSolicitacao.PENDENTE;
    }

    public static void solicitarServico(String cpfCliente, Scanner sc) {
        List<Servico> servicosDisponiveis = JsonRepository.carregarTodos(Servico.class);

        if (servicosDisponiveis == null || servicosDisponiveis.isEmpty()) {
            System.out.println("Nenhum serviço disponível no momento.");
            return;
        }

        System.out.println("\n=== SERVIÇOS DISPONÍVEIS ===");
        for (int i = 0; i < servicosDisponiveis.size(); i++) {
            System.out.println((i + 1) + ". " + servicosDisponiveis.get(i));
        }

        System.out.print("Escolha o número do serviço desejado: ");
        int opcao;
        try {
            opcao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (opcao < 1 || opcao > servicosDisponiveis.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Servico escolhido = servicosDisponiveis.get(opcao - 1);
        SolicitacaoServico nova = new SolicitacaoServico(cpfCliente, escolhido);
        solicitacoes.add(nova);
        JsonRepository.salvarTodos(solicitacoes, SolicitacaoServico.class);

        System.out.println("Solicitação registrada com sucesso!");
    }

    public static void listarSolicitacoes() {
        if (solicitacoes.isEmpty()) {
            System.out.println("Nenhuma solicitação registrada.");
            return;
        }

        System.out.println("\n=== SOLICITAÇÕES DE SERVIÇO ===");
        for (SolicitacaoServico s : solicitacoes) {
            System.out.println("Cliente CPF: " + s.cpfCliente + " | Serviço: " + s.servico + " | Status: " + s.status);
        }
    }

    // Getters e Setters
    public String getCpfCliente() {
        return cpfCliente;
    }

    public Servico getServico() {
        return servico;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
        JsonRepository.salvarTodos(solicitacoes, SolicitacaoServico.class);
    }

    public static List<SolicitacaoServico> getSolicitacoes() {
        return solicitacoes;
    }
}
