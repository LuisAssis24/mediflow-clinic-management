package sql.server;

import java.sql.*;
import java.text.*;
import java.util.*;

public class SqlGeral {

    public static boolean verificarLogin(String utilizador, String senha) {

        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados

        if (conexao != null) {  // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para verificar as credenciais
                String sql = "SELECT * FROM Utilizador WHERE ID = ? AND Password = ?"; // Vai a tabela utilizador e verifica o Nome e a Password
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Substitui os placeholders (?) pelos valores fornecidos pelo utilizador
                statement.setString(1, utilizador);
                statement.setString(2, senha);

                // Executa a consulta a base de dados e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Retorna verdadeiro se encontrar um registo correspondente
                return resultado.next();
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao verificar as credenciais: " + e.getMessage());
            }
        } else {
            // Se a conexão não for estabelecida, exibe uma mensagem de erro
            System.out.println("Erro de conexão com o banco de dados.");
        }
        return false; // Retorna falso se a conexão falhar ou as credenciais forem inválidas
    }

    public static HashMap<String, String> procurarConsultaPorSNS(int snsPaciente) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com o banco de dados
        HashMap<String, String> resultadoConsulta = new HashMap<>(); // Mapa para armazenar os resultados

        if (conexao != null) { // Verifica se a conexão foi estabelecida
            try {
                // Chama a *stored procedure* VerConsultaPorSNS
                String sql = "{CALL ProcurarConsulta(?)}";
                CallableStatement callableStatement = conexao.prepareCall(sql);

                // Define o parâmetro de entrada
                callableStatement.setInt(1, snsPaciente);

                // Executa a *procedure* e obtém o resultado
                ResultSet resultado = callableStatement.executeQuery();

                // Verifica se existem resultados
                if (resultado.next()) {
                    System.out.println("Consulta encontrada para SNS: " + snsPaciente);
                    // Preenche o mapa com os dados retornados
                    resultadoConsulta.put("idConsulta", String.valueOf(resultado.getInt("ID_Consulta")));
                    resultadoConsulta.put("data", resultado.getString("Data"));
                    resultadoConsulta.put("hora", resultado.getString("Hora"));
                    resultadoConsulta.put("motivo", resultado.getString("Motivo"));
                    resultadoConsulta.put("nomePaciente", resultado.getString("Nome_Paciente"));
                    resultadoConsulta.put("snsPaciente", String.valueOf(resultado.getInt("Sns_Paciente")));
                    resultadoConsulta.put("contacto", String.valueOf(resultado.getInt("Contacto")));
                    resultadoConsulta.put("numSala", String.valueOf(resultado.getInt("Num_Sala")));
                    resultadoConsulta.put("idMedico", String.valueOf(resultado.getInt("ID_Medico")));
                } else {
                    System.out.println("Nenhuma consulta encontrada para o SNS informado: " + snsPaciente);
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao executar a *stored procedure* VerConsultaPorSNS: " + e.getMessage());
            }
        }

        return resultadoConsulta; // Retorna os dados da consulta
    }



    public static int criarConsulta(String data, String hora, String motivo, String nomePaciente, int snsPaciente, int contacto, int numSala, int idMedico) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        int idConsultaGerado = -1; // Variável para armazenar o ID gerado pela base de dados

        // Reformatar a data para o formato SQL
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataSql = "";
        try {
            dataSql = sqlDateFormat.format(inputDateFormat.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Adicionar :00 ao final da hora
        String horaSql = hora + ":00";

        // Chamar a stored procedure
        String sql = "{CALL MarcarConsulta(?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Chama a stored procedure MarcarConsulta

        try (CallableStatement callableStatement = conexao.prepareCall(sql)) {
            // Definir parâmetros de entrada
            callableStatement.setDate(1, java.sql.Date.valueOf(dataSql)); // Data
            callableStatement.setTime(2, java.sql.Time.valueOf(horaSql)); // Hora
            callableStatement.setString(3, motivo); // Motivo
            callableStatement.setString(4, nomePaciente); // Nome do paciente
            callableStatement.setInt(5, snsPaciente); // SNS do paciente
            callableStatement.setInt(6, contacto); // Contacto do paciente
            callableStatement.setInt(7, numSala); // Número da sala
            callableStatement.setInt(8, idMedico); // ID do médico
            callableStatement.registerOutParameter(9, java.sql.Types.INTEGER); // ID da consulta (gerado pela base de dados)

            // Executar a stored procedure
            callableStatement.execute();

            // Recuperar o ID gerado
            idConsultaGerado = callableStatement.getInt(9); // Pega o valor gerado da primary key

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idConsultaGerado; // Retorna o ID gerado para aprsentar ao utilizador
    }

    public static ArrayList<Integer> obterTodasConsultas() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        ArrayList<Integer> consultas = new ArrayList<>(); // Lista para armazenar os IDs das consultas

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todos os IDs das consultas
                String sql = "SELECT ID_Consulta FROM Consulta";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os IDs das consultas à lista
                while (resultado.next()) {
                    consultas.add(resultado.getInt("ID_Consulta"));
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter as consultas: " + e.getMessage());
            }
        }

        return consultas; // Retorna a lista com os IDs das consultas

    }

    public static HashMap<String, String> dadosConsulta(int IDConsulta) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        HashMap<String, String> dadosConsulta = new HashMap<>(); // Mapa para armazenar os dados da consulta

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter os dados da consulta
                String sql = "SELECT * FROM Consulta WHERE ID_Consulta = ?";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Substitui o placeholder (?) pelo valor do ID da consulta
                statement.setInt(1, IDConsulta);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Verifica se encontrou um registo
                if (resultado.next()) {
                    // Adiciona os dados da consulta ao mapa
                    dadosConsulta.put("data", resultado.getString("Data"));
                    dadosConsulta.put("hora", resultado.getString("Hora"));
                    dadosConsulta.put("motivo", resultado.getString("Motivo"));
                    dadosConsulta.put("nomePaciente", resultado.getString("Nome_Paciente"));
                    dadosConsulta.put("snsPaciente", resultado.getString("Sns_Paciente"));
                    dadosConsulta.put("contacto", resultado.getString("Contacto"));
                    dadosConsulta.put("numSala", resultado.getString("Num_Sala"));
                    dadosConsulta.put("idMedico", resultado.getString("ID_Medico"));
                    dadosConsulta.put("idConsulta", resultado.getString("ID_Consulta"));
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os dados da consulta: " + e.getMessage());
            }
        }
        return dadosConsulta; // Retorna o mapa com os dados da consulta
    }

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
                    dadosUtilizador.put("TipoUtilizador", verificarTipoUtilizador(IDUtilizador));
                    // Adicionar mais campos aqui, se necessário
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os dados do utilizador: " + e.getMessage());
            }
        }
        return dadosUtilizador; // Retorna o mapa com os dados do utilizador
    }

    public static void desmarcarConsulta(int IDConsulta){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter os dados da consulta
                String sql = "DELETE FROM Consulta WHERE ID_Consulta = ?";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Substitui o placeholder (?) pelo valor do ID da consulta
                statement.setInt(1, IDConsulta);

                // Executa a consulta e armazena o resultado
                statement.executeUpdate();
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao desmarcar a consulta: " + e.getMessage());
            }
        }
    }

    public static String verificarTipoUtilizador(String utilizador) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Consulta SQL para obter o tipo de utilizador com base no ID
                String sql = "SELECT Tipo_Utilizador FROM Utilizador WHERE ID = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, utilizador);

                // Executa a consulta e armazena o resultado
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) { // Se encontrar um registo
                    return rs.getString("Tipo_Utilizador"); // Retorna o tipo de utilizador
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao verificar o tipo de utilizador: " + e.getMessage());
            }
        }
        return null; // Retorna null se não encontrar o tipo do utilizador
    }

    public static void verificarPacienteExiste(int numero, String nome, int contacto) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        String sqlVerificar = "SELECT COUNT(*) FROM Paciente WHERE Numero_SNS = ?"; //Apartir do numero de tuplos existentes na relação Paciente compara p numero do TextField com o numero de sns
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlVerificar)) {
            preparedStatement.setInt(1, numero);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    if(rs.getInt(1) < 1){
                        String sql = "{CALL CriarPaciente(?, ?, ?)}"; // Chama a stored procedure MarcarConsulta

                        try (CallableStatement callableStatement = conexao.prepareCall(sql)) {
                            // Definir parâmetros de entrada
                            callableStatement.setInt(1, numero); // SNS do paciente
                            callableStatement.setString(2, nome); // Nome do paciente
                            callableStatement.setInt(3, contacto); // Contacto do paciente

                            // Executar a stored procedure
                            callableStatement.execute();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar paciente: " + e.getMessage());
        }
    }

    private static boolean idExisteNaTabela(Connection conexao, int id, String tabela, String colunaId) {
        try {
            // Declara uma consulta SQL para verificar a existência do ID na tabela
            String sql = "SELECT " + colunaId + " FROM " + tabela + " WHERE " + colunaId + " = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            // Substitui o placeholder (?) pelo valor do ID
            stmt.setInt(1, id);

            // Executa a consulta e verifica se existe pelo menos um registo correspondente
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna verdadeiro se encontrar um registo
        } catch (SQLException e) { // Trata erros relacionados ao SQL
            System.out.println("Erro ao verificar a tabela " + tabela + ": " + e.getMessage());
        }
        return false; // Retorna falso se ocorrer um erro ou não encontrar o ID
    }

    // A classe DatabaseConnection é a responsavel por criar a conexão com a base de dados
    public static class DatabaseConnection {

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
