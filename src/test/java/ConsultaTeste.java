import medi.flow.Clinica;
import medi.flow.Consulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaTeste {

    private Consulta consulta;
    private Clinica clinica;

    @BeforeEach
    public void setup() {
        clinica = new Clinica();  // Inicializa a clínica (que contém as consultas)
        consulta = new Consulta(1, "12/12/2024", "10:00", "Consulta de rotina", "Carlos Silva", "Dr. João", 987654, 1, 101, 123456789);
        clinica.addConsulta(consulta);  // Adiciona a consulta à clínica para o teste
    }

    // Teste de Entrada e Saída
    @Test
    public void testCriacaoConsulta() {
        // Verifica se a consulta foi criada corretamente
        assertNotNull(consulta, "A consulta não deve ser nula.");
        assertEquals(1, consulta.getIdConsulta(), "O ID da consulta está incorreto.");
        assertEquals("12/12/2024", consulta.getData(), "A data da consulta está incorreta.");
        assertEquals("10:00", consulta.getHora(), "A hora da consulta está incorreta.");
        assertEquals("Consulta de rotina", consulta.getMotivo(), "O motivo da consulta está incorreto.");
        assertEquals("Carlos Silva", consulta.getNomePaciente(), "O nome do paciente está incorreto.");
        assertEquals("Dr. João", consulta.getNomeMedico(), "O nome do médico está incorreto.");
        assertEquals(987654, consulta.getSnsPaciente(), "O SNS do paciente está incorreto.");
        assertEquals(1, consulta.getNumSala(), "O número da sala está incorreto.");  // Certificando que o número da sala é 1
        assertEquals(123456789, consulta.getContacto(), "O contacto está incorreto.");
    }

    // Teste de Entrada e Saída
    @Test
    public void testGettersConsulta() {
        assertEquals(1, consulta.getIdConsulta(), "ID da consulta incorreto.");
        assertEquals("12/12/2024", consulta.getData(), "Data incorreta.");
        assertEquals("10:00", consulta.getHora(), "Hora incorreta.");
        assertEquals("Consulta de rotina", consulta.getMotivo(), "Motivo incorreto.");
        assertEquals("Carlos Silva", consulta.getNomePaciente(), "Nome do paciente incorreto.");
        assertEquals("Dr. João", consulta.getNomeMedico(), "Nome do médico incorreto.");
        assertEquals(987654, consulta.getSnsPaciente(), "SNS incorreto.");
        assertEquals(1, consulta.getNumSala(), "Número da sala incorreto.");
        assertEquals(123456789, consulta.getContacto(), "Contacto incorreto.");
    }

    // Teste de Alteração de Dados
    @Test
    public void testAlterarMotivoConsulta() {
        // Alterando o motivo da consulta
        consulta = new Consulta(1, "12/12/2024", "10:00", "Consulta de emergência", "Carlos Silva", "Dr. João", 987654, 1, 101, 123456789);

        // Verifica se o motivo foi alterado corretamente
        assertEquals("Consulta de emergência", consulta.getMotivo(), "O motivo da consulta não foi alterado corretamente.");
    }

    // Teste de Alteração de Dados
    @Test
    public void testAlterarPacienteConsulta() {
        // Alterando o paciente
        consulta = new Consulta(1, "12/12/2024", "10:00", "Consulta de rotina", "Ana Silva", "Dr. João", 987654, 1, 101, 123456789);

        // Verifica se o nome do paciente foi alterado corretamente
        assertEquals("Ana Silva", consulta.getNomePaciente(), "O nome do paciente não foi alterado corretamente.");
    }

    // Teste de Alteração de Dados
    @Test
    public void testAlterarMedicoConsulta() {
        // Alterando o médico
        consulta = new Consulta(1, "12/12/2024", "10:00", "Consulta de rotina", "Carlos Silva", "Dr. Pedro", 987654, 1, 101, 123456789);

        // Verifica se o nome do médico foi alterado corretamente
        assertEquals("Dr. Pedro", consulta.getNomeMedico(), "O nome do médico não foi alterado corretamente.");
    }
}