package sql.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SqlGestor {
    public static ArrayList<String> obterTodosUtilizadores() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        ArrayList<String> utilizadores = new ArrayList<>(); // Lista para armazenar os IDs dos utilizadores

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todos os IDs dos utilizadores
                String sql = "SELECT ID FROM Utilizador";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os IDs dos utilizadores à lista
                while (resultado.next()) {
                    utilizadores.add(resultado.getString("ID"));
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os utilizadores: " + e.getMessage());
            }
        }

        return utilizadores; // Retorna a lista com os IDs dos utilizadores
    }

    public static HashMap<String, String> dadosUtilizador(String IDUtilizador) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        HashMap<String, String> dadosUtilizador = new HashMap<>(); // Mapa para armazenar os dados do utilizador

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter os dados do utilizador
                String sql = "SELECT * FROM Utilizador WHERE ID = ?";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Substitui o placeholder (?) pelo valor do ID do utilizador
                statement.setString(1, IDUtilizador);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Verifica se encontrou um registo
                if (resultado.next()) {
                    // Adiciona os dados do utilizador ao mapa
                    dadosUtilizador.put("ID", resultado.getString("ID"));
                    dadosUtilizador.put("Nome", resultado.getString("Nome"));
                    dadosUtilizador.put("Password", resultado.getString("Password"));
                    dadosUtilizador.put("TipoUtilizador", SqlGeral.verificarTipoUtilizador(IDUtilizador));
                    // Adicionar mais campos aqui, se necessário
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os dados do utilizador: " + e.getMessage());
            }
        }
        return dadosUtilizador; // Retorna o mapa com os dados do utilizador
    }

    public static int criarMedico(String nome, String password, String especialidade, int numeroMedico, int cc) {
        int idMedicoGerado = -1; // Variável para armazenar o ID do médico gerado
        String sql = "{CALL CriarMedico(?, ?, ?, ?, ?, ?)}"; // Chama a procedure CriarMedico

        try (Connection conexao = SqlGeral.DatabaseConnection.getInstance();
             CallableStatement callableStatement = conexao.prepareCall(sql)) {

            // Definir parâmetros de entrada
            callableStatement.setInt(1, cc); // CC
            callableStatement.setString(2, nome); // Nome
            callableStatement.setString(3, password); // Password
            callableStatement.setString(4, especialidade); // Especialidade
            callableStatement.setInt(5, numeroMedico); // Número do médico

            // Registrar o parâmetro de saída
            callableStatement.registerOutParameter(6, java.sql.Types.INTEGER); // ID_Medico gerado

            // Executar a stored procedure
            callableStatement.execute();

            // Recuperar o ID do médico gerado
            idMedicoGerado = callableStatement.getInt(6);

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return idMedicoGerado; // Retorna o ID do médico gerado para apresentar ao utilizador
    }

    public static int criarUtilizador(String nome, String password, String tipoUtilizador, int cc) {
        int idUtilizadorGerado = -1; // Variável para armazenar o ID gerado
        String sql = "{CALL CriarUtilizador(?, ?, ?, ?, ?)}"; // Chama a procedure CriarUtilizador

        try (Connection conexao = SqlGeral.DatabaseConnection.getInstance();
             CallableStatement callableStatement = conexao.prepareCall(sql)) {

            // Definir parâmetros de entrada
            callableStatement.setInt(1, cc); // CC
            callableStatement.setString(2, nome); // Nome
            callableStatement.setString(3, password); // Password
            callableStatement.setString(4, tipoUtilizador); // Tipo_Utilizador

            // Registrar o parâmetro de saída
            callableStatement.registerOutParameter(5, java.sql.Types.INTEGER); // ID gerado

            // Executar a stored procedure
            callableStatement.execute();

            // Recuperar o ID gerado
            idUtilizadorGerado = callableStatement.getInt(5);

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return idUtilizadorGerado; // Retorna o ID gerado para apresentar ao utilizador
    }
}
