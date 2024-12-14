package medi.flow;

import java.util.ArrayList;
import java.util.List;

public class RegistoClinico {
    private List<String> historicoDoencas;
    private List<String> alergias;
    private List<String> operacoes;
    private int numeroSns;
    private List<EntradaRegistoClinico> entradasRegistosClinicos;


    // Construtor
    public RegistoClinico(List<String> historicoDoencas, List<String> alergias, List<String> operacoes, int numeroSns) {
        this.historicoDoencas = historicoDoencas;
        this.alergias = alergias;
        this.operacoes = operacoes;
        this.numeroSns = numeroSns;
    }

    public RegistoClinico(int numeroSns){
        this.historicoDoencas = new ArrayList<String>();
        this.alergias = new ArrayList<>();
        this.operacoes = new ArrayList<>();
        this.numeroSns = numeroSns;
    }


    // Metodos getters para acesso aos atributos privados
    public List<String> getHistoricoDoencas() { return historicoDoencas; }
    public List<String> getAlergias() { return alergias; }
    public List<String> getOperacoes() { return operacoes; }
    public int getNumeroSns() { return numeroSns; }

    // Metodos setters
    public void setEntradasRegistosClinicos(List<EntradaRegistoClinico> entradasRegistosClinicos) {
        this.entradasRegistosClinicos = entradasRegistosClinicos;
    }


    public class EntradaRegistoClinico {
        private String data;
        private int id_medico;
        private List<String> tratamento;
        private int id_consulta;
        private String assunto;

        //Construtor para inicializar os atributos
        public EntradaRegistoClinico(String data, int id_medico, List<String> tratamento, int id_consulta, String assunto) {
            this.data = data;
            this.id_medico = id_medico;
            this.tratamento = tratamento;
            this.id_consulta = id_consulta;
            this.assunto = assunto;

        }

        // metodos getters
        public String getData() { return data; }
        public int getIdMedico() { return id_medico; }
        public List<String> getTratamento() { return tratamento; }
        public int getId_consulta() { return id_consulta; }
        public String getAssunto() { return assunto; }
    }
}
