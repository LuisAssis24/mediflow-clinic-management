package testes.unit;

import medi.flow.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicoTeste {

    private Medico medico;

    @BeforeEach
    public void setup() {
        // Cria o médico para os testes
        medico = new Medico(1, 123456789, "Dr. João", "senha123", "Médico", 1001, "Cardiologista");
    }

    // Testa a criação de um Medico
    @Test
    public void testCriacaoMedico() {
        assertNotNull(medico, "O médico não deve ser nulo.");
        assertEquals("Dr. João", medico.getNome(), "O nome do médico não corresponde.");
        assertEquals(1001, medico.getNumOrdem(), "O número de ordem do médico está incorreto.");
        assertEquals("Cardiologista", medico.getEspecialidade(), "A especialidade do médico está incorreta.");
    }

    // Testa a especialidade do médico
    @Test
    public void testEspecialidadeMedico() {
        assertEquals("Cardiologista", medico.getEspecialidade(), "Especialidade do médico não foi atribuída corretamente.");
    }

    // Testa o número da ordem do médico
    @Test
    public void testNumOrdemMedico() {
        assertEquals(1001, medico.getNumOrdem(), "Número da ordem do médico não está correto.");
    }

    // Testa a criação de horários para o médico
    @Test
    public void testHorarioMedico() {
        // Criar um horário para o médico
        List<String[]> horarios = new ArrayList<>();
        horarios.add(new String[] {"12/12/2024", "10:00"});
        horarios.add(new String[] {"12/12/2024", "14:00"});

        // Criar o objeto HorarioMedico e associar ao médico
        Medico.HorarioMedico horarioMedico = new Medico.HorarioMedico(medico.getId(), horarios);

        // Verificar se o horário foi adicionado corretamente
        assertNotNull(horarioMedico.getHorarios(), "Horários do médico não podem ser nulos.");
        assertEquals(2, horarioMedico.getHorarios().size(), "O número de horários está incorreto.");
        assertArrayEquals(new String[] {"12/12/2024", "10:00"}, horarioMedico.getHorarios().get(0), "O primeiro horário está incorreto.");
    }

    // Testa a criação de múltiplos horários médicos
    @Test
    public void testVariosHorariosMedico() {
        // Criar diferentes horários para o médico
        List<String[]> horarios = new ArrayList<>();
        horarios.add(new String[] {"13/12/2024", "09:00"});
        horarios.add(new String[] {"13/12/2024", "15:00"});

        // Criar o objeto HorarioMedico e associar ao médico
        Medico.HorarioMedico horarioMedico = new Medico.HorarioMedico(medico.getId(), horarios);

        // Verificar se os horários foram corretamente criados
        assertEquals(2, horarioMedico.getHorarios().size(), "O número de horários está incorreto.");
        assertEquals("13/12/2024", horarioMedico.getHorarios().get(0)[0], "A data do primeiro horário está incorreta.");
        assertEquals("09:00", horarioMedico.getHorarios().get(0)[1], "A hora do primeiro horário está incorreta.");
    }
}
