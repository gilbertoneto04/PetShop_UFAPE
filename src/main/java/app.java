import entidades.clientes.Cliente;
import entidades.funcionarios.*;
import menus.MenuAdmin;
import menus.MenuCliente;
import menus.MenuVendedor;
import menus.MenuVeterinario;
import service.LoginClienteService;
import service.LoginFuncionarioService;

import java.util.Scanner;

public class app {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n=== CLÍNICA VETERINÁRIA ===");
            System.out.println("1. Login como Funcionário");
            System.out.println("2. Login como Cliente");
            System.out.println("3. Cadastrar Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String escolha = sc.nextLine();

            switch (escolha) {
                case "1":
                    System.out.print("Digite o CPF: ");
                    String cpfFunc = sc.nextLine();
                    System.out.print("Digite a senha: ");
                    String senhaFunc = sc.nextLine();
                    Funcionario funcionario = LoginFuncionarioService.login(cpfFunc, senhaFunc);

                    if (funcionario != null) {
                        menuFuncionario(funcionario, sc);
                    } else {
                        System.out.println("Login falhou. Verifique o CPF e a senha.");
                    }
                    break;

                case "2":
                    System.out.print("Digite o CPF: ");
                    String cpfCliente = sc.nextLine();
                    System.out.print("Digite a senha: ");
                    String senhaCliente = sc.nextLine();

                    Cliente clienteLogado = LoginClienteService.login(cpfCliente, senhaCliente);

                    if (clienteLogado != null) {
                        System.out.println("Bem-vindo, cliente " + clienteLogado.getNome() + "!");
                        MenuCliente.exibirMenu(clienteLogado, sc);
                    } else {
                        System.out.println("Login do cliente falhou.");
                    }
                    break;


                case "3":
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Usuário: ");
                    String user = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    Cliente.cadastrarCliente(nome, telefone, cpf, user, senha);
                    break;

                case "0":
                    executando = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
        System.out.println("Sistema encerrado.");
    }

    private static void menuFuncionario(Funcionario f, Scanner sc) {
        if (f instanceof Administrador) {
            MenuAdmin.exibirMenu(sc);
        } else if (f instanceof Vendedor) {
            MenuVendedor.exibirMenu(sc);
        } else if (f instanceof Veterinario) {
            MenuVeterinario.exibirMenu(sc, (Veterinario) f);
        } else {
            System.out.println("Tipo de funcionário desconhecido.");
        }
    }
}
