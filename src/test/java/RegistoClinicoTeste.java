import medi.flow.RegistoClinico;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class RegistoClinicoTeste {

    // Teste de Criação
    @Test
    public void testCriacaoRegistoClinico() {
        // Cria o registo clínico com apenas o número SNS
        RegistoClinico registo = new RegistoClinico(123456789);

        // Verifica se o número SNS foi inicializado corretamente
        assertEquals(123456789, registo.getNumeroSns(), "O número SNS deve ser 123456789");

        // Verifica se as listas estão vazias, já que elas são inicializadas como listas vazias no construtor
        assertTrue(registo.getHistoricoDoencas().isEmpty(), "A lista de histórico de doenças deve estar vazia inicialmente");
        assertTrue(registo.getAlergias().isEmpty(), "A lista de alergias deve estar vazia inicialmente");
        assertTrue(registo.getOperacoes().isEmpty(), "A lista de operações deve estar vazia inicialmente");
    }

    // Teste de Criação com Verificação de Dados Iniciais
    @Test
    public void testCriacaoRegistoClinicoVazio() {
        // Cria o registo clínico sem dados
        RegistoClinico registo = new RegistoClinico(123456789);

        // Verifica se as listas estão vazias
        assertEquals(123456789, registo.getNumeroSns());
        assertTrue(registo.getHistoricoDoencas().isEmpty());
        assertTrue(registo.getAlergias().isEmpty());
        assertTrue(registo.getOperacoes().isEmpty());
    }

    // Teste de Verificação de Listas Vazias
    @Test
    public void testRegistoClinicoVazio() {
        // Cria um registo clínico vazio
        RegistoClinico registo = new RegistoClinico(123456789);

        // Verifica se todas as listas estão vazias
        assertTrue(registo.getHistoricoDoencas().isEmpty());
        assertTrue(registo.getAlergias().isEmpty());
        assertTrue(registo.getOperacoes().isEmpty());
    }
}