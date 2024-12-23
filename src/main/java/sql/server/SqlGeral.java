package sql.server;

import java.sql.*;
import java.util.*;
import java.util.Date;

import medi.flow.*;

/**
 *
 * @author Luís Assis
 * @author Pedro Sampaio
 */

// Classe para executar consultas SQL gerais
public class SqlGeral {

    // Método para verificar as credenciais de login
    public static String verificarLogin(String idUtilizador, String password) {
        Connection conexao = DatabaseConnection.getInstance();// Obtém a conexão com a base de dados
        String tipoUtilizador = null;// Variável para armazenar o tipo de utilizador

        if (conexao != null) {// Verifica se a conexão foi estabelecida com sucesso
            try {// Tenta executar a consulta SQL
                // Cifra a password fornecida pelo usuário
                String passCifrada = CifrarPasswords.cifrar(password);

                // Declara uma consulta SQL para verificar as credenciais
                String sql = "CALL VerificarLogin(?, ?)";// Chamada de procedimento armazenado
                PreparedStatement statement = conexao.prepareStatement(sql);// Prepara a consulta

                // Substitui os placeholders (?) pelos valores fornecidos pelo utilizador
                statement.setString(1, idUtilizador);  // Usando ID para login
                statement.setString(2, passCifrada); // Comparando com a password cifrada

                // Executa a consulta na base de dados e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Retorna o tipo de utilizador se as credenciais estiverem corretas
                if (resultado.next()) {// Verifica se a consulta retornou algum resultado
                    tipoUtilizador = resultado.getString("Tipo_Utilizador");// Obtém o tipo de utilizador
                }
            } catch (Exception e) {// Trata erros relacionados ao SQL
                System.out.println("Erro ao verificar as credenciais: " + e.getMessage());// Mensagem de erro
            }
        } else {// Mensagem de erro se a conexão não foi estabelecida
            System.out.println("Erro de conexão com o banco de dados.");// Mensagem de erro
        }
        return tipoUtilizador;// Retorna o tipo de utilizador
    }

    // A classe DatabaseConnection é a responsavel por criar a conexão com a base de dados
    public static class DatabaseConnection {

        // Definição das constantes para o URL da base de dados, o utilizador e a palavra-passe
        private static final String URL = "jdbc:mysql://estga-dev.ua.pt:3306/PTDA24_BD_04";// URL da base de dados
        private static final String USER = "PTDA24_04";// Utilizador da base de dados
        private static final String PASSWORD = "Etjs=889";// Palavra-passe da base de dados

        private static Connection connection; // Declaração da variável de conexão, que será partilhada pelo sistema todo

        // Construtor privado para evitar a criação de instâncias da classe diretamente
        private DatabaseConnection() {
        }

        // Método estático para obter a conexão com a base de dados
        public static Connection getInstance() {
            try {
                if (connection == null || connection.isClosed()) { // Verifica se a conexão é nula ou foi encerrada
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);// Cria uma nova conexão
                    System.out.println("Conexão com MySQL foi recriada!");// Mensagem de sucesso
                }
            } catch (SQLException e) {// Captura exceções relacionadas à conexão
                System.out.println("Erro ao conectar ao MySQL: " + e.getMessage());// Mensagem de erro
            }
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
