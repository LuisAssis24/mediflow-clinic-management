package medi.flow;

import java.util.*;

import static medi.flow.Main.getClinica;

/**
 *
 * @author Luís Assis
 * @author Luís Nantes
 */

// Classe que representa o Registo Clínico de um Paciente
public class RegistoClinico {
    private int numeroSns;
    private List<String> historicoDoencas;
    private List<String> alergias;
    private List<String> operacoes;
    private List<EntradaRegistoClinico> entradasRegistoClinico;


    // Construtor
    public RegistoClinico(int numeroSns){
        this.numeroSns = numeroSns;// Inicializa o número de sns
        this.historicoDoencas = new ArrayList<>();// Inicializa a lista de historico de doenças
        this.alergias = new ArrayList<>();// Inicializa a lista de alergias
        this.operacoes = new ArrayList<>();// Inicializa a lista de operações
        this.entradasRegistoClinico = new ArrayList<>();// Inicializa a lista de entradas de registo clinico
    }

    // Metodos getters para acesso aos atributos privados
        public List<String> getHistoricoDoencas() { return historicoDoencas; }//retorna a lista de historico de doenças
        public List<String> getAlergias() { return alergias; }//retorna a lista de alergias
        public List<String> getOperacoes() { return operacoes; }//retorna a lista de operações
        public int getNumeroSns() { return numeroSns; }//retorna o numero de sns
        public List<EntradaRegistoClinico> getEntradasRegistoClinico() { return entradasRegistoClinico; }//retorna a lista de entradas de registo clinico

    // Metodos setters
    public void setHistoricoDoencas(List<String> historicoDoencas) {
        this.historicoDoencas = historicoDoencas;//atualiza a lista de historico de doenças
    }
    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;//atualiza a lista de alergias
    }
    public void setOperacoes(List<String> operacoes) {
        this.operacoes = operacoes;//atualiza a lista de operações
    }
    public void setEntradasRegistosClinicos(List<EntradaRegistoClinico> entradasRegistosClinicos) {
        this.entradasRegistoClinico = entradasRegistosClinicos;//atualiza a lista de entradas de registo clinico
    }

    //Adders
    public void addEntradaRegistoClinico(EntradaRegistoClinico entrada) {
        entradasRegistoClinico.add(entrada);//adiciona uma entrada de registo clinico
    }
    //Metodo toString para imprimir os atributos do objeto
    public String listToString(List<String> lista) {
        StringBuilder sb = new StringBuilder();//cria um novo StringBuilder
        for (String item : lista) {//percorre a lista
            sb.append(item).append(" ");//adiciona o item ao StringBuilder
        }
        return sb.toString().trim();//retorna o StringBuilder
    }

    //Metodo para obter as informações do Paciente atráves da instância consulta
    public String[] getInfoPaciente() {
        String[] infoPaciente = new String[2];//cria um array de strings
        for (Paciente paciente : getClinica().getPacientes()) {//percorre a lista de pacientes
            if (paciente.getNumeroSNS() == numeroSns) {//se o sns do paciente for igual ao sns do registo clinico
                infoPaciente[0] = paciente.getNome();//adiciona o nome do paciente ao array
                infoPaciente[1] = String.valueOf(paciente.getContacto());//adiciona o contacto do paciente ao array
                break;
            }
        }
        return infoPaciente;//retorna o array
    }

    //Metodo para obter as informações do Medico atráves da instância consulta
    public class EntradaRegistoClinico {
        private int id_medico;//id do medico
        private String data;//  data da entrada
        private List<String> assunto;//assunto da entrada
        private List<String> tratamento;//tratamento da entrada

        //Construtor para inicializar os atributos
        public EntradaRegistoClinico(int id_medico, String data, List<String> assunto, List<String> tratamento) {
            this.id_medico = id_medico;//inicializa o id do medico
            this.data = data;//inicializa a data
            this.assunto = assunto;//inicializa o assunto
            this.tratamento = tratamento;//inicializa o tratamento
        }

        // metodos getters
        public int getIdMedico() { return id_medico; }//retorna o id do medico
        public String getData() { return data; }//retorna a data
        public List<String> getAssunto() { return assunto; }//retorna o assunto
        public List<String> getTratamentos() { return tratamento; }//retorna o tratamento
        public int getNumeroSns() { return numeroSns; }//retorna o sns
    }
}
