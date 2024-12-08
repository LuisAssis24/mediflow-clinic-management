package sql.server;

import medi.flow.Clinica;

import java.sql.*;
import java.util.*;

public class SqlGestor {
    // Obtem uma lista com todos os IDs dos utilizadores
    public static List<Clinica.Utilizador> obterTodosUtilizadores() {
    Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
    List<Clinica.Utilizador> utilizadores = new ArrayList<>(); // Lista para armazenar os utilizadores

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

                    if (tipoUtilizador.equals("Medico")){
                        String sqlMedico = "SELECT Num_Ordem, Especialidade FROM Medico WHERE ID = ?";
                        PreparedStatement statementMedico = conexao.prepareStatement(sqlMedico);
                        statementMedico.setInt(1, id);
                        ResultSet resultadoMedico = statementMedico.executeQuery();
                        if (resultadoMedico.next()){
                            int numOrdem = resultadoMedico.getInt("Num_Ordem");
                            String especialidade = resultadoMedico.getString("Especialidade");
                            Clinica.Medico medico = new Clinica.Medico(id, cc, nome, password, tipoUtilizador, numOrdem, especialidade);
                            utilizadores.add(medico);
                        }
                    } else {
                        Clinica.Utilizador utilizador = new Clinica.Utilizador(id, cc, nome, password, tipoUtilizador);
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
    public static int criarUtilizadorEAdicionarMedico(String nome, String password, String tipoUtilizador, int cc,
                                                      String especialidade, int numOrdem) {
        int idUtilizadorGerado = -1; // Variável para armazenar o ID gerado
        String sqlCriarUtilizador = "{CALL CriarUtilizador(?, ?, ?, ?, ?)}"; // Procedure SQL para criar utilizador
        String sqlAdicionarMedico = "INSERT INTO Medico (ID, Especialidade, Num_Ordem) VALUES (?, ?, ?)";

        try (Connection conexao = SqlGeral.DatabaseConnection.getInstance()) {

            // 1. Criar o utilizador
            try (CallableStatement callableStatement = conexao.prepareCall(sqlCriarUtilizador)) {
                callableStatement.setInt(1, cc);                // CC
                callableStatement.setString(2, nome);           // Nome

                String senhaCifrada = CifrarPasswords.cifrar(password);
                callableStatement.setString(3, senhaCifrada); // Password cifrada

                callableStatement.setString(4, tipoUtilizador); // Tipo de utilizador
                callableStatement.registerOutParameter(5, java.sql.Types.INTEGER); // ID gerado

                callableStatement.execute();
                idUtilizadorGerado = callableStatement.getInt(5); // Obter o ID gerado
                System.out.println("Utilizador criado com ID: " + idUtilizadorGerado);
            }

            // 2. Verificar se o tipo de utilizador é "Médico"
            if ("Medico".equalsIgnoreCase(tipoUtilizador)) {
                // 3. Inserir os detalhes na tabela Medico
                try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlAdicionarMedico)) {
                    preparedStatement.setInt(1, idUtilizadorGerado);    // ID do médico
                    preparedStatement.setString(2, especialidade);      // Especialidade
                    preparedStatement.setInt(3, numOrdem);              // Número do médico
                    preparedStatement.executeUpdate();
                    System.out.println("Detalhes do médico adicionados com sucesso!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) { // Trata erros de cifragem
            System.out.println("Erro ao cifrar a senha: " + e.getMessage());
        }

        return idUtilizadorGerado; // Retorna o ID gerado para o utilizador
    }

    // Elimina um utilizador da base de dados com base no ID
    public static boolean eliminarUtilizador(int id) throws SQLException {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();

        if (conexao != null) {
            String sql = "DELETE FROM Utilizador WHERE ID = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int linhasAfetadas = stmt.executeUpdate();
                return linhasAfetadas > 0; // Retorna true se alguma linha foi excluída
            }
        }
        return false; // Retorna falso se a conexão for nula
    }

    // Obtem uma lista de todos os gestores
    public static List<HashMap<String, String>> obterTodosGestores() {
        List<HashMap<String, String>> gestores = new ArrayList<>();
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();

        if (conexao != null) {
            try {
                String sql = "SELECT ID, Nome, Password FROM Utilizador WHERE Tipo_Utilizador = 'Gestor'";
                PreparedStatement statement = conexao.prepareStatement(sql);
                ResultSet resultado = statement.executeQuery();

                while (resultado.next()) {
                    HashMap<String, String> gestor = new HashMap<>();
                    gestor.put("ID", resultado.getString("ID"));
                    gestor.put("Nome", resultado.getString("Nome"));
                    gestor.put("Password", resultado.getString("Password"));
                    gestores.add(gestor);
                }
            } catch (SQLException e) {
                System.out.println("Erro ao obter gestores: " + e.getMessage());
            }
        }
        return gestores;
    }

    // Procura um utilizador pelo ID usando um stored procedure
    public static HashMap<String, String> procurarUtilizadorPorID(int idUtilizador) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();  // Obtém a conexão com o banco de dados
        HashMap<String, String> resultadoUtilizador = new HashMap<>();  // Mapa para armazenar os dados do utilizador

        if (conexao != null) {
            try {
                // Chama a stored procedure ProcurarUtilizadorPorID
                String sql = "{CALL ProcurarUtilizadorPorID(?)}";
                CallableStatement callableStatement = conexao.prepareCall(sql);

                // Define o parâmetro de entrada (ID do utilizador)
                callableStatement.setInt(1, idUtilizador);

                // Executa a stored procedure e obtém o resultado
                ResultSet resultado = callableStatement.executeQuery();

                // Verifica se a consulta encontrou um utilizador
                if (resultado.next()) {
                    System.out.println("Utilizador encontrado com ID: " + idUtilizador);

                    // Preenche o mapa com os dados do utilizador
                    resultadoUtilizador.put("ID", String.valueOf(resultado.getInt("ID")));
                    resultadoUtilizador.put("CC", resultado.getString("CC"));
                    resultadoUtilizador.put("Nome", resultado.getString("Nome"));
                    resultadoUtilizador.put("Password", resultado.getString("Password"));
                    resultadoUtilizador.put("TipoUtilizador", resultado.getString("Tipo_Utilizador"));  // Atualize o nome da coluna aqui

                    // Debugging: Print the retrieved data
                    System.out.println("Dados do Utilizador: " + resultadoUtilizador);
                } else {
                    System.out.println("Nenhum utilizador encontrado com ID: " + idUtilizador);
                }
            } catch (SQLException e) {
                System.out.println("Erro ao executar a stored procedure ProcurarUtilizadorPorID: " + e.getMessage());
            }
        }

        return resultadoUtilizador;  // Retorna os dados do utilizador (ou vazio se não encontrado)
    }





}
