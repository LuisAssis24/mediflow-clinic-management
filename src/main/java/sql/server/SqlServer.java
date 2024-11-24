package sql.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServer {

    // A classe DatabaseConnection é a responsavel por criar a conexão com a base de dados
    public class DatabaseConnection {

        // Definição das constantes para o URL da base de dados, o utilizador e a palavra-passe
        private static final String URL = "jdbc:mysql://estga-dev.ua.pt:3306/PTDA24_BD_04";
        private static final String USER = "PTDA24_04";
        private static final String PASSWORD = "Etjs=889";

        private static Connection connection; // Declaração da variável de conexão, que será partilhada pelo sistema todo

        // Construtor privado para evitar a criação de instâncias da classe diretamente
        private DatabaseConnection() {
        }

        // Método estático para obter a conexão com a base de dados
        public static Connection getInstance() {

            // Verifica se a conexão já foi criada
            if (connection == null) { // Se a conexão ainda não foi estabelecida
                try {
                    // Tenta estabelecer uma nova conexão com a base de dados usando os parâmetros fornecidos
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Conexão com MySQL bem-sucedida!"); // Mensagem de sucesso na conexão na consola
                } catch (SQLException e) { // Captura exceções relacionadas ao SQL
                    System.out.println("Erro ao conectar ao MySQL: " + e.getMessage()); // Mensagem de erro em caso de falha
                }
            }
            // Retorna a instância da conexão, que pode ser null se não for criada
            return connection;
        }

        // Método estático para fechar a conexão com a base de dados
        public static void closeConnection() {
            if (connection != null) { // Verifica se a conexão existe
                try {
                    connection.close(); // Tenta fechar a conexão
                    System.out.println("Conexão encerrada."); // Mensagem que indica que a conexão foi encerrada
                } catch (SQLException e) { // Captura exceções relacionadas ao fecho da conexão
                    System.out.println("Erro ao fechar a conexão: " + e.getMessage()); // Mensagem de erro ao tentar fechar a conexão
                }
            }
        }
    }
}
