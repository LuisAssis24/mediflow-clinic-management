package medi.flow;

import java.util.List;

public class Clinica {
    public static class Utilizador {
        private int id;
        private int cc;
        private String nome, password, tipoUtilizador;

        // Construtor para inicializar todos os campos
        public Utilizador(int id, int cc, String nome, String password, String tipoUtilizador) {
            this.id = id;
            this.cc = cc;
            this.nome = nome;
            this.password = password;
            this.tipoUtilizador = tipoUtilizador;
        }

        // Getters
        public int getId() { return id; }
        public int getCc() { return cc; }
        public String getNome() { return nome; }
        public String getPassword() { return password; }
        public String getTipoUtilizador() { return tipoUtilizador; }
    }

    public static class Medico extends Utilizador {
        private int numOrdem; // Número da Ordem dos Médicos
        private String especialidade; // Especialidade médica

        // Construtor para inicializar os campos do Médico e da classe Utilizador
        public Medico(int id, int cc, String nome, String password, String tipoUtilizador, int numOrdem, String especialidade) {
            super(id, cc, nome, password, tipoUtilizador); // Chama o construtor da classe pai (Utilizador)
            this.numOrdem = numOrdem;
            this.especialidade = especialidade;
        }

        // Getters
        public int getNumOrdem() { return numOrdem; }
        public String getEspecialidade() { return especialidade; }
    }

    public static class Consulta {
        private int idConsulta;
        private String data, hora, motivo, nomePaciente;
        private int snsPaciente, numSala, idMedico, contacto;

        // Construtor para inicializar todos os campos
        public Consulta(int idConsulta, String data, String hora, String motivo, String nomePaciente, int snsPaciente, int numSala, int idMedico, int contacto) {
            this.idConsulta = idConsulta;
            this.data = data;
            this.hora = hora;
            this.motivo = motivo;
            this.nomePaciente = nomePaciente;
            this.snsPaciente = snsPaciente;
            this.numSala = numSala;
            this.idMedico = idMedico;
            this.contacto = contacto;
        }

        // Getters
        public int getIdConsulta() { return idConsulta; }
        public String getData() { return data; }
        public String getHora() { return hora; }
        public String getMotivo() { return motivo; }
        public String getNomePaciente() { return nomePaciente; }
        public int getSnsPaciente() { return snsPaciente; }
        public int getNumSala() { return numSala; }
        public int getIdMedico() { return idMedico; }
        public int getContacto() { return contacto; }
    }

    public static class Paciente {
        private int numeroSNS;
        private String nome;
        private int contacto;

        public Paciente(int numeroSNS, String nome, int contacto) {
            this.numeroSNS = numeroSNS;
            this.nome = nome;
            this.contacto = contacto;
        }

        // Getters
        public int getNumeroSNS() { return numeroSNS; }
        public String getNome() { return nome; }
        public int getContacto() { return contacto; }
    }


    class RegistroClinico {
        private String dataNascimento;
        private int cc;
        private List<String> tratamentos;
        private List<String> alergias;
        private List<String> historicoDoencas;

        // Construtor
        public RegistroClinico(String dataNascimento, int cc, List<String> tratamentos, List<String> alergias, List<String> historicoDoencas) {}

        // Metodos getters para acesso aos atributos privados
        public String getDataNascimento() { return dataNascimento; }
        public int getCc() { return cc; }
        public List<String> getTratamentos() { return null; }
        public List<String> getAlergias() { return null; }
        public List<String> getHistoricoDoencas() { return null; }
        public void addHistoricoDoencas(String doenca) {}
    }

    class EntradaRegistroClinico {
        private String data;
        private Medico medico;
        private String assunto;
        private List<String> tratamento;

        //Construtor para inicializar os atributos
        public EntradaRegistroClinico(String data, Medico medico, String assunto, List<String> tratamento) {}

        // metodos getters
        public String getData() { return data; }
        public Medico getMedico() { return medico; }
        public String getAssunto() { return assunto; }
        public String getTratamento() { return tratamento.toString(); }
    }

    class Receita {
        private int numeroReceita;
        private String dataEmissao;
        private List<String> medicacaoPrescrita;

        // Construtor para inicializar os atributos
        public Receita(int numeroReceita, String dataEmissao, List<String> medicacaoPrescrita) {}

        // metodos getters
        public int getNumeroReceita() { return numeroReceita; }
        public String getDataEmissao() { return dataEmissao; }
        public List<String> getMedicacaoPrescrita() { return null; }
    }

}
