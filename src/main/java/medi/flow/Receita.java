package medi.flow;

import java.util.List;

/**
 *
 * @author Luís Assis
 */

// Classe que representa uma Receita
public class Receita {
    private int numeroReceita;// Número da Receita
    private String dataEmissao;// Data de Emissão
    private List<String> medicacaoPrescrita;// Medicamentos prescritos

    // Construtor para inicializar os atributos
    public Receita(int numeroReceita, String dataEmissao, List<String> medicacaoPrescrita) {}// Inicializa os atributos

    // metodos getters
    public int getNumeroReceita() { return numeroReceita; }//retorna o numero da receita
    public String getDataEmissao() { return dataEmissao; }//retorna a data de emissao
    public List<String> getMedicacaoPrescrita() { return null; }//retorna a lista de medicamentos prescritos
}
