package medi.flow;

public class Paciente {
    private int numeroSNS;
    private String nome;
    private int contacto;

    public Paciente(int numeroSNS, String nome, int contacto) {
        this.numeroSNS = numeroSNS;
        this.nome = nome;
        this.contacto = contacto;
    }

    // Getters
    public int getNumeroSNS() { return numeroSNS; }
    public String getNome() { return nome; }
    public int getContacto() { return contacto; }
}
