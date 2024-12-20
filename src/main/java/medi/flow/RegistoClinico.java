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
    public List<EntradaRegistoClinico> getEntradasRegistoClinico() { return entradasRegistoClinico; }

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

    //Adders
    public void addEntradaRegistoClinico(EntradaRegistoClinico entrada) {
        entradasRegistoClinico.add(entrada);
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
        private String data;
        private List<String> assunto;
        private List<String> tratamento;

        //Construtor para inicializar os atributos
        public EntradaRegistoClinico(int id_medico, String data, List<String> assunto, List<String> tratamento) {
            this.id_medico = id_medico;
            this.data = data;
            this.assunto = assunto;
            this.tratamento = tratamento;
        }

        // metodos getters
        public int getIdMedico() { return id_medico; }
        public String getData() { return data; }
        public List<String> getAssunto() { return assunto; }
        public List<String> getTratamentos() { return tratamento; }
        public int getNumeroSns() { return numeroSns; }
    }
}
