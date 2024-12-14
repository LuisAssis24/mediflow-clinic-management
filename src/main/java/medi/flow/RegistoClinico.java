package medi.flow;

import java.util.ArrayList;
import java.util.List;

public class RegistoClinico {
    private int idFicha;
    private List<String> historicoDoencas;
    private List<String> alergias;
    private List<String> operacoes;
    private int numeroSns;


    // Construtor
    public RegistoClinico(int idFicha, List<String> historicoDoencas, List<String> alergias, List<String> operacoes, int numeroSns) {
        this.idFicha = idFicha;
        this.historicoDoencas = historicoDoencas;
        this.alergias = alergias;
        this.operacoes = operacoes;
        this.numeroSns = numeroSns;
    }

    public RegistoClinico(int idFicha, int numeroSns){
        this.idFicha = idFicha;
        this.historicoDoencas = new ArrayList<String>();
        this.alergias = new ArrayList<>();
        this.operacoes = new ArrayList<>();
        this.numeroSns = numeroSns;
    }


    // Metodos getters para acesso aos atributos privados
    public int getIdFicha() { return idFicha; }
    public List<String> getHistoricoDoencas() { return historicoDoencas; }
    public List<String> getAlergias() { return alergias; }
    public List<String> getOperacoes() { return operacoes; }
    public int getNumeroSns() { return numeroSns; }


    public class EntradaRegistroClinico {
        private int id_consulta;
        private int id_medico;
        private int id_registro;
        private int id_ficha;
        private String data;
        private Medico medico;
        private String assunto;
        private List<String> tratamento;

        //Construtor para inicializar os atributos
        public EntradaRegistroClinico(int id_ficha , int id_registro ,int id_medico, int id_consulta, String data, Medico medico, String assunto, List<String> tratamento) {
            this.id_ficha = id_ficha;
            this.id_registro = id_registro;
            this.id_medico = id_medico;
            this.id_consulta = id_consulta;
            this.data = data;
            this.medico = medico;
            this.assunto = assunto;
            this.tratamento = tratamento;
        }

        // metodos getters
        public String getData() { return data; }
        public Medico getMedico() { return medico; }
        public String getAssunto() { return assunto; }
        public String getTratamento() { return tratamento.toString(); }
    }
}
