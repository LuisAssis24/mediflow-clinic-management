package medi.flow;

import java.util.ArrayList;
import java.util.List;

import static sql.server.SqlGeral.*;
import static sql.server.SqlGestor.*;
import static sql.server.SqlMedico.obterTodosRegistos;
import static sql.server.SqlSecretaria.*;

// Classe que contém todas as listas de consultas, médicos, utilizadores, pacientes e registos
public class Clinica {

    private List<Consulta> consultas;
    private List<String[]> medicos;
    private List<Medico.HorarioMedico> horariosMedicos;
    private List<Utilizador> utilizadores;
    private List<Paciente> pacientes;
    private List<RegistoClinico> registos;

    //Construtor
    public Clinica() {
        this.consultas = obterTodasConsultas();//obterTodasConsultas();
        this.medicos = obterTodosMedicos();//obterTodosMedicos();
        this.utilizadores = obterTodosUtilizadores();//obterTodosUtilizadores();
        this.pacientes = obterTodosPacientes();//obterTodosPacientes();
        this.horariosMedicos = todosHorariosMedicos();//todosHorariosMedicos();
        this.registos = obterTodosRegistos();//obterTodosRegistos();
    }

    //Getters
    public List<Consulta> getConsultas() {return consultas;}//obterTodasConsultas();}
    public List<String[]> getMedicos() {return medicos;}//obterTodosMedicos();}
    public List<Utilizador> getUtilizador() {return utilizadores;}//obterTodosUtilizadores();}
    public List<Paciente> getPacientes() { return pacientes; }//obterTodosPacientes();}
    public Medico.HorarioMedico getHorarioMedico(int id) {//todosHorariosMedicos();
        for (Medico.HorarioMedico horario : horariosMedicos) {//todosHorariosMedicos();
            if (horario.getIdMedico() == id) {//todosHorariosMedicos();
                return horario;
            }
        }
        return null;
    }
    public List<RegistoClinico> getRegistos() { return registos; }//obterTodosRegistos();}

    //obter todos os medicos por id
    public String obterNomeMedicoPorId(int id) {
        for (Utilizador medico : utilizadores) {
            if (medico.getId() == id) {
                return medico.getNome();
            }
        }
        return null;
    }

    //obter todos os pacientes por sns
    public String[] obterPacientePorSns(int sns) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNumeroSNS() == sns) {
                return new String[]{paciente.getNome(), String.valueOf(paciente.getContacto())};
            }
        }
        return null;
    }

    //Adders
    public void addConsulta(Consulta consulta) {consultas.add(consulta);}//obterTodasConsultas();}
    public void addMedico(String[] medico) {medicos.add(medico);}//obterTodosMedicos();}
    public void addUtilizador(Utilizador utilizador) {utilizadores.add(utilizador);}//obterTodosUtilizadores();}
    public void addPaciente(Paciente paciente) {pacientes.add(paciente);}//obterTodosPacientes();}

    //Removers
    public void removeConsulta(int id) {//remove consulta por id
        for (Consulta consulta : consultas) {
            if (consulta.getIdConsulta() == id) {
                consultas.remove(consulta);
                break;
            }
        }
    }
    public void removeMedico(int id) {// remove medico por id
        for (String[] medico : medicos) {
            if (Integer.parseInt(medico[0]) == id) {
                medicos.remove(medico);
                break;
            }
        }
    }
    public void removeUtilizador(int id) {// remove utilizador por id
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getId() == id) {
                utilizadores.remove(utilizador);
                break;
            }
        }
    }
    public void removePaciente(int nSns) { // remove paciente por sns
        for (Paciente paciente : pacientes) {
            if (paciente.getNumeroSNS() == nSns) {
                pacientes.remove(paciente);
                break;
            }
        }
    }
}

