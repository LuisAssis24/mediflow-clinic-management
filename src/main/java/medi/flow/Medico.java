package medi.flow;

import java.util.List;

// Classe que representa um Médico
public class Medico extends Utilizador {
    private int numOrdem; // Número da Ordem dos Médicos
    private String especialidade; // Especialidade médica
    private List<HorarioMedico> horario; // Horário do Médico

    // Construtor para inicializar os campos do Médico e da classe Utilizador
    public Medico(int id, int cc, String nome, String password, String tipoUtilizador, int numOrdem, String especialidade) {
        super(id, cc, nome, password, tipoUtilizador); // Chama o construtor da classe pai (Utilizador)
        this.numOrdem = numOrdem;// Inicializa o número da ordem
        this.especialidade = especialidade;// Inicializa a especialidade
        this.horario = null;// Inicializa o horário
    }

    // Getters
    public int getNumOrdem() { return numOrdem; }
    public String getEspecialidade() { return especialidade; }

    // Classe que representa o horário de um Médico
    public static class HorarioMedico {
        private int idMedico;// ID do Médico
        private List<String[]> horarios;// Lista de horários

        // Construtor para inicializar todos os campos
        public HorarioMedico(int idMedico, List<String[]> horarios) {
            this.idMedico = idMedico;// Inicializa o ID do Médico
            this.horarios = horarios;// Inicializa a lista de horários
        }

        // Getters
        public int getIdMedico() { return idMedico; }
        public List<String[]> getHorarios() { return horarios; }

    }
}
