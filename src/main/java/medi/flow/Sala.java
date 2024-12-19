package medi.flow;

public class Sala {
    private int numSala;
    private int idMedico;
    private boolean ocupada;
    private String tipoSala;

    public Sala(int numSala, int idMedico, boolean ocupada, String tipoSala) {
        this.numSala = numSala;
        this.idMedico = idMedico;
        this.tipoSala = tipoSala;

        if (idMedico != -1) {
            this.ocupada = true;
        } else {
            this.ocupada = false;
        }
    }

    //Getters
    public int getNumSala() { return numSala; }
    public int getIdMedico() { return idMedico; }
    public boolean isOcupada() { return ocupada; }
    public String getTipoSala() { return tipoSala; }

    //Setters
    public void setNumSala(int numSala) { this.numSala = numSala; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }

}
