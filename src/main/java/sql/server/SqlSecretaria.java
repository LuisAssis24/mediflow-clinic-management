package sql.server;

import medi.flow.Clinica;

import java.sql.*;
import java.text.*;
import java.util.*;

public class SqlSecretaria {
    // Metodo para buscar uma consulta pelo numero SNS do paceinte
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


    // Metodo para criar uma nova consulta medica
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

    // Metodo para desmarcar uma consulta existente
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

    // Metodo para verificar se um paciente já existe no banco de dados
    public static Clinica.Paciente verificarPacienteExiste(int numero) {
    Connection conexao = SqlGeral.DatabaseConnection.getInstance();
    String sqlVerificar = "SELECT Numero_SNS, Nome, Contacto FROM Paciente WHERE Numero_SNS = ?";

    try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlVerificar)) {
        preparedStatement.setInt(1, numero);
        try (ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                int numeroSNS = rs.getInt("Numero_SNS");
                String nome = rs.getString("Nome");
                int contacto = rs.getInt("Contacto");
                return new Clinica.Paciente(numeroSNS, nome, contacto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erro ao verificar paciente: " + e.getMessage());
    }
    return null;
}

    // Metodo para criar um novo paciente caso eel nao exista
    public static void criarPaciente(int numero, String nome, int contacto) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        if (verificarPacienteExiste(numero) == null) {
            String sql = "CALL CriarPaciente(?, ?, ?)";
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                preparedStatement.setInt(1, numero);
                preparedStatement.setString(2, nome);
                preparedStatement.setInt(3, contacto);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao criar paciente: " + e.getMessage());
            }
        }
    }
}
