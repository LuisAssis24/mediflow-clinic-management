package sql.server;

import medi.flow.*;

import java.sql.*;
import java.text.*;
import java.util.*;

import static medi.flow.Main.getClinica;
import static medi.flow.Text.dataFormat;
import static medi.flow.Text.dataSqlParaJava;

// Classe para executar consultas SQL relacionadas com a secretaria
public class SqlSecretaria {
    // Metodo para carregar os medicos
    public static List<String[]> obterTodosMedicos() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<String[]> medicos = new ArrayList<>(); // Lista para armazenar os ‘IDs’ e Especialidades dos médicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todos os IDs dos médicos
                String sql = "{CALL ObterTodosMedicos()}";// Declara uma consulta SQL para obter todos os médicos
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os médicos à lista
                while (resultado.next()) {// Adiciona todos os médicos à lista
                    int idMedico = resultado.getInt("ID");// Obtém o ID do médico
                    String especialidade = resultado.getString("Especialidade");// Obtém a especialidade do médico

                    medicos.add(new String[]{String.valueOf(idMedico), especialidade});// Adiciona o ID e a especialidade do médico à lista
                }
                return medicos; // Retorna a lista com os IDs dos médicos
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os médicos: " + e.getMessage());// Mensagem de erro
            }
        }
        return medicos;
    }

    // Metodo para carregar os pacientes
    public static List<Paciente> obterTodosPacientes() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<Paciente> pacientes = new ArrayList<>(); // Lista para armazenar os pacientes

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todos os pacientes
                String sql = "{CALL ObterTodosPacientes()}";
                CallableStatement statement = conexao.prepareCall(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os pacientes à lista
                while (resultado.next()) {// Adiciona todos os pacientes à lista
                    int numeroSNS = resultado.getInt("Numero_SNS");//   Obtém o número de SNS do paciente
                    String nome = resultado.getString("Nome");// Obtém o nome do paciente
                    int contacto = resultado.getInt("Contacto");// Obtém o contacto do paciente

                    pacientes.add(new Paciente(numeroSNS, nome, contacto)); // Adiciona o paciente à lista
                }
                return pacientes; // Retorna a lista com os pacientes
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os pacientes: " + e.getMessage());// Mensagem de erro
            }
        }
        return pacientes;
    }

    // Metodo para criar uma nova consulta medica
    public static int criarConsulta(String data, String hora, String motivo, String nomePaciente, int snsPaciente, int contacto, int numSala, int idMedico, String nomeMedico) {// Método para criar uma nova consulta
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados
        int idConsultaGerado = -1; // Variable to store the generated ID

        // Converte a data para o formato de SQL
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");// Formato de data de entrada
        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");// SQL formato de entrada
        String dataSql = "";// Data em formato SQL
        try {// Tenta converter a data
            dataSql = sqlDateFormat.format(inputDateFormat.parse(data));// Converte a data para o formato SQL
        } catch (ParseException e) {// Trata erros relacionados à formatação da data
            e.printStackTrace();// Mensagem de erro
        }

        // Adiciona :00 para ai fim da hora
        String horaSql = hora + ":00";

        // Chama o procedure
        String sql = "{CALL MarcarConsulta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Call the stored procedure MarcarConsulta

        try (CallableStatement callableStatement = conexao.prepareCall(sql)) {// Prepara a chamada do procedimento

            callableStatement.setDate(1, java.sql.Date.valueOf(dataSql)); // Data da consulta
            callableStatement.setTime(2, java.sql.Time.valueOf(horaSql)); // Hora da consulta
            callableStatement.setString(3, motivo); // Motivo da consulta
            callableStatement.setString(4, nomePaciente); //Nome do paciente
            callableStatement.setInt(5, snsPaciente); // Sns do Paciente
            callableStatement.setInt(6, contacto); //Contacto do Paciente
            callableStatement.setInt(7, numSala); // Numero da sala
            callableStatement.setInt(8, idMedico); // Id do medico
            callableStatement.setString(9, nomeMedico); // Nome do medico
            callableStatement.registerOutParameter(10, java.sql.Types.INTEGER); // Consultation ID (generated by the database)


            callableStatement.execute(); // Executa a stored procedure

            // Encontra o ID da consulta gerado
            idConsultaGerado = callableStatement.getInt(10); //Encontra o ID da consulta gerado
        } catch (SQLException e) {// Trata erros relacionados ao SQL
            e.printStackTrace();// Mensagem de erro
        }

        return idConsultaGerado; // Retorna o ID da consulta gerado
    }

    // Metodo para desmarcar uma consulta existente
    public static void desmarcarConsulta(int IDConsulta) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {// Tenta executar a consulta SQL
                // Declara uma consulta SQL para obter os dados da consulta
                String sql = "{CALL DesmarcarConsulta(?)}";// Declara uma consulta SQL para desmarcar a consulta
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta

                // Substitui o placeholder (?) pelo valor do ID da consulta
                statement.setInt(1, IDConsulta);

                // Executa a consulta e armazena o resultado
                statement.executeUpdate();// Executa a consulta
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao desmarcar a consulta: " + e.getMessage());// Mensagem de erro
            }
        }
    }

    // Metodo para criar um novo paciente caso eel nao exista
    public static boolean criarPaciente(int numero, String nome, int contacto) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados

        if (getClinica().obterPacientePorSns(numero) == null) {// Verifica se o paciente já existe
            String sql = "{CALL CriarPaciente(?, ?, ?)}";// Declara a query para criar um paciente
            try (CallableStatement callableStatement = conexao.prepareCall(sql)) {// Prepara a query
                callableStatement.setInt(1, numero);// Substitui o placeholder (?) pelo número de SNS
                callableStatement.setString(2, nome);// Substitui o placeholder (?) pelo nome
                callableStatement.setInt(3, contacto);// Substitui o placeholder (?) pelo contacto
                callableStatement.executeUpdate();// Executa a query
                System.out.println("Paciente criado com sucesso!");// Mensagem de sucesso
                return true;
            } catch (SQLException e) {// Trata erros relacionados ao SQL
                e.printStackTrace();// Mensagem de erro
                throw new RuntimeException("Erro ao criar paciente: " + e.getMessage());// Mensagem de erro
            }
        } else {//  Mensagem de erro se o paciente já existir
            return false;
        }
    }

    // Metodo para criar um novo registo clinico
    public static void criarNovoRC(int numeroSns){
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados

        if (conexao != null){// Verifica se a conexão foi estabelecida com sucesso
            try{// Tenta executar a consulta SQL
                String sql = "{CALL CriarNovoRegisto(?)}";// Declara uma consulta SQL para criar um novo registo
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta

                statement.setInt(1, numeroSns);// Substitui o placeholder (?) pelo número de SNS

                statement.execute();// Executa a consulta
            }catch(SQLException e){// Trata erros relacionados ao SQL
                System.out.println("Erro ao criar novo registo: " + e.getMessage());// Mensagem de erro
            }
        }
    }

    //Metodo para armazenar todos os horarios de todos os medicos
    public static List<Medico.HorarioMedico> todosHorariosMedicos() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance();// Obtém a conexão com a base de dados
        List<Medico.HorarioMedico> todosHorarios = new ArrayList<>();// Lista para armazenar os horários de todos os médicos

        if (conexao != null) {// Verifica se a conexão foi estabelecida com sucesso
            try {// Tenta executar a consulta SQL
                // Retrieve all doctor IDs
                String sql = "{CALL ObterTodosMedicos()}";// Declara uma consulta SQL para obter todos os médicos
                CallableStatement statement = conexao.prepareCall(sql);// Prepara a consulta
                ResultSet resultado = statement.executeQuery();// Executa a consulta e armazena o resultado

                // Adiciona todos os horários à lista
                while (resultado.next()) {// Adiciona todos os horários à lista
                    int idMedico = resultado.getInt("ID");// Obtém o ID do médico
                    Medico.HorarioMedico horarioMedico = new Medico.HorarioMedico(idMedico, horariosOcupadosMedico(idMedico));// Cria um objeto HorarioMedico
                    todosHorarios.add(horarioMedico);// Adiciona o objeto à lista
                }
            } catch (SQLException e) {// Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os horários de todos os médicos: " + e.getMessage());// Mensagem de erro
            }
        }
        return todosHorarios;
    }

    //metodo para obter os horarios de um médico especifico
    public static List<String[]> horariosOcupadosMedico(int id) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        List<String[]> horarios = new ArrayList<>(); // Lista para armazenar os horários ocupados dos médicos

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter os horários ocupados do médico
                String sql = "{CALL ObterHorariosMedico(?)}";
                CallableStatement statement = conexao.prepareCall(sql);

                // Substitui o placeholder (?) pelo valor do ID do médico
                statement.setInt(1, id);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os horários à lista
                while (resultado.next()) {
                    String data = resultado.getString("Data");// Obtém a data
                    String hora = resultado.getString("Hora");// Obtém a hora

                    String dataFormatada = dataFormat(dataSqlParaJava(data));// Formata a data
                    horarios.add(new String[]{dataFormatada, hora});// Adiciona a data e a hora à lista
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os horários ocupados do médico: " + e.getMessage());// Mensagem de erro
            } catch (ParseException e) {// Trata erros relacionados à formatação da data
                throw new RuntimeException(e);// Mensagem de erro
            }
        }
        return horarios; // Retorna a lista com os horários ocupados
    }
}