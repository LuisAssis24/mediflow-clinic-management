import medi.flow.Text;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextTeste {

    @Test
    public void testQuebraPontos() {
        // Cria um JList para testar
        JList<String> jList = new JList<>();

        // Texto de entrada com pontos
        String texto = "Primeiro ponto. Segundo ponto. Terceiro ponto.";

        // Chama o método
        Text.quebraPontos(jList, texto);

        // Verifica o tamanho do modelo do JList
        assertEquals(3, jList.getModel().getSize());

        // Verifica o conteúdo do modelo
        assertEquals("Primeiro ponto", jList.getModel().getElementAt(0));
        assertEquals("Segundo ponto", jList.getModel().getElementAt(1));
        assertEquals("Terceiro ponto", jList.getModel().getElementAt(2));
    }
    @Test
    public void testDataJavaParaSql() throws ParseException {
        // Entrada de data e hora no formato "dd/MM/yyyy HH:mm"
        String data = "12/12/2024";
        String hora = "10:30";

        // Chama o método
        Date resultado = Text.dataJavaParaSql(data, hora);

        // Cria a data esperada para comparação
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date esperado = sdf.parse("12/12/2024 10:30");

        // Verifica se a data convertida é igual à esperada
        assertEquals(esperado, resultado);
    }
    @Test
    public void testDataSqlParaJava() throws ParseException {
        // Data no formato SQL "yyyy-MM-dd"
        String dataSql = "2024-12-12";

        // Chama o método
        String resultado = Text.dataSqlParaJava(dataSql);

        // A data esperada no formato "dd/MM/yyyy"
        String esperado = "12/12/2024";

        // Verifica se a data convertida é igual à esperada
        assertEquals(esperado, resultado);
    }
    @Test
    public void testDataFormat() throws ParseException {
        // Data no formato "dd/MM/yyyy"
        String data = "12/12/2024";

        // Chama o método
        String resultado = Text.dataFormat(data);

        // A data esperada no formato "dd/MM"
        String esperado = "12/12";

        // Verifica se a data convertida é igual à esperada
        assertEquals(esperado, resultado);
    }
    @Test
    public void testTimeFormat() throws ParseException {
        // Hora no formato "HH:mm:ss"
        String hora = "10:30:00";

        // Chama o método
        String resultado = Text.timeFormat(hora);

        // A hora esperada no formato "HHh - HHh"
        String esperado = "10h - 11h";

        // Verifica se a hora convertida é igual à esperada
        assertEquals(esperado, resultado);
    }
    @Test
    public void testNomeMedicoTransform() {
        // Nome do médico
        String nome = "João da Silva";

        // Chama o método
        String resultado = Text.nomeMedicoTransform(nome);

        // O nome esperado no formato "Dr. PrimeiroNome ÚltimoNome"
        String esperado = "Dr. João Silva";

        // Verifica se o nome formatado é o esperado
        assertEquals(esperado, resultado);
    }

    @Test
    public void testNomeMedicoTransformSemUltimoNome() {
        // Nome do médico sem sobrenome
        String nome = "João";

        // Chama o método
        String resultado = Text.nomeMedicoTransform(nome);

        // O nome esperado no formato "Dr. Nome"
        String esperado = "Dr. João";

        // Verifica se o nome formatado é o esperado
        assertEquals(esperado, resultado);
    }
}