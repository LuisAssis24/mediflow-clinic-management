package medi.flow;

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
