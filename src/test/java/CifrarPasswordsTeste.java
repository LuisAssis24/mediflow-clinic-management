import org.junit.jupiter.api.Test;
import sql.server.CifrarPasswords;

import static org.junit.jupiter.api.Assertions.*;

public class CifrarPasswordsTeste {

    @Test
    public void testCifrarEDecifrar() {
        try {
            // Senha original
            String passwordOriginal = "Sampas13";

            // Cifra a senha
            String passwordCifrada = CifrarPasswords.cifrar(passwordOriginal);
            System.out.println("Password Cifrada: " + passwordCifrada); // Mostra a senha cifrada

            // Decifra a senha
            String passwordDecifrada = CifrarPasswords.decifrar(passwordCifrada);
            System.out.println("Password Decifrada: " + passwordDecifrada); // Mostra a senha decifrada

            // Verifica se a senha original e a senha decifrada são iguais
            assertEquals(passwordOriginal, passwordDecifrada, "A senha original e a decifrada devem ser iguais");
        } catch (Exception e) {
            fail("Erro ao cifrar ou decifrar: " + e.getMessage());
        }
    }

    @Test
    public void testCifrarSempreADiversaSenha() {
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
    public void testDecifrarTextoInvalido() {
        try {
            // Texto inválido para a decifração
            String textoInvalido = "textoInvalido123";

            // Tenta decifrar um texto inválido
            assertThrows(Exception.class, () -> {
                CifrarPasswords.decifrar(textoInvalido);
            });
        } catch (Exception e) {
            // Se qualquer exceção for lançada, o teste será bem-sucedido
            System.out.println("Erro esperado ao tentar decifrar texto inválido: " + e.getMessage());
        }
    }
}
