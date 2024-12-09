package sql.server;

import medi.flow.Clinica;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class SqlMedico {

    public static List<Clinica.RegistroClinico> obterTodosRegistros() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<Clinica.RegistroClinico> registros = new ArrayList<>(); // Lista para armazenar os registros clinicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                String sql = "SELECT ID_Ficha, Historico_Clinico, Alergias, Doencas_Cronicas, Cirurgias_Anteriores, Historico_Medicamentos, Numero_Utente FROM RegistroClinico";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as consultas à lista
                while (resultado.next()) {
                    int idFicha = resultado.getInt("ID_Ficha");

                    //Pega da base de dados o elemento como string e depois cria uma List com elementos da String separados a cada espaço
                    String historicoClinicoString = resultado.getString("Historico_Clinico");
                    List<String> historicoClinico = new ArrayList<>(Arrays.asList(historicoClinicoString.split(" ")));

                    String alergiasString = resultado.getString("Alergias");
                    List<String> alergias = new ArrayList<>(Arrays.asList(alergiasString.split(" ")));

                    String doencasCronicasString = resultado.getString("Doencas_Cronicas");
                    List<String> doencasCronicas = new ArrayList<>(Arrays.asList(doencasCronicasString.split(" ")));

                    String cirurgiasAnterioresString = resultado.getString("Cirurgias_Anteriores");
                    List<String> cirurgiasAnteriores = new ArrayList<>(Arrays.asList(cirurgiasAnterioresString.split(" ")));

                    String historicoMedicamentosString = resultado.getString("Historico_Medicamentos");
                    List<String> historicoMedicamentos = new ArrayList<>(Arrays.asList(historicoMedicamentosString.split(" ")));

                    int numeroSns = resultado.getInt("Numero_Utente");


                    Clinica.RegistroClinico registroClinico = new Clinica.RegistroClinico(idFicha, historicoClinico, alergias, doencasCronicas, cirurgiasAnteriores, historicoMedicamentos, numeroSns);
                    registros.add(registroClinico);
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter as consultas: " + e.getMessage());
            }
        }
        return registros; // Retorna a lista com os IDs das consultas
    }

    public static void criarRegistro(int numeroSns) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();

        String sql = "{CALL CriarRegistro(?)}"; // Chama a stored procedure CriarRegistro

        try (CallableStatement callableStatement = conexao.prepareCall(sql)) {
            // Definir parâmetros de entrada
            callableStatement.setInt(1, numeroSns); // SNS do paciente

            // Executar a stored procedure
            callableStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void editarRegistro(String historicoClinico, String alergias, String doencasCronicas, String cirurgiasAnteriores, String historicoMedicamentos) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();

        String sql = "{CALL EditarRegistro(?, ?, ?, ?, ?)}"; // Chama a stored procedure CriarRegistro

        try (CallableStatement callableStatement = conexao.prepareCall(sql)) {
            // Definir parâmetros de entrada
            callableStatement.setString(1, historicoClinico);
            callableStatement.setString(2, alergias);
            callableStatement.setString(3, doencasCronicas);
            callableStatement.setString(4, cirurgiasAnteriores);
            callableStatement.setString(5, historicoMedicamentos);

            // Executar a stored procedure
            callableStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
