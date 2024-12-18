package sql.server;

import medi.flow.*;
import medi.flow.RegistoClinico.*;
import java.sql.*;
import java.util.*;

import static medi.flow.Text.listToString;
import static medi.flow.Text.splitStringToList;

// Classe para executar consultas SQL relacionadas com a gestão de utilizadores
public class SqlMedico {
    // Obtem uma lista com todos os IDs dos utilizadores
    public static List<RegistoClinico> obterTodosRegistos() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<RegistoClinico> registos = new ArrayList<>(); // Lista para armazenar os registros clinicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {// Declara uma consulta SQL para obter todos os utilizadores
                String sql = "{CALL ObterRegistosClinicos()}";// Declara uma consulta SQL para obter todos os registros clinicos
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as consultas à lista
                while (resultado.next()) {
                    //Pega da base de dados o elemento como string e depois cria uma List com elementos da String separados a cada ponto
                    //Confirma se a string é nula e se for, cria uma lista vazia
                    String historicoDoencasString = resultado.getString("Historico_Doencas");// Obtém o histórico de doenças do paciente
                    if (historicoDoencasString == null) {// Verifica se o histórico de doenças é nulo
                        historicoDoencasString = "";// Define o histórico de doenças como uma string vazia
                    }
                    List<String> historicoDoencas = splitStringToList(historicoDoencasString);// Converte a string em uma lista de doenças


                    String alergiasString = resultado.getString("Alergias");// Obtém as alergias do paciente
                    if (alergiasString == null) {// Verifica se as alergias são nulas
                        alergiasString = "";// Define as alergias como uma string vazia
                    }
                    List<String> alergias = splitStringToList(alergiasString);// Converte a string em uma lista de alergias


                    String operacoesString = resultado.getString("Operacoes");// Obtém as operações do paciente
                    if (operacoesString == null) {// Verifica se as operações são nulas
                        operacoesString = "";// Define as operações como uma string vazia
                    }
                    List<String> operacoes = splitStringToList(operacoesString);// Converte a string em uma lista de operações

                    int numeroSns = resultado.getInt("Numero_Sns");// Obtém o número de SNS do paciente

                    RegistoClinico registoClinico = new RegistoClinico(numeroSns);// Cria um objeto RegistoClinico
                    registoClinico.setHistoricoDoencas(historicoDoencas);// Define o histórico de doenças do paciente
                    registoClinico.setAlergias(alergias);// Define as alergias do paciente
                    registoClinico.setOperacoes(operacoes);// Define as operações do paciente
                    registoClinico.setEntradasRegistosClinicos(obterEntradasRC(registoClinico));// Define as entradas do registo clinico
                    registos.add(registoClinico);// Adiciona o registo clinico à lista de registos clinicos
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter registos: " + e.getMessage());// Mensagem de erro
            }
        }
        return registos; // Retorna a lista de Registos Clínicos
    }

    // Obtem uma lista com todos os IDs dos utilizadores
    public static List<EntradaRegistoClinico> obterEntradasRC(RegistoClinico registoClinico){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados
        List<EntradaRegistoClinico> entradas = new ArrayList<>();// Lista para armazenar as entradas

        if (conexao != null){// Verifica se a conexão foi estabelecida com sucesso
            try{// Declara uma consulta SQL para obter todas as entradas
                String sql = "{CALL ObterEntradasDeRC()}";// Declara uma consulta SQL para obter todas as entradas
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta

                ResultSet resultado = statement.executeQuery();// Executa a consulta e armazena o resultado

                while(resultado.next()){// Adiciona todas as entradas à lista
                    if (resultado.getInt("Numero_Sns") == registoClinico.getNumeroSns()){// Verifica se o número de SNS é igual ao do registo clinico
                        int idMedico = resultado.getInt("Id_Medico");// Obtém o ID do médico
                        String data = resultado.getString("Data");// Obtém a data da entrada
                        String assuntoStr = resultado.getString("Assunto");// Obtém o assunto da entrada
                        String tratamentoStr = resultado.getString("Tratamento");// Obtém o tratamento da entrada

                        List<String> assunto = splitStringToList(assuntoStr);//Converte a string em uma lista de assuntos
                        List<String> tratamento = splitStringToList(tratamentoStr);// Converte a string em uma lista de tratamentos

                        EntradaRegistoClinico entrada = registoClinico.new EntradaRegistoClinico(idMedico, data, assunto, tratamento);// Cria um objeto EntradaRegistoClinico
                        entradas.add(entrada);// Adiciona a entrada à lista de entradas
                    }
                }
            }catch(SQLException e){// Trata erros relacionados ao SQL
                System.out.println("Erro ao obter entradas: " + e.getMessage());// Mensagem de erro
            }
        }
        return entradas;
    }

    // Cria um utilizador e, se for medico, adiciona os detalhes na tabela medico
    public static void criarNovaEntrada(EntradaRegistoClinico entrada){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados

        if (conexao != null){// Verifica se a conexão foi estabelecida com sucesso
            try{
                String sql = "{CALL CriarEntradaRegistoClinico(?, ?, ?, ?, ?)}";// Declara uma consulta SQL para criar uma nova entrada
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta

                statement.setInt(1, entrada.getIdMedico());// ID do médico
                statement.setString(3, entrada.getData());// Data da entrada

                String entradaStr = listToString(entrada.getAssunto());// Converte a lista de assuntos em uma string
                String tratamentoStr = listToString(entrada.getTratamentos());// Converte a lista de tratamentos em uma string

                statement.setString(4, entradaStr);// Assunto da entrada
                statement.setString(5, tratamentoStr);// Tratamento da entrada

                statement.execute();// Executa a consulta
            }catch(SQLException e){// Trata erros relacionados ao SQL
                System.out.println("Erro ao criar nova entrada: " + e.getMessage());// Mensagem de erro
            }
        }
    }

    // Cria um utilizador e, se for medico, adiciona os detalhes na tabela medico
    public static void alterarRC(RegistoClinico registoClinico){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados

        if (conexao != null){// Verifica se a conexão foi estabelecida com sucesso
            try{// Declara uma consulta SQL para alterar um registo clinico
                String sql = "{CALL AlterarRegistoClinico(?, ?, ?, ?)}";// Declara uma consulta SQL para alterar um registo clinico
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta

                statement.setInt(1, registoClinico.getNumeroSns());// Número de SNS do paciente
                statement.setString(2, registoClinico.listToString(registoClinico.getHistoricoDoencas()));// Histórico de doenças do paciente
                statement.setString(3, registoClinico.listToString(registoClinico.getAlergias()));// Alergias do paciente
                statement.setString(4, registoClinico.listToString(registoClinico.getOperacoes()));// Operações do paciente

                statement.execute();// Executa a consulta
            }catch(SQLException e){// Trata erros relacionados ao SQL
                System.out.println("Erro ao alterar registo clinico: " + e.getMessage());// Mensagem de erro
            }
        }
    }
}
