package sql.server;

import medi.flow.*;
import medi.flow.RegistoClinico.*;
import java.sql.*;
import java.util.*;

import static medi.flow.Text.splitStringToList;

public class SqlMedico {

    public static List<RegistoClinico> obterTodosRegistos() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<RegistoClinico> registos = new ArrayList<>(); // Lista para armazenar os registros clinicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                String sql = "{CALL ObterRegistosClinicos()}";
                CallableStatement statement = conexao.prepareCall(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as consultas à lista
                while (resultado.next()) {
                    //Pega da base de dados o elemento como string e depois cria uma List com elementos da String separados a cada ponto
                    //Confirma se a string é nula e se for, cria uma lista vazia
                    String historicoDoencasString = resultado.getString("Historico_Doencas");
                    if (historicoDoencasString == null) {
                        historicoDoencasString = "";
                    }
                    List<String> historicoDoencas = splitStringToList(historicoDoencasString);


                    String alergiasString = resultado.getString("Alergias");
                    if (alergiasString == null) {
                        alergiasString = "";
                    }
                    List<String> alergias = splitStringToList(alergiasString);


                    String operacoesString = resultado.getString("Operacoes");
                    if (operacoesString == null) {
                        operacoesString = "";
                    }
                    List<String> operacoes = splitStringToList(operacoesString);

                    int numeroSns = resultado.getInt("Numero_Sns");

                    RegistoClinico registoClinico = new RegistoClinico(numeroSns);
                    registoClinico.setHistoricoDoencas(historicoDoencas);
                    registoClinico.setAlergias(alergias);
                    registoClinico.setOperacoes(operacoes);
                    registoClinico.setEntradasRegistosClinicos(obterEntradasRC(registoClinico));
                    registos.add(registoClinico);
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter registos: " + e.getMessage());
            }
        }
        return registos; // Retorna a lista de Registos Clínicos
    }

    public static List<EntradaRegistoClinico> obterEntradasRC(RegistoClinico registoClinico){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();
        List<EntradaRegistoClinico> entradas = new ArrayList<>();

        if (conexao != null){
            try{
                String sql = "{CALL ObterEntradasDeRC()}";
                CallableStatement statement = conexao.prepareCall(sql);

                ResultSet resultado = statement.executeQuery();

                while(resultado.next()){
                    if (resultado.getInt("Numero_Sns") == registoClinico.getNumeroSns()){
                        int idMedico = resultado.getInt("Id_Medico");
                        int idConsulta = resultado.getInt("Id_Consulta");
                        String data = resultado.getString("Data");
                        String assunto = resultado.getString("Assunto");
                        String tratamento = resultado.getString("Tratamento");

                        EntradaRegistoClinico entrada = registoClinico.new EntradaRegistoClinico(idMedico, idConsulta, data, assunto, tratamento);
                        entradas.add(entrada);
                    }
                }
            }catch(SQLException e){
                System.out.println("Erro ao obter entradas: " + e.getMessage());
            }
        }
        return entradas;
    }

    public static void criarNovaEntrada(EntradaRegistoClinico entrada){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();

        if (conexao != null){
            try{
                String sql = "{CALL CriarEntradaRegistoClinico(?, ?, ?, ?, ?)}";
                CallableStatement statement = conexao.prepareCall(sql);

                statement.setInt(1, entrada.getIdMedico());
                statement.setInt(2, entrada.getIdConsulta());
                statement.setString(3, entrada.getData());
                statement.setString(4, entrada.getAssunto());
                statement.setString(5, entrada.getTratamento());

                statement.execute();
            }catch(SQLException e){
                System.out.println("Erro ao criar nova entrada: " + e.getMessage());
            }
        }
    }

    public static void alterarRC(RegistoClinico registoClinico){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();

        if (conexao != null){
            try{
                String sql = "{CALL AlterarRegistoClinico(?, ?, ?, ?)}";
                CallableStatement statement = conexao.prepareCall(sql);

                statement.setInt(1, registoClinico.getNumeroSns());
                statement.setString(2, registoClinico.listToString(registoClinico.getHistoricoDoencas()));
                statement.setString(3, registoClinico.listToString(registoClinico.getAlergias()));
                statement.setString(4, registoClinico.listToString(registoClinico.getOperacoes()));

                statement.execute();
            }catch(SQLException e){
                System.out.println("Erro ao alterar registo clinico: " + e.getMessage());
            }
        }
    }
}
