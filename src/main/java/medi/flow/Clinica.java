package medi.flow;

import java.util.List;

import static sql.server.SqlGeral.*;
import static sql.server.SqlGestor.*;
import static sql.server.SqlSecretaria.*;

public class Clinica {

    private List<Consulta> consultas;
    private List<String[]> medicos;
    private List<Medico.HorarioMedico> horariosMedicos;
    private static List<Utilizador> utilizadores;
    private List<Paciente> pacientes;

    public Clinica() {
        this.consultas = obterTodasConsultas();
        this.medicos = obterTodosMedicos();
        this.utilizadores = obterTodosUtilizadores();
        this.pacientes = obterTodosPacientes();
        this.horariosMedicos = todosHorariosMedicos();
    }

    //Getters
    public List<Consulta> getConsultas() {return consultas;}
    public List<String[]> getMedicos() {return medicos;}
    public List<Utilizador> getUtilizador() {return utilizadores;}
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medico.HorarioMedico> getHorariosMedicos() { return horariosMedicos; }

    public static String obterNomeMedicoPorId(int id) {
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

    public void adicionarHorarioMedico() {

    }

    //Adders
    public void addConsulta(Consulta consulta) {consultas.add(consulta);}
    public void addMedico(String[] medico) {medicos.add(medico);}
    public void addUtilizador(Utilizador utilizador) {utilizadores.add(utilizador);}
    public void addPaciente(Paciente paciente) {pacientes.add(paciente);}

    //Removers
    public void removeConsulta(int id) {
        for (Consulta consulta : consultas) {
            if (consulta.getIdConsulta() == id) {
                consultas.remove(consulta);
                break;
            }
        }
    }
    public void removeMedico(int id) {
        for (String[] medico : medicos) {
            if (Integer.parseInt(medico[0]) == id) {
                medicos.remove(medico);
                break;
            }
        }
    }
    public void removeUtilizador(int id) {
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getId() == id) {
                utilizadores.remove(utilizador);
                break;
            }
        }
    }
    public void removePaciente(int nSns) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNumeroSNS() == nSns) {
                pacientes.remove(paciente);
                break;
            }
        }
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

    public static class EntradaRegistroClinico {
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

    public class Receita {
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
