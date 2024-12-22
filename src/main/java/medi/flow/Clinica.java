package medi.flow;

import java.util.List;

import static sql.server.SqlGeral.*;
import static sql.server.SqlGestor.*;
import static sql.server.SqlMedico.obterTodosRegistos;
import static sql.server.SqlSecretaria.*;

/**
 *
 * @author Luís Assis
 * @author Luís Nantes
 */

// Classe que representa a Clinica, contendo listas de consultas, medicos, utilizadores, pacientes e registos
public class Clinica {

    private List<Consulta> consultas;
    private List<String[]> medicos;
    private List<Medico.HorarioMedico> horariosMedicos;
    private List<Utilizador> utilizadores;
    private List<Paciente> pacientes;
    private List<RegistoClinico> registos;

    //Construtor
    public Clinica() {
        this.consultas = obterTodasConsultas();
        this.medicos = obterTodosMedicos();
        this.utilizadores = obterTodosUtilizadores();
        this.pacientes = obterTodosPacientes();
        this.horariosMedicos = todosHorariosMedicos();
        this.registos = obterTodosRegistos();
    }

    //Getters
    public List<Consulta> getConsultas() {return consultas;}//retorna a lista de consultas
    public List<String[]> getMedicos() {return medicos;}//retorna a lista de medicos
    public List<Utilizador> getUtilizador() {return utilizadores;}//retorna a lista de utilizadores
    public List<Paciente> getPacientes() { return pacientes; }//retorna a lista de pacientes
    public Medico.HorarioMedico getHorarioMedico(int id) {//retorna o horario de um medico
        for (Medico.HorarioMedico horario : horariosMedicos) {//percorre a lista de horarios
            if (horario.getIdMedico() == id) {//se o id do medico for igual ao id passado como argumento
                return horario;
            }
        }
        return null;
    }
    //retorna a lista de horarios de todos os medicos
    public List<RegistoClinico> getRegistos() { return registos; }

    //Obter o nome do medico por id
    public String obterNomeMedicoPorId(int id) {
        for (Utilizador medico : utilizadores) {//percorre a lista de utilizadores
            if (medico.getId() == id) {//se o id do medico for igual ao id passado como argumento
                return medico.getNome();//retorna o nome do medico
            }
        }
        return null;
    }

    //Obter o id do medico por nome
    public String[] obterPacientePorSns(int sns) {
        for (Paciente paciente : pacientes) {//percorre a lista de pacientes
            if (paciente.getNumeroSNS() == sns) {//se o sns do paciente for igual ao sns passado como argumento
                return new String[]{paciente.getNome(), String.valueOf(paciente.getContacto())};//retorna o nome e o contacto do paciente
            }
        }
        return null;
    }

    //Obter o id do medico por nome
    public RegistoClinico obterRegistoPorSns(int sns) {
        for (RegistoClinico registo : registos) {//percorre a lista de registos
            if (registo.getNumeroSns() == sns) {//se o sns do registo for igual ao sns passado como argumento
                return registo;//retorna o registo
            }
        }
        return null;
    }

    //Adders
    public void addConsulta(Consulta consulta) {consultas.add(consulta);}// adiciona uma consulta
    public void addMedico(String[] medico) {medicos.add(medico);}//adiciona um medico
    public void addUtilizador(Utilizador utilizador) {utilizadores.add(utilizador);}//adiciona um utilizador
    public void addPaciente(Paciente paciente) {pacientes.add(paciente);}//adiciona um paciente
    public void addRegisto(RegistoClinico registo) {registos.add(registo);}//adiciona um registo


    //Removers
    public void removeConsulta(int id) {
        for (Consulta consulta : consultas) {//percorre a lista de consultas
            if (consulta.getIdConsulta() == id) {//se o id da consulta for igual ao id passado como argumento
                consultas.remove(consulta);//remove a consulta
                break;
            }
        }
    }
    //remove um medico
    public void removeMedico(int id) {
        for (String[] medico : medicos) {//percorre a lista de medicos
            if (Integer.parseInt(medico[0]) == id) {//se o id do medico for igual ao id passado como argumento
                medicos.remove(medico);//remove o medico
                break;
            }
        }
    }
    //remove um utilizador
    public void removeUtilizador(int id) {
        for (Utilizador utilizador : utilizadores) {//percorre a lista de utilizadores
            if (utilizador.getId() == id) {//se o id do utilizador for igual ao id passado como argumento
                utilizadores.remove(utilizador);//remove o utilizador
                break;
            }
        }
    }
    //remove um paciente
    public void removePaciente(int nSns) {
        for (Paciente paciente : pacientes) {//percorre a lista de pacientes
            if (paciente.getNumeroSNS() == nSns) {//se o sns do paciente for igual ao sns passado como argumento
                pacientes.remove(paciente);//remove o paciente
                break;
            }
        }
    }

    //Atualizar a clinica
    public void atualizarClinica() {
        this.consultas = obterTodasConsultas();//atualiza a lista de consultas
        this.medicos = obterTodosMedicos();//atualiza a lista de medicos
        this.utilizadores = obterTodosUtilizadores();//atualiza a lista de utilizadores
        this.pacientes = obterTodosPacientes();//atualiza a lista de pacientes
        this.horariosMedicos = todosHorariosMedicos();//atualiza a lista de horarios de medicos
        this.registos = obterTodosRegistos();//atualiza a lista de registos
    }
}

