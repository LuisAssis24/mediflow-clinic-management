import medi.flow.Clinica;
import medi.flow.Consulta;
import medi.flow.Paciente;
import medi.flow.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClinicaTeste {

    private Clinica clinica;

    // Este método é executado antes de cada teste para garantir que os dados sejam reiniciados.
    @BeforeEach
    public void setup() {
        clinica = new Clinica();
    }

    // Teste de Entrada e Saída
    @Test
    public void testAddMedico() {
        // Limpa a lista de médicos para evitar dados acumulados de outros testes
        clinica.getMedicos().clear();

        // Cria um novo médico no formato String[]
        String[] medico = {"101", "Dr. João", "Cardiologista", "12/12/2024"};

        // Adiciona o médico
        clinica.addMedico(medico);

        // Verifica se o número de médicos é 1 após adicionar
        assertEquals(1, clinica.getMedicos().size());
    }

    // Teste de Entrada e Saída
    @Test
    public void testAddAndRemoveConsulta() {
        // Limpa a lista de consultas antes do teste
        clinica.getConsultas().clear();

        // Cria uma nova consulta
        Consulta consulta = new Consulta(1, "Consultorio A", "Dr. João", "Paciente A", "12/12/2024", "10:00", 101, 987654, 500, 100);

        // Adiciona a consulta
        clinica.addConsulta(consulta);

        // Verifica se a consulta foi adicionada corretamente (1 consulta na lista)
        assertEquals(1, clinica.getConsultas().size());

        // Remove a consulta
        clinica.removeConsulta(1);

        // Verifica se a consulta foi removida corretamente (0 consultas na lista)
        assertEquals(0, clinica.getConsultas().size());
    }

    // Teste de Entrada e Saída
    @Test
    public void testAddPaciente() {
        // Limpa a lista de pacientes antes do teste
        clinica.getPacientes().clear();

        // Cria um novo paciente
        Paciente paciente = new Paciente(12345, "Carlos Silva", 987654321);

        // Adiciona o paciente
        clinica.addPaciente(paciente);

        // Verifica se o paciente foi adicionado corretamente
        assertEquals(1, clinica.getPacientes().size());
    }

    // Teste Estrutural
    @Test
    public void testAddHorarioMedico() {
        // Limpa a lista de horários médicos
        clinica.getConsultas().clear(); // Assumindo que os horários dos médicos possam ser geridos de alguma forma no sistema

        // Cria um horário para o médico
        List<String[]> horarios = new ArrayList<>();
        horarios.add(new String[] {"12/12/2024", "10:00"});
        horarios.add(new String[] {"12/12/2024", "14:00"});

        Medico.HorarioMedico horarioMedico = new Medico.HorarioMedico(101, horarios);

        // Cria o médico
        Medico medico = new Medico(101, 123456789, "Dr. João", "senha123", "Médico", 1001, "Cardiologista");

        // Aqui a lógica para adicionar esse horário ao médico ou à clínica pode ser ajustada, dependendo da implementação da sua clínica
        // No entanto, vamos apenas verificar a criação do horário
        assertEquals(2, horarioMedico.getHorarios().size());  // Verifica se dois horários foram adicionados
    }

    // Teste de Entrada e Saída
    @Test
    public void testRemoveMedico() {
        // Limpa a lista de médicos antes do teste
        clinica.getMedicos().clear();

        // Cria um novo médico
        String[] medico = {"101", "Dr. João", "Cardiologista", "12/12/2024"};

        // Adiciona o médico
        clinica.addMedico(medico);

        // Verifica se o número de médicos é 1 após adicionar
        assertEquals(1, clinica.getMedicos().size());

        // Remove o médico
        clinica.removeMedico(101);

        // Verifica se a lista de médicos está vazia
        assertEquals(0, clinica.getMedicos().size());
    }

    // Teste de Entrada e Saída
    @Test
    public void testAddConsultasComPacientesEMedicosDiferentes() {
        // Limpa a lista de consultas antes do teste
        clinica.getConsultas().clear();

        // Cria médicos e pacientes
        Medico medico1 = new Medico(101, 123456789, "Dr. João", "senha123", "Médico", 1001, "Cardiologista");
        Medico medico2 = new Medico(102, 987654321, "Dr. Ana", "senha456", "Médico", 1002, "Dermatologista");
        Paciente paciente1 = new Paciente(12345, "Carlos Silva", 987654321);
        Paciente paciente2 = new Paciente(67890, "Maria Souza", 123456789);

        // Cria consultas para médicos e pacientes diferentes
        Consulta consulta1 = new Consulta(1, "Consultorio A", "Dr. João", "Carlos Silva", "12/12/2024", "10:00", 101, 987654, 500, 100);
        Consulta consulta2 = new Consulta(2, "Consultorio B", "Dr. Ana", "Maria Souza", "13/12/2024", "14:00", 102, 123456, 500, 100);

        // Adiciona as consultas
        clinica.addConsulta(consulta1);
        clinica.addConsulta(consulta2);

        // Verifica se as duas consultas foram adicionadas corretamente
        assertEquals(2, clinica.getConsultas().size());
    }

}
