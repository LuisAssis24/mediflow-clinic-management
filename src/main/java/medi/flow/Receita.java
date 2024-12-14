package medi.flow;

import java.util.List;

public class Receita {
    private int numeroReceita;
    private String dataEmissao;
    private List<String> medicacaoPrescrita;

    // Construtor para inicializar os atributos
    public Receita(int numeroReceita, String dataEmissao, List<String> medicacaoPrescrita) {}

    // metodos getters
    public int getNumeroReceita() { return numeroReceita; }
    public String getDataEmissao() { return dataEmissao; }
    public List<String> getMedicacaoPrescrita() { return null; }
}
