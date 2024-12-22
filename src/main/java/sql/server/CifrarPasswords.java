package sql.server;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

/**
 *
 * @author Joáo Nunes
 * @author Pedro Sampaio
 */

// Classe para cifrar e decifrar passwords
public class CifrarPasswords {
    // Define a chave criptografia. A chave é uma string fixa de 8 caracteres para o algaritmo DES
    private static final String password = "12345678";

    // Método para cifrar um texto
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

    // Método principal para testar a cifragem e decifragem de passwords
    public static void main(String[] args) throws Exception {

    }
}
