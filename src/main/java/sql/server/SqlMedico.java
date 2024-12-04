package sql.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SqlMedico {
    public static int idMedicoAUtilizarOSistema = 0;

    public static ArrayList<Integer> obterTodasConsultasMedico() {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        ArrayList<Integer> consultas = new ArrayList<>(); // Lista para armazenar os IDs das consultas

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter todos os IDs das consultas
                String sql = "SELECT ID_Consulta FROM Consulta WHERE ID_Medico = " + idMedicoAUtilizarOSistema;
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Adiciona todos os IDs das consultas à lista
                while (resultado.next()) {
                    consultas.add(resultado.getInt("ID_Consulta"));
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter as consultas: " + e.getMessage());
            }
        }

        return consultas; // Retorna a lista com os IDs das consultas

    }

    public static HashMap<String, String> dadosConsultaMedico(int IDConsulta) {
        Connection conexao = SqlGeral.DatabaseConnection.getInstance(); // Obtém a conexão com a base de dados
        HashMap<String, String> dadosConsulta = new HashMap<>(); // Mapa para armazenar os dados da consulta

        if (conexao != null) { // Verifica se a conexão foi estabelecida com sucesso
            try {
                // Declara uma consulta SQL para obter os dados da consulta
                String sql = "SELECT * FROM Consulta WHERE ID_Consulta = ? AND ID_Medico = " + idMedicoAUtilizarOSistema;
                PreparedStatement statement = conexao.prepareStatement(sql);

                // Substitui o placeholder (?) pelo valor do ID da consulta
                statement.setInt(1, IDConsulta);

                // Executa a consulta e armazena o resultado
                ResultSet resultado = statement.executeQuery();

                // Verifica se encontrou um registo
                if (resultado.next()) {
                    // Adiciona os dados da consulta ao mapa
                    dadosConsulta.put("data", resultado.getString("Data"));
                    dadosConsulta.put("hora", resultado.getString("Hora"));
                    dadosConsulta.put("motivo", resultado.getString("Motivo"));
                    dadosConsulta.put("nomePaciente", resultado.getString("Nome_Paciente"));
                    dadosConsulta.put("snsPaciente", resultado.getString("Sns_Paciente"));
                    dadosConsulta.put("contacto", resultado.getString("Contacto"));
                    dadosConsulta.put("numSala", resultado.getString("Num_Sala"));
                    dadosConsulta.put("idMedico", resultado.getString("ID_Medico"));
                    dadosConsulta.put("idConsulta", resultado.getString("ID_Consulta"));
                }
            } catch (SQLException e) { // Trata erros relacionados ao SQL
                System.out.println("Erro ao obter os dados da consulta: " + e.getMessage());
            }
        }
        return dadosConsulta; // Retorna o mapa com os dados da consulta
    }
}
