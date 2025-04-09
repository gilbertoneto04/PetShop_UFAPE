package service;

import entidades.clientes.Cliente;

public class LoginClienteService {

    public static Cliente login(String cpf, String senha) {
        boolean autenticado = Cliente.login(cpf, senha);

        if (autenticado) {
            return Cliente.getClientePorCpf(cpf);
        }

        return null;
    }
}
