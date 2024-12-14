package sql.server;

import medi.flow.RegistoClinico;
import medi.flow.RegistoClinico.EntradaRegistoClinico;

import java.sql.*;
import java.util.*;

public class SqlMedico {

    public static List<RegistoClinico> obterTodosRegistos() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<RegistoClinico> registros = new ArrayList<>(); // Lista para armazenar os registros clinicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                String sql = "SELECT Historico_Doencas, Alergias, Operacoes, Numero_Sns FROM RegistoClinico";
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as consultas à lista
                while (resultado.next()) {
                    //Pega da base de dados o elemento como string e depois cria uma List com elementos da String separados a cada espaço
                    String historicoDoencasString = resultado.getString("Historico_Doencas");
                    List<String> historicoDoencas = new ArrayList<>(Arrays.asList(historicoDoencasString.split(" ")));

                    String alergiasString = resultado.getString("Alergias");
                    List<String> alergias = new ArrayList<>(Arrays.asList(alergiasString.split(" ")));

                    String operacoesString = resultado.getString("Operacoes");
                    List<String> operacoes = new ArrayList<>(Arrays.asList(operacoesString.split(" ")));

                    int numeroSns = resultado.getInt("Numero_Sns");


                    RegistoClinico registoClinico = new RegistoClinico(historicoDoencas, alergias, operacoes, numeroSns);
                    registoClinico.setEntradasRegistosClinicos(obterTodasEntradas(registoClinico));
                    registros.add(registoClinico);
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter registos: " + e.getMessage());
            }
        }
        return registros; // Retorna a lista com os IDs das consultas
    }


    public static List<EntradaRegistoClinico> obterTodasEntradas(RegistoClinico registoClinico){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        List<EntradaRegistoClinico> entradas = new ArrayList<>();

        if (conexao != null){
            try{
                String sql = "SELECT Data, ID_Medico, Tratamento, ID_Consulta, Assunto FROM EntradaRegistoClinicoPanel";
                PreparedStatement statement = conexao.prepareStatement(sql);

                ResultSet resultado = statement.executeQuery();

                while(resultado.next()){

                    String data = resultado.getString("Data");
                    int idMedico = resultado.getInt("ID_Medico");
                    String tratamentoString = resultado.getString("Tratamento");
                    List<String> tratamento = new ArrayList<>(Arrays.asList(tratamentoString.split(" ")));
                    int idConsulta = resultado.getInt("ID_Consulta");
                    String assunto = resultado.getString("Assunto");

                    EntradaRegistoClinico entrada = registoClinico.new EntradaRegistoClinico(data, idMedico, tratamento, idConsulta, assunto);
                    entradas.add(entrada);
                }
            }catch(SQLException e){
                System.out.println("Erro ao obter entradas: " + e.getMessage());
            }
        }
        return entradas;
    }
}
