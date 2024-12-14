package sql.server;

import medi.flow.RegistoClinico;

import java.sql.*;
import java.util.*;

public class SqlMedico {

    public static List<RegistoClinico> obterTodosRegistros() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<RegistoClinico> registros = new ArrayList<>(); // Lista para armazenar os registros clinicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                String sql = "SELECT ID_Ficha, Historico_Doencas, Alergias, Operacoes, Numero_Utente FROM RegistoClinico";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as consultas à lista
                while (resultado.next()) {
                    int idFicha = resultado.getInt("ID_Ficha");

                    //Pega da base de dados o elemento como string e depois cria uma List com elementos da String separados a cada espaço
                    String historicoDoencasString = resultado.getString("Historico_Doencas");
                    List<String> historicoDoencas = new ArrayList<>(Arrays.asList(historicoDoencasString.split(" ")));

                    String alergiasString = resultado.getString("Alergias");
                    List<String> alergias = new ArrayList<>(Arrays.asList(alergiasString.split(" ")));

                    String operacoesString = resultado.getString("Operacoes");
                    List<String> operacoes = new ArrayList<>(Arrays.asList(operacoesString.split(" ")));

                    int numeroSns = resultado.getInt("Numero_Utente");


                    RegistoClinico registoClinico = new RegistoClinico(idFicha, historicoDoencas, alergias, operacoes, numeroSns);
                    registros.add(registoClinico);
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

    public static void criarEntradaRegisto(int numeroSns, int iD_Medico, int iD_Ficha, int data, String motivo, String tratamento) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        String sql = "{CALL CriarEntradaRegistro(?, ?, ?, ?, ?, ?)}";

        try(CallableStatement callableStatement = conexao.prepareCall(sql)){

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static List<Clinica.EntradaRegistroClinico> obterTodasEntradas(){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        List<Clinica.EntradaRegistroClinico> entradas = new ArrayList<>();

        if (conexao != null){
            try{
                String sql = "SELECT ID_Registro, ID_Ficha, Data, ID_Medico, Tratamento, ID_Consulta, Assunto FROM EntradaRegistoClinicoPanel";
                PreparedStatement statement = conexao.prepareStatement(sql);

                ResultSet resultado = statement.executeQuery();

                while(resultado.next()){
                    int id_Registro = resultado.getInt("ID_Registro");
                    int id_Ficha = resultado.getInt("ID_Ficha");
                    String data = resultado.getString("Data");
                    int id_Medico = resultado.getInt("ID_Medico");
                    String tratamento = resultado.getString("Tratamento");
                    int id_Consulta = resultado.getInt("ID_Consulta");
                    String assunto = resultado.getString("Assunto");
                }
                
                Clinica.EntradaRegistroClinico entradas = new Clinica.EntradaRegistroClinico();


                Clinica.EntradaRegistroClinico entradaRegistroClinico = new Clinica.EntradaRegistroClinico()

            }catch(SQLException e){

            }


        }



        return entradas;
    }*/
}
