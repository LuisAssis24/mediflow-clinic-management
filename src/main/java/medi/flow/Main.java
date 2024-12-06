package medi.flow;

import inter.face.VistaDeLogin;
import sql.server.SqlGeral;

import java.sql.Connection;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
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
        private int numeroSns;
        private String nome;
        private String contacto;

        // Construtor par ainicializar os atributos
    public Paciente(int numeroSns, String nome, String contacto) {}

        // Metodos getters
        public int getNumeroSns() { return numeroSns; }
        public String getNome() { return nome; }
        public String getContacto() { return contacto; }
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

    class Consulta {
        private Sala sala;
        private String data;
        private String hora;
        private boolean estado;
        private Medico medico;
        private Paciente paciente;

        // COnstrutor para inicializar os atributos
    public Consulta(Sala sala, String data, String hora, boolean estado) {}

        // Metodos getters e setters
        public Sala getSala() { return sala; }
        public String getData() { return data; }
        public String getHora() { return hora; }
        public Medico getMedico() { return medico; }
        public Paciente getPaciente() { return paciente; }
        public void setEstado(boolean estado) {}
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
    public static void main(String[] args) {
    // Cria a conexão com a base de dados assim que o programa inicia
        Connection connection = SqlGeral.DatabaseConnection.getInstance();

        // Verifica se a conexão foi bem-sucedida
        if (connection != null) {
            System.out.println("Conexão inicializada.");
        } else {
            System.out.println("Falha ao conectar a base de dados.");
        }

        // Inicia a interface gráfica
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaDeLogin().setVisible(true); // Mostra a janela de login
            }
        });

    }
}