package medi.flow;

import java.util.List;

// Classe que representa uma Receita
public class Receita {
    private int numeroReceita;// Número da Receita
    private String dataEmissao;// Data de Emissão
    private List<String> medicacaoPrescrita;// Medicamentos prescritos

    // Construtor para inicializar os atributos
    public Receita(int numeroReceita, String dataEmissao, List<String> medicacaoPrescrita) {}

    // metodos getters
    public int getNumeroReceita() { return numeroReceita; }
    public String getDataEmissao() { return dataEmissao; }
    public List<String> getMedicacaoPrescrita() { return null; }
}
