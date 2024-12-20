package medi.flow;

import inter.face.*;
import sql.server.*;
import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static Clinica clinica = new Clinica(); // Cria uma instância da classe Clinica

    public static Clinica getClinica() {
        return clinica;
    }//retorna a clinica

    // Método principal
    public static void main(String[] args) {
        // Cria a conexão com a base de dados assim que o programa inicia
        Connection connection = SqlGeral.DatabaseConnection.getInstance();

        // Verifica se a conexão foi bem-sucedida
        if (connection != null) {// Se a conexão foi bem-sucedida
            System.out.println("Conexão inicializada.");// Mostra uma mensagem de sucesso
        } else {// Se a conexão falhou
            System.out.println("Falha ao conectar a base de dados.");// Mostra uma mensagem de erro
        }



        // Inicia a interface gráfica
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {//cria uma nova thread
                new VistaDeLogin().setVisible(true); // Mostra a janela de login
            }
        });

    }
}