import inter.face.VistaDeLogin;
import medi.flow.Main;
import org.junit.jupiter.api.Test;
import sql.server.SqlGeral;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class MainTeste {

    // Teste de Conexão com o Banco de Dados
    @Test
    public void testConnectionInitialization() {
        // Simula o comportamento da conexão, sem mocks
        Connection connection = SqlGeral.DatabaseConnection.getInstance();

        // Verifica se a conexão não é nula (significa que foi estabelecida com sucesso)
        assertNotNull(connection, "A conexão com o banco de dados falhou!");
    }

    // Teste de Interface Gráfica
    @Test
    public void testLoginWindowVisibility() {
        // Cria a janela de login
        VistaDeLogin loginWindow = new VistaDeLogin();

        // Verifica se a janela foi criada corretamente e está visível
        loginWindow.setVisible(true);
        assertTrue(loginWindow.isVisible(), "A janela de login não está visível!");
    }

    // Teste do Método Principal
    @Test
    public void testMainMethod() {
        // Aqui, não é possível fazer uma verificação profunda,
        // mas você pode garantir que o método main executa sem lançar exceções
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("O método main falhou com a exceção: " + e.getMessage());
        }
    }
}
