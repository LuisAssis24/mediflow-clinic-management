package medi.flow;

import java.util.*;

import static medi.flow.Main.getClinica;

// Classe que representa o Registo Clínico de um Paciente
public class RegistoClinico {
    private int numeroSns;// Número de Segurança Social
    private List<String> historicoDoencas;// Histórico de Doenças
    private List<String> alergias;// Alergias
    private List<String> operacoes;// Operações
    private List<EntradaRegistoClinico> entradasRegistoClinico;// Entradas no Registo Clínico


    // Construtor
    public RegistoClinico(int numeroSns){
        this.numeroSns = numeroSns;// Inicializa o número de segurança social
        this.historicoDoencas = new ArrayList<>();// Inicializa o histórico de doenças
        this.alergias = new ArrayList<>();// Inicializa as alergias
        this.operacoes = new ArrayList<>();// Inicializa as operações
        this.entradasRegistoClinico = new ArrayList<>();// Inicializa as entradas no registo clínico
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

    //Metodo toString para imprimir os atributos do objeto
    public String listToString(List<String> lista) {
        StringBuilder sb = new StringBuilder();// Cria um StringBuilder
        for (String item : lista) {// Itera sobre a lista
            sb.append(item).append(" ");// Adiciona o item ao StringBuilder
        }
        return sb.toString().trim();// Retorna o StringBuilder como String
    }

    //Metodo para obter as informações do Paciente atráves da instância consulta
    public String[] getInfoPaciente() {
        String[] infoPaciente = new String[2];// Cria um array de Strings
        for (Paciente paciente : getClinica().getPacientes()) {// Itera sobre a lista de pacientes
            if (paciente.getNumeroSNS() == numeroSns) {// Verifica se o número de segurança social é igual ao número de segurança social do paciente
                infoPaciente[0] = paciente.getNome();// Adiciona o nome do paciente ao array
                infoPaciente[1] = String.valueOf(paciente.getContacto());// Adiciona o contacto do paciente ao array
                break;
            }
        }
        return infoPaciente;
    }

    //Metodo para adicionar uma entrada ao registo clínico
    public class EntradaRegistoClinico {
        private int id_medico;// ID do Médico
        private int id_consulta;// ID da Consulta
        private String data;// Data da Entrada
        private List<String> assunto;// Assunto
        private List<String> tratamento;// Tratamento

        //Construtor para inicializar os atributos
        public EntradaRegistoClinico(int id_medico, String data, List<String> assunto, List<String> tratamento) {
            this.id_medico = id_medico;// Inicializa o ID do Médico
            this.data = data;// Inicializa a data
            this.assunto = assunto;// Inicializa o assunto
            this.tratamento = tratamento;// Inicializa o tratamento
        }

        // metodos getters
        public int getIdMedico() { return id_medico; }
        public String getData() { return data; }
        public List<String> getAssunto() { return assunto; }
        public List<String> getTratamentos() { return tratamento; }
    }
}
