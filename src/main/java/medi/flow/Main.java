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
    }

    public static void main(String[] args) {
        // Cria a conexão com a base de dados assim que o programa inicia
        Connection connection = SqlGeral.DatabaseConnection.getInstance();

        // Verifica se a conexão foi bem-sucedida
        if (connection != null) {
            System.out.println("Conexão inicializada.");
        } else {
            System.out.println("Falha ao conectar a base de dados.");
        }



        // Inicia a interface gráfica
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaDeLogin().setVisible(true); // Mostra a janela de login
            }
        });

    }
}