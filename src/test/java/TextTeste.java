import medi.flow.Text;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextTeste {

    // Teste de Divisão com String Vazia
    @Test
    public void testSplitStringToListComStringVazia() {
        // Prepara os dados
        String texto = "";

        // Chama o método splitStringToList
        List<String> resultado = Text.splitStringToList(texto);

        // Valida os resultados
        assertEquals(1, resultado.size(), "A lista deve conter 1 elemento vazio");
        assertEquals("", resultado.get(0), "O único elemento deve ser uma string vazia");
    }

    // Teste de Divisão com String Nula
    @Test
    public void testSplitStringToListComStringNula() {
        // Prepara os dados
        String texto = null;

        // Chama o método splitStringToList e trata a exceção
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Text.splitStringToList(texto);
        });

        // Valida a exceção
        assertEquals("Cannot invoke \"String.split(String)\" because \"string\" is null",
                exception.getMessage());
    }
}
