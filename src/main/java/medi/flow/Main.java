package medi.flow;

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

        public RegistroClinico(String dataNascimento, int cc, List<String> tratamentos, List<String> alergias, List<String> historicoDoencas) {}

        public String getDataNascimento() { return ""; }
        public int getCc() { return 0; }
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

        public EntradaRegistroClinico(String data, Medico medico, String assunto, List<String> tratamento) {}

        public String getData() { return ""; }
        public Medico getMedico() { return null; }
        public String getAssunto() { return ""; }
        public String getTratamento() { return ""; }
    }

    class Receita {
        private int numeroReceita;
        private String dataEmissao;
        private List<String> medicacaoPrescrita;

        public Receita(int numeroReceita, String dataEmissao, List<String> medicacaoPrescrita) {}

        public int getNumeroReceita() { return 0; }
        public String getDataEmissao() { return ""; }
        public List<String> getMedicacaoPrescrita() { return null; }
    }

    class Paciente {
        private int numeroSns;
        private String nome;
        private String contacto;

    public Paciente(int numeroSns, String nome, String contacto) {}

        public int getNumeroSns() { return 0; }
        public String getNome() { return ""; }
        public String getContacto() { return ""; }
    }

    class Medico {
        private int numeroMedico;
        private String especialidade;

        public int getNumeroMedico() { return 0; }
        public String getEspecialidade() { return ""; }
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

    public Consulta(Sala sala, String data, String hora, boolean estado) {}

        public Sala getSala() { return null; }
        public String getData() { return ""; }
        public String getHora() { return ""; }
        public Medico getMedico() { return null; }
        public Paciente getPaciente() { return null; }
        public void setEstado(boolean estado) {}
    }

    class Sala {
        private int numero;
        private int especialidade;

        public int getNumero() { return 0; }
        public int getEspecialidade() { return 0; }
    }

    class Utilizador {
        private String nomeUtilizador;
        private String password;
        private int numeroClinica;
        private String nome;

        public Utilizador(String nomeUtilizador, String password, int numeroClinica, String nome) {}

        public String getNomeUtilizador() { return ""; }
        public String getPassword() { return ""; }
        public int getNumeroClinica() { return 0; }
        public String getNome() { return ""; }
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

    }
}