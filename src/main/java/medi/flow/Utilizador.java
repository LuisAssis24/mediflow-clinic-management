package medi.flow;


public class Utilizador {
    private int id;
    private int cc;
    private String nome, password, tipoUtilizador;

    // Construtor para inicializar todos os campos
    public Utilizador(int id, int cc, String nome, String password, String tipoUtilizador) {
        this.id = id;
        this.cc = cc;
        this.nome = nome;
        this.password = password;
        this.tipoUtilizador = tipoUtilizador;
    }

    // Getters
    public int getId() { return id; }
    public int getCc() { return cc; }
    public String getNome() { return nome; }
    public String getPassword() { return password; }
    public String getTipoUtilizador() { return tipoUtilizador; }

}