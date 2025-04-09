package entidades.petshop;

import entidades.clientes.Paciente;
import entidades.funcionarios.Veterinario;

public class Exame {
    private Paciente paciente;
    private Veterinario veterinario;
    private StatusProcedimento status;

    public Exame(Paciente paciente, Veterinario veterinario, StatusProcedimento status) {
        this.paciente = paciente;
        this.veterinario = veterinario;
        this.status = status;
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }

    public StatusProcedimento getStatus() { return status; }
    public void setStatus(StatusProcedimento status) { this.status = status; }
}
