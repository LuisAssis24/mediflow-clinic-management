import org.junit.jupiter.api.Test;
import sql.server.CifrarPasswords;

import static org.junit.jupiter.api.Assertions.*;

public class CifrarPasswordsTeste {

    @Test
    public void testCifrar() {
        try {
            // Senha original
            String passwordOriginal = "Sampas13";

            // Cifra a senha
            String passwordCifrada = CifrarPasswords.cifrar(passwordOriginal);
            System.out.println("Password Cifrada: " + passwordCifrada); // Mostra a senha cifrada


            // Verifica se a senha original e a senha decifrada são iguais
            assertEquals(passwordOriginal, "A senha original e a decifrada devem ser iguais");
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

}

