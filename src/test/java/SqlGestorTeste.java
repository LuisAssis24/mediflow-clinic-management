import medi.flow.Medico;
import medi.flow.Utilizador;
import org.junit.jupiter.api.Test;
import sql.server.SqlGestor;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

public class SqlGestorTeste {

    @Test
    public void testCriarUtilizador() {
        String nome = "Carlos Silva";
        String password = "senha123";
        String tipoUtilizador = "Medico";
        int cc = 123456789;
        String especialidade = "Cardiologia";
        int numOrdem = 45678;

        int idCriado = SqlGestor.criarUtilizador(nome, password, tipoUtilizador, cc, especialidade, numOrdem);

        assertTrue(idCriado > 0, "O ID do utilizador criado deve ser maior que 0.");

        // Verificar se os detalhes do médico foram criados corretamente
        List<Utilizador> utilizadores = SqlGestor.obterTodosUtilizadores();
        boolean medicoEncontrado = false;
        for (Utilizador utilizador : utilizadores) {
            if (utilizador instanceof Medico) {
                Medico medico = (Medico) utilizador;
                if (medico.getId() == idCriado) {
                    assertEquals(especialidade, medico.getEspecialidade());
                    assertEquals(numOrdem, medico.getNumOrdem());
                    medicoEncontrado = true;
                    break;
                }
            }
        }

        assertTrue(medicoEncontrado, "O médico criado não foi encontrado.");
    }
    @Test
    public void testEliminarUtilizador() throws SQLException {
        // Criar um utilizador para teste
        String nome = "João Souza";
        String password = "senha456";
        String tipoUtilizador = "Medico";
        int cc = 987654321;
        String especialidade = "Dermatologia";
        int numOrdem = 98765;

        int idCriado = SqlGestor.criarUtilizador(nome, password, tipoUtilizador, cc, especialidade, numOrdem);

        // Verificar que o utilizador foi criado
        assertTrue(idCriado > 0, "O utilizador deve ser criado com um ID válido.");

        // Tentar eliminar o utilizador
        boolean eliminado = SqlGestor.eliminarUtilizador(idCriado, "Medico");
        assertTrue(eliminado, "O utilizador deve ser eliminado com sucesso.");

        // Verificar que o utilizador foi realmente excluído
        List<Utilizador> utilizadores = SqlGestor.obterTodosUtilizadores();
        boolean utilizadorEncontrado = false;
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getId() == idCriado) {
                utilizadorEncontrado = true;
                break;
            }
        }
        assertFalse(utilizadorEncontrado, "O utilizador excluído ainda está presente na lista.");
    }
}
