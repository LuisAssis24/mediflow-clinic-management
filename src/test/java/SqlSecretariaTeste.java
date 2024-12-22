import medi.flow.Medico;
import medi.flow.Paciente;
import sql.server.SqlSecretaria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SqlSecretariaTeste {

    // Teste de Obtenção de Pacientes
    @Test
    public void testObterTodosPacientes() {
        List<Paciente> pacientes = SqlSecretaria.obterTodosPacientes();

        assertNotNull(pacientes, "A lista de pacientes não pode ser nula.");
        assertTrue(pacientes.size() > 0, "A lista de pacientes não pode ser vazia.");

        // Verificar se cada paciente tem SNS, nome e contato válidos
        for (Paciente paciente : pacientes) {
            assertTrue(paciente.getNumeroSNS() > 0, "O número de SNS do paciente deve ser válido.");
            assertNotNull(paciente.getNome(), "O nome do paciente não pode ser nulo.");
            assertTrue(paciente.getContacto() > 0, "O contato do paciente deve ser válido.");
        }
    }

    // Teste de Criação de Paciente
    @Test
    public void testCriarPaciente() {
        int numeroSNS = 987654321;
        String nome = "Maria Santos";
        int contacto = 987654321;

        SqlSecretaria.criarPaciente(numeroSNS, nome, contacto);

        // Verificar se o paciente foi criado (pode envolver a verificação da existência do paciente no banco de dados)
        // Exemplo: Verificar se o paciente com o SNS existe
    }

    // Teste de Obtenção de Horários dos Médicos
    @Test
    public void testTodosHorariosMedicos() {
        List<Medico.HorarioMedico> horarios = SqlSecretaria.todosHorariosMedicos();

        assertNotNull(horarios, "A lista de horários dos médicos não pode ser nula.");
        assertTrue(horarios.size() > 0, "A lista de horários não pode ser vazia.");

        // Verificar se cada médico tem horários ocupados
        for (Medico.HorarioMedico horario : horarios) {
            assertNotNull(horario.getHorarios(), "Os horários do médico não podem ser nulos.");
        }
    }
}