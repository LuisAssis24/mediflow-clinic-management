import org.junit.jupiter.api.Test;
import sql.server.CifrarPasswords;

import static org.junit.jupiter.api.Assertions.*;

public class CifrarPasswordsTeste {

    // Testes de Entrada e Saída
    @Test
    public void testCifrar() {
        try {
            String passwordOriginal = "Sampas13";
            String passwordCifrada = CifrarPasswords.cifrar(passwordOriginal);
            System.out.println("Password Cifrada: " + passwordCifrada);

            // Since there is no decifrar method, we cannot compare with the original password
            // Instead, we can check if the cifrar method returns a non-null and non-empty string
            assertNotNull(passwordCifrada, "A senha cifrada não deve ser nula");
            assertFalse(passwordCifrada.isEmpty(), "A senha cifrada não deve ser vazia");
        } catch (Exception e) {
            fail("Erro ao cifrar: " + e.getMessage());
        }
    }

    // Testes Lógicos
    @Test
    public void testCifrarSempreAMesmaSenha() {
        try {
            // Senha original
            String passwordOriginal = "Sampas13";

            // Cifra a senha
            String passwordCifrada1 = CifrarPasswords.cifrar(passwordOriginal);
            String passwordCifrada2 = CifrarPasswords.cifrar(passwordOriginal);

            // Verifica se as senhas cifradas são sempre iguais
            assertEquals(passwordCifrada1, passwordCifrada2, "A senha cifrada deve ser sempre a mesma para a mesma senha original");
        } catch (Exception e) {
            fail("Erro ao cifrar: " + e.getMessage());
        }
    }

    @Test
    public void testCifrarSenhasDiferentes() {
        try {
            // Senhas diferentes
            String password1 = "Sampas13";
            String password2 = "OutroPass14";

            // Cifra as senhas
            String passwordCifrada1 = CifrarPasswords.cifrar(password1);
            String passwordCifrada2 = CifrarPasswords.cifrar(password2);

            // Verifica se as senhas cifradas são diferentes
            assertNotEquals(passwordCifrada1, passwordCifrada2, "Senhas diferentes devem produzir saídas cifradas diferentes");
        } catch (Exception e) {
            fail("Erro ao cifrar senhas diferentes: " + e.getMessage());
        }
    }
}

