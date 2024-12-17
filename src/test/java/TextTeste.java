
import medi.flow.Text;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextTeste {

    @Test
    public void testSplitStringToListComStringValida() {
        // Prepara os dados
        String texto = "primeiro.segundo.terceiro";

        // Chama o método splitStringToList
        List<String> resultado = Text.splitStringToList(texto);

        // Valida os resultados
        assertEquals(3, resultado.size(), "A lista deve conter 3 elementos");
        assertEquals("primeiro", resultado.get(0), "O primeiro elemento deve ser 'primeiro'");
        assertEquals("segundo", resultado.get(1), "O segundo elemento deve ser 'segundo'");
        assertEquals("terceiro", resultado.get(2), "O terceiro elemento deve ser 'terceiro'");
    }

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