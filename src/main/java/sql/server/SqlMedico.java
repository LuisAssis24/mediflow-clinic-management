package sql.server;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class SqlMedico {

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

    public static void editarRegistro(String historicoClinico, String alergias, String doencasCronicas, String cirurgiasAnteriores, String historicoMedicamentos){
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
