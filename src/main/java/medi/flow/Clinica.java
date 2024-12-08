package medi.flow;

import java.util.List;

public class Clinica {
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

    class Paciente {
        private int numSns, contacto;
        private String nome;

        // Construtor
        public Paciente(int numSns, int contacto, String nome) {
            this.numSns = numSns;
            this.contacto = contacto;
            this.nome = nome;
        }

        public String getNome() {
            return nome;
        }

        public int getNumSns() {
            return numSns;
        }

        public int getContacto() {
            return contacto;
        }

    }


    class Medico {
        private int numeroMedico;
        private String especialidade;

        // Metodos getters e setters
        public int getNumeroMedico() { return numeroMedico; }
        public String getEspecialidade() { return especialidade; }
        public void setEspecialidade(String especialidade) {}
        public Receita receitaMedica(Receita receita) { return null; }
        public void imprimirReceita(Receita receita) {}
    }

    public static class Consulta {
        private int idConsulta;
        private String data;
        private String hora;
        private String motivo;
        private String nomePaciente;
        private int snsPaciente;
        private int numSala;
        private int idMedico;
        private int contacto;

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
        public int getIdConsulta() {
            return idConsulta;
        }

        public String getData() {
            return data;
        }

        public String getHora() {
            return hora;
        }

        public String getMotivo() {
            return motivo;
        }

        public String getNomePaciente() {
            return nomePaciente;
        }

        public int getSnsPaciente() {
            return snsPaciente;
        }

        public int getNumSala() {
            return numSala;
        }

        public int getIdMedico() {
            return idMedico;
        }

        public int getContacto() {
            return contacto;
        }
    }


    class Sala {
        private int numero;
        private int especialidade;

        // Metodos getters
        public int getNumero() { return numero; }
        public int getEspecialidade() { return especialidade; }
    }

    class Utilizador {
        private String nomeUtilizador;
        private String password;
        private int numeroClinica;
        private String nome;

        // Construtor para inicializar os atributos
        public Utilizador(String nomeUtilizador, String password, int numeroClinica, String nome) {}

        // Metodos getters
        public String getNomeUtilizador() { return nomeUtilizador; }
        public String getPassword() { return password; }
        public int getNumeroClinica() { return numeroClinica; }
        public String getNome() { return nome; }
    }

    class Gestor {
        public void criarUtilizador(String nomeUtilizador, String password, String tipoUtilizador) {}
        public void apagarUtilizador(String nomeUtilizador, String password) {}
    }

    class Funcionario {
        public Consulta marcarConsulta(Sala sala, String data, String hora) { return null; }
        public void desmarcarConsulta(Sala sala, String data, String hora) {}
        public void dispMedicos(String dia, String hora) {}
        public void dispSalas(String dia, String hora) {}
        public Consulta verConsulta(String dia, String hora, Paciente paciente) { return null; }
    }
}
