package medi.flow;

/**
 *
 * @author Luís Assis
 */

// Classe que contém todas as listas de consultas, médicos, utilizadores, pacientes e registos
public class Consulta {
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
    public int getIdConsulta() { return idConsulta; }//retorna o id da consulta
    public String getData() { return data; }//retorna a data da consulta
    public String getHora() { return hora; }//retorna a hora da consulta
    public String getMotivo() { return motivo; }//retorna o motivo da consulta
    public String getNomePaciente() { return nomePaciente; }//retorna o nome do paciente
    public String getNomeMedico() { return nomeMedico; }//retorna o nome do medico
    public int getSnsPaciente() { return snsPaciente; }//retorna o sns do paciente
    public int getNumSala() { return numSala; }//retorna o numero da sala
    public int getIdMedico() { return idMedico; }//retorna o id do medicoq
    public int getContacto() { return contacto; }//retorna o contacto
}
