import medi.flow.Consulta;
import org.junit.jupiter.api.Test;
import sql.server.SqlGeral;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.List;

public class SqlGeralTeste {

    @Test
    public void testVerificarLoginComCredenciaisValidas() {
        try {
            // Cria a conexão com a base de dados de teste
            Connection conexao = SqlGeral.DatabaseConnection.getInstance();

            // Verifica se a conexão foi criada corretamente
            assertNotNull(conexao, "A conexão com a base de dados não foi estabelecida.");

            // Testa o método com credenciais válidas
            String tipoUtilizador = SqlGeral.verificarLogin("100038", "44332211");

            // Verifica se o tipo de utilizador retornado é o esperado
            assertEquals("Gestor", tipoUtilizador, "O tipo de utilizador deveria ser Gestor");

        } catch (Exception e) {
            fail("Erro ao verificar login: " + e.getMessage());
        }
    }

    @Test
    public void testVerificarLoginComCredenciaisInvalidas() {
        try {
            // Cria a conexão com a base de dados de teste
            Connection conexao = SqlGeral.DatabaseConnection.getInstance();

            // Verifica se a conexão foi criada corretamente
            assertNotNull(conexao, "A conexão com a base de dados não foi estabelecida.");

            // Testa o método com credenciais inválidas
            String tipoUtilizador = SqlGeral.verificarLogin("123", "wrongpassword");

            // Verifica se o tipo de utilizador é null
            assertNull(tipoUtilizador, "O tipo de utilizador deveria ser null para credenciais inválidas");

        } catch (Exception e) {
            fail("Erro ao verificar login: " + e.getMessage());
        }
    }
    @Test
    public void testObterTodasConsultas() {
        try {
            // Cria a conexão com a bade de dados de teste
            Connection conexao = SqlGeral.DatabaseConnection.getInstance();

            // Verifica se a conexão foi criada corretamente
            assertNotNull(conexao, "A conexão com a base de dados não foi estabelecida.");

            // Obtém todas as consultas
            List<Consulta> consultas = SqlGeral.obterTodasConsultas();

            // Verifica se a lista de consultas não está vazia
            assertNotNull(consultas, "A lista de consultas não pode ser nula");
            assertFalse(consultas.isEmpty(), "A lista de consultas não pode ser vazia");

            // Verifica se todas as consultas estão no futuro
            Date dataAtual = new Date(System.currentTimeMillis());
            for (Consulta consulta : consultas) {
                Date dataConsulta = Date.valueOf(consulta.getData()); // Supondo que o método getData retorna um Date
                assertTrue(dataConsulta.after(dataAtual), "A consulta deve estar no futuro");
            }

        } catch (Exception e) {
            fail("Erro ao obter consultas: " + e.getMessage());
        }
    }

    @Test
    public void testGetInstance() {
        try {
            // Obtém a conexão com o banco de dados
            Connection connection = SqlGeral.DatabaseConnection.getInstance();

            // Verifica se a conexão não é nula
            assertNotNull(connection, "A conexão com o banco de dados não pode ser nula");

        } catch (Exception e) {
            fail("Erro ao obter a conexão: " + e.getMessage());
        }
    }

    @Test
    public void testCloseConnection() {
        try {
            // Obtém a conexão com o banco de dados
            Connection connection = SqlGeral.DatabaseConnection.getInstance();

            // Verifica se a conexão foi aberta corretamente
            assertNotNull(connection, "A conexão com o banco de dados não pode ser nula");

            // Fecha a conexão
            SqlGeral.DatabaseConnection.closeConnection();

            // Verifica se a conexão foi fechada sem erros
            // Isso pode ser feito verificando se o método `getInstance` abrirá uma nova conexão
            Connection newConnection = SqlGeral.DatabaseConnection.getInstance();
            assertNotNull(newConnection, "A nova conexão não pode ser nula após o fechamento da anterior");

        } catch (Exception e) {
            fail("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

}
