package sql.server;

import medi.flow.*;

import java.sql.*;
import java.util.*;

public class SqlGestor {
    // Obtem uma lista com todos os IDs dos utilizadores
    public static List<Utilizador> obterTodosUtilizadores() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<Utilizador> utilizadores = new ArrayList<>(); // Lista para armazenar os utilizadores

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todos os utilizadores
                String sql = "SELECT ID, CC, Nome, Password, Tipo_Utilizador FROM Utilizador";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os utilizadores à lista
                while (resultado.next()) {
                    int id = resultado.getInt("ID");
                    int cc = resultado.getInt("CC");
                    String nome = resultado.getString("Nome");
                    String password = resultado.getString("Password");
                    String tipoUtilizador = resultado.getString("Tipo_Utilizador");

                    if (tipoUtilizador.equals("Medico")) {
                        String sqlMedico = "SELECT Num_Ordem, Especialidade FROM Medico WHERE ID = ?";
                        PreparedStatement statementMedico = conexao.prepareStatement(sqlMedico);
                        statementMedico.setInt(1, id);
                        ResultSet resultadoMedico = statementMedico.executeQuery();
                        if (resultadoMedico.next()) {
                            int numOrdem = resultadoMedico.getInt("Num_Ordem");
                            String especialidade = resultadoMedico.getString("Especialidade");
                            Medico medico = new Medico(id, cc, nome, password, tipoUtilizador, numOrdem, especialidade);
                            utilizadores.add(medico);
                        }
                    } else {
                        Utilizador utilizador = new Utilizador(id, cc, nome, password, tipoUtilizador);
                        utilizadores.add(utilizador);
                    }
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os utilizadores: " + e.getMessage());
            }
        }

        return utilizadores; // Retorna a lista com os utilizadores
    }

    // Cria um utilizador e, se for medico, adiciona os detalhes na tabela medico
    public static int criarUtilizador(String nome, String password, String tipoUtilizador, int cc, String especialidade, int numOrdem) {
        int idUtilizadorGerado = -1;

        try (Connection conexao = SqlGeral.DatabaseConnection.getInstance()) {

            // Criar o utilizador
            String sqlCriarUtilizador = "{CALL CriarUtilizador(?, ?, ?, ?, ?)}";
            try (CallableStatement callableStatement = conexao.prepareCall(sqlCriarUtilizador)) {
                callableStatement.setInt(1, cc);                // CC
                callableStatement.setString(2, nome);           // Name

                String passwordCifrada = CifrarPasswords.cifrar(password);
                callableStatement.setString(3, passwordCifrada); // Encrypted password

                callableStatement.setString(4, tipoUtilizador); // User type
                callableStatement.registerOutParameter(5, java.sql.Types.INTEGER); // Generated ID

                callableStatement.execute();
                idUtilizadorGerado = callableStatement.getInt(5); // Get the generated ID
                System.out.println("User created with ID: " + idUtilizadorGerado);
            }

            // Ver se o utilizador é um médico
            if ("Medico".equalsIgnoreCase(tipoUtilizador)) {
                // Adicionar os detalhes do médico
                String sqlInserirMedico = "{CALL InserirMedico(?, ?, ?)}";
                try (CallableStatement callableStatement = conexao.prepareCall(sqlInserirMedico)) {
                    callableStatement.setInt(1, idUtilizadorGerado);    // Doctor's ID
                    callableStatement.setString(2, especialidade);      // Specialty
                    callableStatement.setInt(3, numOrdem);              // Doctor's number
                    callableStatement.executeUpdate();
                    System.out.println("Doctor details added successfully!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error encrypting the password: " + e.getMessage());
        }

        return idUtilizadorGerado; // retornar o id do utilizador criado
    }

    public static boolean eliminarUtilizador(int idUtilizador, String tipoUtilizador) throws SQLException {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        boolean deletado = false;

        if (conexao != null) {
            try {
                // Ver se o utilizador é um médico para eliminar da tabela Medico
                if ("Medico".equalsIgnoreCase(tipoUtilizador)) {
                    String sqlMedico = "{CALL EliminarMedico(?)}";
                    try (CallableStatement callableStatement = conexao.prepareCall(sqlMedico)) {
                        callableStatement.setInt(1, idUtilizador);
                        callableStatement.executeUpdate();
                    }
                }

                // Deleter o utilizador
                String sqlUtilizador = "{CALL EliminarUtilizador(?)}";
                try (CallableStatement callableStatement = conexao.prepareCall(sqlUtilizador)) {
                    callableStatement.setInt(1, idUtilizador);
                    int affectedRows = callableStatement.executeUpdate();
                    deletado = affectedRows > 0;
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("foreign key constraint fails")) {
                    System.err.println("Erro ao eliminar utilizador: O utilizador tem consultas associadas.");
                } else {
                    System.err.println("Erro ao eliminar utilizador: " + e.getMessage());
                    throw e;
                }
            }
        }
        return deletado;
    }
}
