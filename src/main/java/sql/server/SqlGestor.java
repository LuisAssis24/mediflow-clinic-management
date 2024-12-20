package sql.server;

import medi.flow.*;

import java.sql.*;
import java.util.*;

// Classe para executar consultas SQL relacionadas com a gestão de utilizadores
public class SqlGestor {
    // Obtem uma lista com todos os IDs dos utilizadores
    public static List<Utilizador> obterTodosUtilizadores() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<Utilizador> utilizadores = new ArrayList<>(); // Lista para armazenar os utilizadores

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todos os utilizadores
                String sql = "{CALL ObterTodosUtilizadores()}";
                CallableStatement statement = conexao.prepareCall(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os utilizadores à lista
                while (resultado.next()) {
                    int id = resultado.getInt("ID");
                    int cc = resultado.getInt("CC");
                    String nome = resultado.getString("Nome");
                    String password = resultado.getString("Password");
                    String tipoUtilizador = resultado.getString("Tipo_Utilizador");

                    // Ver se o utilizador é um médico para obter os detalhes da tabela Medico
                    if (tipoUtilizador.equals("Médico")) {// Verifica se o utilizador é um médico
                        String sqlMedico = "{CALL ObterMedicoPorId(?)}";// Declara uma consulta SQL para obter os detalhes do médico
                        CallableStatement callableStatement = conexao.prepareCall(sqlMedico);// Prepara a consulta
                        callableStatement.setInt(1, id);// Substitui o placeholder (?) pelo ID do médico
                        ResultSet resultadoMedico = callableStatement.executeQuery();// Executa a consulta
                        if (resultadoMedico.next()) {// Verifica se a consulta retornou algum resultado
                            int numOrdem = resultadoMedico.getInt("Num_Ordem");// Obtém o número de ordem do médico
                            String especialidade = resultadoMedico.getString("Especialidade");// Obtém a especialidade do médico
                            Medico medico = new Medico(id, cc, nome, password, tipoUtilizador, numOrdem, especialidade);// Cria um objeto Médico
                            utilizadores.add(medico);// Adiciona o médico à lista de utilizadores
                        }
                    } else {// Se não for médico, cria um objeto Utilizador
                        Utilizador utilizador = new Utilizador(id, cc, nome, password, tipoUtilizador);// Cria um objeto Utilizador
                        utilizadores.add(utilizador);// Adiciona o utilizador à lista de utilizadores
                    }
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os utilizadores: " + e.getMessage());// Mensagem de erro
            }
        }
        return utilizadores; // Retorna a lista com os utilizadores
    }

    // Cria um utilizador e, se for medico, adiciona os detalhes na tabela medico
    public static int criarUtilizador(String nome, String password, String tipoUtilizador, int cc, String especialidade, int numOrdem, int sala) {
        int idUtilizadorGerado = -1;// ID do utilizador gerado

        try (Connection conexao = SqlGeral.DatabaseConnection.getInstance()) {

            // Criar o utilizador
            String sqlCriarUtilizador = "{CALL CriarUtilizador(?, ?, ?, ?, ?)}";// Declara a query para criar um utilizador
            try (CallableStatement callableStatement = conexao.prepareCall(sqlCriarUtilizador)) {// Prepara a query
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

            // Verifica se o utilizador é um médico
            if ("Médico".equalsIgnoreCase(tipoUtilizador)) {
                // Adicionar os detalhes do médico
                String sqlInserirMedico = "{CALL InserirMedico(?, ?, ?, ?)}";
                try (CallableStatement callableStatement = conexao.prepareCall(sqlInserirMedico)) {
                    callableStatement.setInt(1, idUtilizadorGerado);    // idUtilizador
                    callableStatement.setString(2, especialidade);      // especialidade
                    callableStatement.setInt(3, numOrdem);              // numero da ordem
                    callableStatement.setInt(4, sala);                  // sala
                    callableStatement.executeUpdate();
                    System.out.println("Médico inserido com sucesso");
                }
            }

        } catch (SQLException e) {// Trata erros relacionados ao SQL
            e.printStackTrace();// Mensagem de erro
        } catch (Exception e) {// Trata erros gerais
            System.out.println("Error encrypting the password: " + e.getMessage());// Mensagem de erro
        }

        return idUtilizadorGerado; // retornar o id do utilizador criado
    }

    public static List<Integer> obterSalas() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<Integer> salas = new ArrayList<>(); // Lista para armazenar as salas

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todas as salas
                String sql = "{CALL ObterTodasSalas()}";
                CallableStatement statement = conexao.prepareCall(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as salas à lista
                while (resultado.next()) {
                    int numSalaAtual = resultado.getInt("Sala");
                    salas.add(numSalaAtual);
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter as salas: " + e.getMessage());// Mensagem de erro
            }
        }
        return salas; // Retorna a lista com as salas
    }

    // Atualiza os dados de um utilizador
    public static void eliminarUtilizador(int idUtilizador, String tipoUtilizador) throws SQLException {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados


        if (conexao != null) {// Verifica se a conexão foi estabelecida com sucesso
            try {// Tenta executar a query
                // Ver se o utilizador é um médico para eliminar da tabela Medico
                if ("Médico".equalsIgnoreCase(tipoUtilizador)) {// Verifica se o utilizador é um médico
                    String sqlMedico = "{CALL EliminarMedico(?)}";// Declara a query para eliminar um médico
                    try (CallableStatement callableStatement = conexao.prepareCall(sqlMedico)) {// Prepara a query
                        callableStatement.setInt(1, idUtilizador);// Substitui o placeholder (?) pelo ID do médico
                        callableStatement.executeUpdate();// Executa a query
                    }
                }

                // Deleter o utilizador
                String sqlUtilizador = "{CALL EliminarUtilizador(?)}";// Declara a query para eliminar um utilizador
                try (CallableStatement callableStatement = conexao.prepareCall(sqlUtilizador)) {// Prepara a query
                    callableStatement.setInt(1, idUtilizador);// Substitui o placeholder (?) pelo ID do utilizador
                    callableStatement.executeUpdate();// Executa a query
                }
            } catch (SQLException e) {// Trata erros relacionados ao SQL
                if (e.getMessage().contains("foreign key constraint fails")) {// Verifica se o erro é devido a uma restrição de chave estrangeira
                    System.err.println("Erro ao eliminar utilizador: O utilizador tem consultas associadas.");// Mensagem de erro
                } else {// Se não for uma restrição de chave estrangeira, mostra a mensagem de erro
                    System.err.println("Erro ao eliminar utilizador: " + e.getMessage());// Mensagem de erro
                    throw e;// Lança a exceção
                }
            }
        }
    }
}
