package medi.flow;

import java.util.List;

public class Medico extends Utilizador {
    private int numOrdem; // Número da Ordem dos Médicos
    private String especialidade; // Especialidade médica
    private List<HorarioMedico> horario; // Horário do Médico

    // Construtor para inicializar os campos do Médico e da classe Utilizador
    public Medico(int id, int cc, String nome, String password, String tipoUtilizador, int numOrdem, String especialidade) {
        super(id, cc, nome, password, tipoUtilizador); // Chama o construtor da classe pai (Utilizador)
        this.numOrdem = numOrdem;
        this.especialidade = especialidade;
        this.horario = null;
    }

    // Getters
    public int getNumOrdem() { return numOrdem; }
    public String getEspecialidade() { return especialidade; }


    public static class HorarioMedico {
        private int idMedico;
        private List<String[]> horarios;

        // Construtor para inicializar todos os campos
        public HorarioMedico(int idMedico, List<String[]> horarios) {
            this.idMedico = idMedico;
            this.horarios = horarios;
        }

        // Getters
        public int getIdMedico() { return idMedico; }
        public List<String[]> getHorarios() { return horarios; }

    }
}
