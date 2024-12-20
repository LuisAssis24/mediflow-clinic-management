package medi.flow;

import java.util.List;

import static sql.server.SqlGeral.*;
import static sql.server.SqlGestor.*;
import static sql.server.SqlMedico.obterTodosRegistos;
import static sql.server.SqlSecretaria.*;

public class Clinica {

    private List<Consulta> consultas;
    private List<String[]> medicos;
    private List<Medico.HorarioMedico> horariosMedicos;
    private List<Utilizador> utilizadores;
    private List<Paciente> pacientes;
    private List<RegistoClinico> registos;

    public Clinica() {
        this.consultas = obterTodasConsultas();
        this.medicos = obterTodosMedicos();
        this.utilizadores = obterTodosUtilizadores();
        this.pacientes = obterTodosPacientes();
        this.horariosMedicos = todosHorariosMedicos();
        this.registos = obterTodosRegistos();
    }

    //Getters
    public List<Consulta> getConsultas() {return consultas;}
    public List<String[]> getMedicos() {return medicos;}
    public List<Utilizador> getUtilizador() {return utilizadores;}
    public List<Paciente> getPacientes() { return pacientes; }
    public Medico.HorarioMedico getHorarioMedico(int id) {
        for (Medico.HorarioMedico horario : horariosMedicos) {
            if (horario.getIdMedico() == id) {
                return horario;
            }
        }
        return null;
    }
    public List<RegistoClinico> getRegistos() { return registos; }

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

    public RegistoClinico obterRegistoPorSns(int sns) {
        for (RegistoClinico registo : registos) {
            if (registo.getNumeroSns() == sns) {
                return registo;
            }
        }
        return null;
    }

    //Adders
    public void addConsulta(Consulta consulta) {consultas.add(consulta);}
    public void addMedico(String[] medico) {medicos.add(medico);}
    public void addUtilizador(Utilizador utilizador) {utilizadores.add(utilizador);}
    public void addPaciente(Paciente paciente) {pacientes.add(paciente);}
    public void addRegisto(RegistoClinico registo) {registos.add(registo);}


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

    public void atualizarClinica() {
        this.consultas = obterTodasConsultas();
        this.medicos = obterTodosMedicos();
        this.utilizadores = obterTodosUtilizadores();
        this.pacientes = obterTodosPacientes();
        this.horariosMedicos = todosHorariosMedicos();
        this.registos = obterTodosRegistos();
    }
}

