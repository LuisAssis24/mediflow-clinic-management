package medi.flow;
import static medi.flow.Main.getClinica;

// Classe que representa um Utilizador
public class Utilizador {
    private int id;// ID do Utilizador
    private int cc;// Cartão de Cidadão
    private String nome, password, tipoUtilizador;// Nome, Password e Tipo de Utilizador

    // Construtor para inicializar todos os campos
    public Utilizador(int id, int cc, String nome, String password, String tipoUtilizador) {
        this.id = id;// Inicializa o ID
        this.cc = cc;// Inicializa o Cartão de Cidadão
        this.nome = nome;// Inicializa o Nome
        this.password = password;// Inicializa a Password
        this.tipoUtilizador = tipoUtilizador;// Inicializa o Tipo de Utilizador
    }

    // Método para obter o último ID de Utilizador
    public static int ultimoIdUtilizador(){
        int maiorId = 0;// Inicializa o maior ID
        for (Utilizador utilizador : getClinica().getUtilizador()){// Percorre a lista de Utilizadores
            if (utilizador.id >= maiorId){// Se o ID do Utilizador for maior ou igual ao maior ID
                maiorId = utilizador.id;// Atualiza o maior ID
            }
        }
        return maiorId;// Retorna o maior ID
    }

    // Getters
    public int getId() { return id; }
    public int getCc() { return cc; }
    public String getNome() { return nome; }
    public String getPassword() { return password; }
    public String getTipoUtilizador() { return tipoUtilizador; }

}