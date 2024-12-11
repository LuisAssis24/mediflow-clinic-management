package medi.flow;

import java.util.List;

import static sql.server.SqlGeral.*;
import static sql.server.SqlGestor.*;
import static sql.server.SqlSecretaria.*;

public class Clinica {

    private List<Consulta> consultas;
    private List<String[]> medicos;
    private List<Utilizador> utilizadores;
    private List<Paciente> pacientes;

    public Clinica() {
        this.consultas = obterTodasConsultas();
        this.medicos = obterTodosMedicos();
        this.utilizadores = obterTodosUtilizadores();
        this.pacientes = obterTodosPacientes();
    }

    //Getters
    public List<Consulta> getConsultas() {return consultas;}
    public List<String[]> getMedicos() {return medicos;}
    public List<Utilizador> getUtilizador() {return utilizadores;}
    public List<Paciente> getPacientes() { return pacientes; }

    public String obterNomeMedicoPorId(int id) {
        for (Utilizador medico : utilizadores) {
            if (medico.getId() == id) {
                return medico.getNome();
            }
        }
        return null;
    }

    public String[] obterPacientePorSns(int sns) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNumeroSNS() == sns) {
                return new String[]{paciente.getNome(), String.valueOf(paciente.getContacto())};
            }
        }
        return null;
    }

    //Adders
    public void addConsulta(Consulta consulta) {consultas.add(consulta);}
    public void addMedico(String[] medico) {medicos.add(medico);}
    public void addUtilizador(Utilizador utilizador) {utilizadores.add(utilizador);}
    public void addPaciente(Paciente paciente) {pacientes.add(paciente);}

    //Removers
    public void removeConsulta(Consulta consulta) {consultas.remove(consulta);}
    public void removeMedico(String[] medico) {medicos.remove(medico);}
    public void removeUtilizador(Utilizador utilizador) {utilizadores.remove(utilizador);}
    public void removePaciente(Paciente paciente) {pacientes.remove(paciente);}

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
        private String data, hora, motivo, nomePaciente, nomeMedico;
        private int snsPaciente, numSala, idMedico, contacto;

        // Construtor para inicializar todos os campos
        public Consulta(int idConsulta, String data, String hora, String motivo, String nomePaciente, String nomeMedico, int snsPaciente, int numSala, int idMedico, int contacto) {
            this.idConsulta = idConsulta;
            this.data = data;
            this.hora = hora;
            this.motivo = motivo;
            this.nomePaciente = nomePaciente;
            this.nomeMedico = nomeMedico;
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
        public String getNomeMedico() { return nomeMedico; }
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


    public static class RegistroClinico {
        private int idFicha;
        private List<String> historicoClinico;
        private List<String> alergias;
        private List<String> doencasCronicas;
        private List<String> cirurgiasAnteriores;
        private List<String> historicoMedicamentos;
        private int numeroSns;


        // Construtor
        public RegistroClinico(int idFicha, List<String> historicoClinico, List<String> alergias, List<String> doencasCronicas, List<String> cirurgiasAnteriores, List<String> historicoMedicamentos, int numeroSns) {
            this.idFicha = idFicha;
            this.historicoClinico = historicoClinico;
            this.alergias = alergias;
            this.doencasCronicas = doencasCronicas;
            this.cirurgiasAnteriores = cirurgiasAnteriores;
            this.historicoMedicamentos = historicoMedicamentos;
            this.numeroSns = numeroSns;
        }


        // Metodos getters para acesso aos atributos privados
        public int getIdFicha() { return idFicha; }
        public List<String> getHistoricoClinico() { return historicoClinico; }
        public List<String> getAlergias() { return alergias; }
        public List<String> getDoencasCronicas() { return doencasCronicas; }
        public List<String> getCirurgiasAnteriores() { return cirurgiasAnteriores; }
        public List<String> getHistoricoMedicamentos() { return historicoMedicamentos; }
        public int getNumeroSns() { return numeroSns; }
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
