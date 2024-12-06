package sql.server;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class CifrarPasswords {
    private static final String password = "12345678";

    public static String cifrar(String texto) throws Exception {
        SecretKeySpec passwordkey= new SecretKeySpec(password.getBytes(), "DES");
        Cipher cifra = Cipher.getInstance("DES");
        cifra.init(Cipher.ENCRYPT_MODE, passwordkey);
        byte[] textoCifrado = cifra.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(textoCifrado); // Converte o texto cifrado para Base64
    }

    public static String decifrar(String textoCifrado) throws Exception {
        SecretKeySpec passwordkey= new SecretKeySpec(password.getBytes(), "DES");
        Cipher cifra = Cipher.getInstance("DES");
        cifra.init(Cipher.DECRYPT_MODE, passwordkey);
        byte[] textoDecifrado = cifra.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String(textoDecifrado);
    }

    public static class AtualizarSenhas {
        public static void main(String[] args) {
            // Obtém a conexão com o banco de dados
            Connection conexao = SqlGeral.DatabaseConnection.getInstance();

            if (conexao != null) {
                try {
                    // 1. Seleciona todos os registros com senhas atuais
                    String sqlSelect = "SELECT ID, Password FROM Utilizador";
                    PreparedStatement selectStmt = conexao.prepareStatement(sqlSelect);
                    ResultSet resultado = selectStmt.executeQuery();

                    // 2. Prepara a query de atualização das senhas
                    String sqlUpdate = "UPDATE Utilizador SET Password = ? WHERE ID = ?";
                    PreparedStatement updateStmt = conexao.prepareStatement(sqlUpdate);

                    // 3. Itera pelos resultados e atualiza cada senha
                    while (resultado.next()) {
                        int id = resultado.getInt("ID");
                        String senhaAtual = resultado.getString("Password");

                        // Cifra a senha atual
                        String senhaCifrada = CifrarPasswords.cifrar(senhaAtual);

                        // Atualiza a senha cifrada no banco de dados
                        updateStmt.setString(1, senhaCifrada);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                        System.out.println("Senha atualizada para o ID: " + id);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao atualizar senhas: " + e.getMessage());
                }
            } else {
                System.out.println("Erro de conexão com o banco de dados.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            String senhaOriginal = "Sampas13"; // Senha original (antes da cifragem)

            // Cifrar a senha
            String senhaCifrada = CifrarPasswords.cifrar(senhaOriginal);
            System.out.println("Senha Cifrada: " + senhaCifrada); // Mostra a senha cifrada

            // Decifrar a senha
            String senhaDecifrada = CifrarPasswords.decifrar(senhaCifrada);
            System.out.println("Senha Decifrada: " + senhaDecifrada); // Mostra a senha decifrada

            // Verifica se a senha original e a senha decifrada são iguais
            System.out.println("A senha original é igual à senha decifrada? " + senhaOriginal.equals(senhaDecifrada));
        } catch (Exception e) {
            System.out.println("Erro ao cifrar ou decifrar: " + e.getMessage());
        }
    }
}
