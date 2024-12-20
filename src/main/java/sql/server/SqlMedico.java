package sql.server;

import medi.flow.*;
import medi.flow.RegistoClinico.*;
import java.sql.*;
import java.util.*;

import static medi.flow.Text.*;

// Classe para executar consultas SQL relacionadas com a gestão de utilizadores
public class SqlMedico {

    // Obtem uma lista com todos os IDs dos utilizadores
    public static List<RegistoClinico> obterTodosRegistos() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<RegistoClinico> registos = new ArrayList<>(); // Lista para armazenar os registros clinicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {// Tenta executar a consulta SQL
                String sql = "{CALL ObterRegistosClinicos()}";// Declara a query para obter todos os registros clinicos
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a query

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todas as consultas à lista
                while (resultado.next()) {
                    //Pega da base de dados o elemento como string e depois cria uma List com elementos da String separados a cada ponto
                    //Confirma se a string é nula e se for, cria uma lista vazia
                    String historicoDoencasString = resultado.getString("Historico_Doencas");// Obtém o historico de doenças
                    if (historicoDoencasString == null) {// Verifica se a consulta retornou algum resultado
                        historicoDoencasString = "";// Obtém o historico de doenças
                    }
                    List<String> historicoDoencas = splitStringToList(historicoDoencasString);// Obtém o historico de doenças


                    String alergiasString = resultado.getString("Alergias");// Obtém as alergias
                    if (alergiasString == null) {// Verifica se a consulta retornou algum resultado
                        alergiasString = "";// Obtém as alergias
                    }
                    List<String> alergias = splitStringToList(alergiasString);// Obtém as alergias


                    String operacoesString = resultado.getString("Operacoes");// Obtém as operações
                    if (operacoesString == null) {// Verifica se a consulta retornou algum resultado
                        operacoesString = "";// Obtém as operações
                    }
                    List<String> operacoes = splitStringToList(operacoesString);// Obtém as operações

                    int numeroSns = resultado.getInt("Numero_Sns");// Obtém o número de sns

                    RegistoClinico registoClinico = new RegistoClinico(numeroSns);// Cria um objeto RegistoClinico
                    registoClinico.setHistoricoDoencas(historicoDoencas);// Adiciona o historico de doenças ao registo clinico
                    registoClinico.setAlergias(alergias);// Adiciona as alergias ao registo clinico
                    registoClinico.setOperacoes(operacoes);// Adiciona as operações ao registo clinico
                    registoClinico.setEntradasRegistosClinicos(obterEntradasRC(registoClinico));// Adiciona as entradas ao registo clinico
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
            try{// Tenta executar a consulta SQL
                String sql = "{CALL ObterEntradasDeRC()}";// Declara a query para obter todas as entradas
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a query

                ResultSet resultado = statement.executeQuery();// Executa a consulta e armazena o resultado

                while(resultado.next()){// Enquanto houver entradas
                    if (resultado.getInt("Numero_Sns") == registoClinico.getNumeroSns()){// Verifica se a entrada pertence ao registo clinico
                        int idMedico = resultado.getInt("Id_Medico");// Obtém o ID do médico
                        String data = resultado.getString("Data");// Obtém a data da entrada
                        String assuntoStr = resultado.getString("Assunto");// Obtém o assunto da entrada
                        String tratamentoStr = resultado.getString("Tratamento");// Obtém o tratamento da entrada

                        List<String> assunto = splitStringToList(assuntoStr); // Cria uma lista com os assuntos
                        List<String> tratamento = splitStringToList(tratamentoStr);// Cria uma lista com os tratamentos

                        EntradaRegistoClinico entrada = registoClinico.new EntradaRegistoClinico(idMedico, data, assunto, tratamento);// Cria um objeto EntradaRegistoClinico
                        entradas.add(entrada);// Adiciona a entrada à lista de entradas
                    }
                }
            }catch(SQLException e){// Trata erros relacionados ao SQL
                System.out.println("Erro ao obter entradas: " + e.getMessage());// Mensagem de erro
            }
        }
        return entradas;// Retorna a lista de entradas
    }

    // Cria um utilizador e, se for medico, adiciona os detalhes na tabela medico
    public static void criarNovaEntrada(EntradaRegistoClinico entrada){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados

        if (conexao != null){// Verifica se a conexão foi estabelecida com sucesso
            try{// Tenta executar a consulta SQL
                String sql = "{CALL CriarEntradaRegistoClinico(?, ?, ?, ?, ?)}";// Declara a query para criar uma nova entrada
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a query

                statement.setInt(1, entrada.getNumeroSns());// Substitui o placeholder (?) pelo número de sns
                statement.setInt(2, entrada.getIdMedico());// Substitui o placeholder (?) pelo ID do médico
                statement.setString(3, entrada.getData());// Substitui o placeholder (?) pela data da entrada

                String assuntoStr = listToString(entrada.getAssunto());// Cria uma string com os assuntos
                String tratamentoStr = listToString(entrada.getTratamentos());// Cria uma string com os tratamentos

                statement.setString(4, assuntoStr); // Substitui o placeholder (?) pelo assunto da entrada
                statement.setString(5, tratamentoStr); // Substitui o placeholder (?) pelo tratamento da entrada

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
            try{// Tenta executar a consulta SQL
                String sql = "{CALL AlterarRegistoClinico(?, ?, ?, ?)}";// Declara a query para alterar um registo clinico
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a query

                statement.setInt(1, registoClinico.getNumeroSns());// Substitui o placeholder (?) pelo número de sns
                statement.setString(2, registoClinico.listToString(registoClinico.getHistoricoDoencas()));// Substitui o placeholder (?) pelo historico de doenças
                statement.setString(3, registoClinico.listToString(registoClinico.getAlergias()));// Substitui o placeholder (?) pelas alergias
                statement.setString(4, registoClinico.listToString(registoClinico.getOperacoes()));// Substitui o placeholder (?) pelas operações

                statement.execute();// Executa a consulta
            }catch(SQLException e){// Trata erros relacionados ao SQL
                System.out.println("Erro ao alterar registo clinico: " + e.getMessage());// Mensagem de erro
            }
        }
    }
}
