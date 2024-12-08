package sql.server;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class CifrarPasswords {
    // Define a chave criptografia. A chave é uma string fixa de 8 caracteres para o algaritmo DES
    private static final String password = "12345678";

    public static String cifrar(String texto) throws Exception {
        // Criando a chave secreta a partir da password fixa
        SecretKeySpec passwordkey= new SecretKeySpec(password.getBytes(), "DES");
        // Inicializando o objeto Cipher para o algarismo DES
        Cipher cifra = Cipher.getInstance("DES");
        // Configura o Cipher no modo de difragem
        cifra.init(Cipher.ENCRYPT_MODE, passwordkey);
        // Cifra o texto fornecido e converte o resultado para Base64
        byte[] textoCifrado = cifra.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(textoCifrado); // Converte o texto cifrado para Base64
    }

    public static String decifrar(String textoCifrado) throws Exception {
        // Criando a chave secreta a partir da password fixa
        SecretKeySpec passwordkey= new SecretKeySpec(password.getBytes(), "DES");
        // Inicialiaza o objeto Cipher para o algarismo DES
        Cipher cifra = Cipher.getInstance("DES");
        // Configura o Cipher no modo de decifragem
        cifra.init(Cipher.DECRYPT_MODE, passwordkey);
        //Decifra o texto cifrado fornecido e converte o resultado para texto
        byte[] textoDecifrado = cifra.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String(textoDecifrado); // converte o texto decifrado para String
    }

    /*public static class AtualizarPassowrds {
        public static void main(String[] args) {
            // Obtém a conexão com o banco de dados
            Connection conexao = SqlGeral.DatabaseConnection.getInstance();

            if (conexao != null) {
                try {
                    // 1. Seleciona todos os registros com passowrds atuais
                    String sqlSelect = "SELECT ID, Password FROM Utilizador";
                    PreparedStatement selectStmt = conexao.prepareStatement(sqlSelect);
                    ResultSet resultado = selectStmt.executeQuery();

                    // 2. Prepara a query de atualização das passwords
                    String sqlUpdate = "UPDATE Utilizador SET Password = ? WHERE ID = ?";
                    PreparedStatement updateStmt = conexao.prepareStatement(sqlUpdate);

                    // 3. Itera pelos resultados e atualiza cada password
                    while (resultado.next()) {
                        int id = resultado.getInt("ID");
                        String passwordAtual = resultado.getString("Password");

                        // Cifra a password atual
                        String passwordCifrada = CifrarPasswords.cifrar(passwordAtual);

                        // Atualiza a password cifrada no banco de dados
                        updateStmt.setString(1, passwordCifrada);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                        System.out.println("Password atualizada para o ID: " + id);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao atualizar passwords: " + e.getMessage());
                }
            } else {
                System.out.println("Erro de conexão com o banco de dados.");
            }
        }
    }
*/
    public static void main(String[] args) {
        try {
            String passwordOriginal = "Sampas13"; // Password original (antes da cifragem)

            // Cifrar a password
            String passwordCifrada = CifrarPasswords.cifrar(passwordOriginal);
            System.out.println("Password Cifrada: " + passwordCifrada); // Mostra a password cifrada

            // Decifrar a password
            String passwordDecifrada = CifrarPasswords.decifrar(passwordCifrada);
            System.out.println("Password Decifrada: " + passwordDecifrada); // Mostra a password decifrada

            // Verifica se a password original e a password decifrada são iguais
            System.out.println("A password original é igual à password decifrada? " + passwordOriginal.equals(passwordDecifrada));
        } catch (Exception e) {
            System.out.println("Erro ao cifrar ou decifrar: " + e.getMessage());
        }
    }
}
