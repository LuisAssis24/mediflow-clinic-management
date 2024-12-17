package medi.flow;

import java.util.*;

import static medi.flow.Main.getClinica;

public class RegistoClinico {
    private int numeroSns;
    private List<String> historicoDoencas;
    private List<String> alergias;
    private List<String> operacoes;
    private List<EntradaRegistoClinico> entradasRegistoClinico;


    // Construtor
    public RegistoClinico(int numeroSns){
        this.numeroSns = numeroSns;
        this.historicoDoencas = new ArrayList<>();
        this.alergias = new ArrayList<>();
        this.operacoes = new ArrayList<>();
        this.entradasRegistoClinico = new ArrayList<>();
    }

    // Metodos getters para acesso aos atributos privados
    public List<String> getHistoricoDoencas() { return historicoDoencas; }
    public List<String> getAlergias() { return alergias; }
    public List<String> getOperacoes() { return operacoes; }
    public int getNumeroSns() { return numeroSns; }

    // Metodos setters
    public void setHistoricoDoencas(List<String> historicoDoencas) {
        this.historicoDoencas = historicoDoencas;
    }
    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }
    public void setOperacoes(List<String> operacoes) {
        this.operacoes = operacoes;
    }
    public void setEntradasRegistosClinicos(List<EntradaRegistoClinico> entradasRegistosClinicos) {
        this.entradasRegistoClinico = entradasRegistosClinicos;
    }

    //Metodo toString para imprimir os atributos do objeto
    public String listToString(List<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String item : lista) {
            sb.append(item).append(" ");
        }
        return sb.toString().trim();
    }

    //Metodo para obter as informações do Paciente atráves da instância consulta
    public String[] getInfoPaciente() {
        String[] infoPaciente = new String[2];
        for (Paciente paciente : getClinica().getPacientes()) {
            if (paciente.getNumeroSNS() == numeroSns) {
                infoPaciente[0] = paciente.getNome();
                infoPaciente[1] = String.valueOf(paciente.getContacto());
                break;
            }
        }
        return infoPaciente;
    }

    public class EntradaRegistoClinico {
        private int id_medico;
        private int id_consulta;
        private String data;
        private String assunto;
        private String tratamento;

        //Construtor para inicializar os atributos
        public EntradaRegistoClinico(int id_medico, int id_consulta, String data, String assunto, String tratamento) {
            this.id_medico = id_medico;
            this.id_consulta = id_consulta;
            this.data = data;
            this.assunto = assunto;
            this.tratamento = tratamento;
        }

        // metodos getters
        public int getIdMedico() { return id_medico; }
        public int getIdConsulta() { return id_consulta; }
        public String getData() { return data; }
        public String getAssunto() { return assunto; }
        public String getTratamento() { return tratamento; }
    }
}
