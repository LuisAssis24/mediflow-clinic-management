package inter.face;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.io.FileReader;
import java.sql.*;

public class ConfiguracaoBD extends JFrame {
    public ConfiguracaoBD() {
        setTitle("Configurar Base de Dados");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2));

        JLabel hostLabel = new JLabel("Host:");
        JTextField hostField = new JTextField("localhost");

        JLabel portaLabel = new JLabel("Porta:");
        JTextField portaField = new JTextField("3306");

        JLabel userLabel = new JLabel("Utilizador:");
        JTextField userField = new JTextField("root");

        JLabel passLabel = new JLabel("Palavra-passe:");
        JPasswordField passField = new JPasswordField();

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            Properties props = new Properties();
            props.setProperty("db.host", hostField.getText());
            props.setProperty("db.port", portaField.getText());
            props.setProperty("db.user", userField.getText());
            props.setProperty("db.password", new String(passField.getPassword()));

            try (FileWriter writer = new FileWriter("config.properties")) {
                props.store(writer, "Configuração da Base de Dados");
                JOptionPane.showMessageDialog(this, "Configuração salva com sucesso!");
                dispose(); // Fecha a janela
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(hostLabel);
        add(hostField);
        add(portaLabel);
        add(portaField);
        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(saveButton);
    }

    public class ExecutarScriptSQL {
        public static void executar() {
            try (FileReader reader = new FileReader("config.properties")) {
                Properties props = new Properties();
                props.load(reader);

                String url = "jdbc:mysql://" + props.getProperty("db.host") + ":" +
                        props.getProperty("db.port") + "/?allowMultiQueries=true";
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();

                String scriptSQL = "CREATE DATABASE IF NOT EXISTS MediFlow;\n" +
                        "USE MediFlow;\n" +
                        "CREATE TABLE exemplo (id INT PRIMARY KEY, nome VARCHAR(50));";
                stmt.execute(scriptSQL);

                System.out.println("Script SQL executado com sucesso!");
            } catch (IOException | SQLException e) {
                System.err.println("Erro ao executar o script SQL: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            executar();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfiguracaoBD().setVisible(true));
    }
}

