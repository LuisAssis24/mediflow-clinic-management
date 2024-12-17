import medi.flow.RegistoClinico;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class RegistoClinicoTeste {

    @Test
    public void testCriacaoRegistoClinico() {
        // Dados para o histórico
        List<String> doencas = Arrays.asList("Gripe", "Diabetes");
        List<String> alergias = Arrays.asList("Penicilina");
        List<String> operacoes = Arrays.asList("Cirurgia de apendicite");

        // Cria o registo clínico com os dados
        RegistoClinico registo = new RegistoClinico(123456789);

        // Verifica se os dados foram inicializados corretamente
        assertEquals(123456789, registo.getNumeroSns());
        assertEquals(doencas, registo.getHistoricoDoencas());
        assertEquals(alergias, registo.getAlergias());
        assertEquals(operacoes, registo.getOperacoes());
    }
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
    @Test
    public void testRegistoClinicoVazio() {
        // Cria um registo clínico vazio
        RegistoClinico registo = new RegistoClinico(123456789);

        // Verifica se todas as listas estão vazias
        assertTrue(registo.getHistoricoDoencas().isEmpty());
        assertTrue(registo.getAlergias().isEmpty());
        assertTrue(registo.getOperacoes().isEmpty());
    }
    @Test
    public void testComListasNulas() {
        // Cria um registo clínico com listas nulas
        RegistoClinico registo = new RegistoClinico(null, null, null, 123456789);

        // Verifica se as listas nulas são tratadas corretamente
        assertNull(registo.getHistoricoDoencas());
        assertNull(registo.getAlergias());
        assertNull(registo.getOperacoes());
    }

}
