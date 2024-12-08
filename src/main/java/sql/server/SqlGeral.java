package sql.server;

import java.sql.*;
import java.util.*;
import java.util.Date;

import medi.flow.Clinica;

public class SqlGeral {

    public static boolean verificarLogin(String idUtilizador, String senha) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();

        if (conexao != null) {
            try {
                // Cifra a senha fornecida pelo usuário
                String senhaCifrada = CifrarPasswords.cifrar(senha);

                // Declara uma consulta SQL para verificar as credenciais
                String sql = "SELECT * FROM Utilizador WHERE ID = ? AND Password = ?";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Substitui os placeholders (?) pelos valores fornecidos pelo utilizador
                statement.setString(1, idUtilizador);  // Usando ID para login
                statement.setString(2, senhaCifrada); // Comparando com a senha cifrada

                // Executa a consulta na base de dados e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Retorna verdadeiro se encontrar um registro correspondente
                return resultado.next();
            } catch (Exception e) {
                System.out.println("Erro ao verificar as credenciais: " + e.getMessage());
            }
        } else {
            System.out.println("Erro de conexão com o banco de dados.");
        }
        return false;
    }


    public static List<Clinica.Consulta> obterTodasConsultas() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<Clinica.Consulta> consultas = new ArrayList<>(); // Lista para armazenar os IDs das consultas

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                Date dataHoraAtual = new Date(); // Obtém a data e hora atuais para carregar apenas as consultas futuras.
                // Declara uma consulta SQL para obter todos os IDs das consultas
                String sql = "SELECT ID_Consulta, Data, Hora, Motivo, Nome_Paciente, SNS_Paciente, Num_Sala, ID_Medico, Contacto FROM Consulta";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as consultas à lista
                while (resultado.next()) {
                    if (resultado.getDate("Data").after(dataHoraAtual)) {
                        int idConsulta = resultado.getInt("ID_Consulta");
                        String data = resultado.getString("Data");
                        String hora = resultado.getString("Hora");
                        String motivo = resultado.getString("Motivo");
                        String nomePaciente = resultado.getString("Nome_Paciente");
                        int snsPaciente = resultado.getInt("SNS_Paciente");
                        int numSala = resultado.getInt("Num_Sala");
                        int idMedico = resultado.getInt("ID_Medico");
                        int contacto = resultado.getInt("Contacto");

                        Clinica.Consulta consulta = new Clinica.Consulta(idConsulta, data, hora, motivo, nomePaciente, snsPaciente, numSala, idMedico, contacto);
                        consultas.add(consulta);
                    }
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter as consultas: " + e.getMessage());
            }
        }
        return consultas; // Retorna a lista com os IDs das consultas
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
                    if (rs.getString("Tipo_Utilizador").equals("Medico")) {
                        SqlMedico.idMedicoAUtilizarOSistema = Integer.parseInt(utilizador);
                    }
                    return rs.getString("Tipo_Utilizador"); // Retorna o tipo de utilizador
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao verificar o tipo de utilizador: " + e.getMessage());
            }
        }
        return null; // Retorna null se não encontrar o tipo do utilizador
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
            try {
                if (connection == null || connection.isClosed()) { // Verifica se a conexão é nula ou foi encerrada
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Conexão com MySQL foi recriada!");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao MySQL: " + e.getMessage());
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
