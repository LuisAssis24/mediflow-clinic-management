import medi.flow.Utilizador;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilizadorTeste {

    @Test
    public void testUtilizadorCreation() {
        // Dados para o construtor
        int id = 1;
        int cc = 123456789;
        String nome = "João Silva";
        String password = "senha123";
        String tipoUtilizador = "Paciente";

        // Criação do objeto Utilizador
        Utilizador utilizador = new Utilizador(id, cc, nome, password, tipoUtilizador);

        // Testa se os valores dos atributos são corretamente atribuídos e obtidos
        assertEquals(id, utilizador.getId());
        assertEquals(cc, utilizador.getCc());
        assertEquals(nome, utilizador.getNome());
        assertEquals(password, utilizador.getPassword());
        assertEquals(tipoUtilizador, utilizador.getTipoUtilizador());
    }

    @Test
    public void testUtilizadorGetter() {
        // Dados para o construtor
        int id = 2;
        int cc = 987654321;
        String nome = "Maria Costa";
        String password = "senha456";
        String tipoUtilizador = "Médico";

        // Criação do objeto Utilizador
        Utilizador utilizador = new Utilizador(id, cc, nome, password, tipoUtilizador);

        // Testa se o método getter retorna os valores corretos
        assertEquals(2, utilizador.getId());
        assertEquals(987654321, utilizador.getCc());
        assertEquals("Maria Costa", utilizador.getNome());
        assertEquals("senha456", utilizador.getPassword());
        assertEquals("Médico", utilizador.getTipoUtilizador());
    }
}