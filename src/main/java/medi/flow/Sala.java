package medi.flow;

// Classe que representa uma Sala
public class Sala {
    private int numSala;// Número da Sala
    private int idMedico;// ID do Médico
    private boolean ocupada;// Sala ocupada ou não
    private String tipoSala;// Tipo de Sala

    // Construtor para inicializar todos os campos
    public Sala(int numSala, int idMedico, boolean ocupada, String tipoSala) {
        this.numSala = numSala;// Inicializa o número da sala
        this.idMedico = idMedico;// Inicializa o ID do Médico
        this.tipoSala = tipoSala;// Inicializa o tipo de sala

        if (idMedico != -1) {// Se o ID do Médico for diferente de -1, a sala está ocupada
            this.ocupada = true;// Inicializa a sala como ocupada
        } else {// Se o ID do Médico for igual a -1, a sala está livre
            this.ocupada = false;// Inicializa a sala como livre
        }
    }

    //Getters
    public int getNumSala() { return numSala; }//retorna o numero da sala
    public int getIdMedico() { return idMedico; }//retorna o id do medico
    public boolean isOcupada() { return ocupada; }//retorna se a sala esta ocupada
    public String getTipoSala() { return tipoSala; }//retorna o tipo de sala

    //Setters
    public void setNumSala(int numSala) { this.numSala = numSala; }//define o numero da sala
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }//define o id do medico
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }//define se a sala esta ocupada
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }//define o tipo de sala

}
