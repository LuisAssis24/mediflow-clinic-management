package medi.flow;

// Classe que representa um Paciente
public class Paciente {
    private int numeroSNS;// Número de Segurança Social
    private String nome;// Nome do Paciente
    private int contacto;// Contacto do Paciente

    // Construtor para inicializar todos os campos
    public Paciente(int numeroSNS, String nome, int contacto) {
        this.numeroSNS = numeroSNS;// Inicializa o número de segurança social
        this.nome = nome;// Inicializa o nome
        this.contacto = contacto;// Inicializa o contacto
    }

    // Getters
    public int getNumeroSNS() { return numeroSNS; }//retorna o numero de sns
    public String getNome() { return nome; }//retorna o nome
    public int getContacto() { return contacto; }//retorna o contacto
}
