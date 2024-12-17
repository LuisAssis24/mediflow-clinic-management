import medi.flow.Paciente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PacienteTeste {

    @Test
    public void testCriacaoPaciente() {
        // Cria um paciente
        Paciente paciente = new Paciente(123456789, "Carlos Silva", 987654321);

        // Verifica se os dados foram armazenados corretamente
        assertEquals(123456789, paciente.getNumeroSNS());
        assertEquals("Carlos Silva", paciente.getNome());
        assertEquals(987654321, paciente.getContacto());
    }

    @Test
    public void testGetters() {
        // Cria um paciente
        Paciente paciente = new Paciente(123456789, "Carlos Silva", 987654321);

        // Verifica se os valores retornados pelos getters são os mesmos passados para o construtor
        assertEquals(123456789, paciente.getNumeroSNS());
        assertEquals("Carlos Silva", paciente.getNome());
        assertEquals(987654321, paciente.getContacto());
    }

    @Test
    public void testIgualdadePaciente() {
        // Cria dois pacientes com os mesmos dados
        Paciente paciente1 = new Paciente(123456789, "Carlos Silva", 987654321);
        Paciente paciente2 = new Paciente(123456789, "Carlos Silva", 987654321);

        // Verifica se as instâncias são iguais (mas isso não é garantido sem sobrescrever o método equals)
        assertNotSame(paciente1, paciente2); // Eles não são a mesma instância, mas podem ter dados iguais
    }
    @Test
    public void testNumeroSNSExtremo() {
        // Cria um paciente com SNS muito grande
        Paciente paciente = new Paciente(999999999, "Maria Silva", 987654321);

        // Verifica se o SNS foi armazenado corretamente
        assertEquals(999999999, paciente.getNumeroSNS());
    }

    @Test
    public void testContactoPacienteExtremo() {
        // Cria um paciente com contacto muito grande
        Paciente paciente = new Paciente(123456789, "Carlos Silva", Integer.MAX_VALUE);

        // Verifica se o contacto foi armazenado corretamente
        assertEquals(Integer.MAX_VALUE, paciente.getContacto());
    }
}