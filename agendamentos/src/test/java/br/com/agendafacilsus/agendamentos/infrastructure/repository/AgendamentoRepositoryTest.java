package br.com.agendafacilsus.agendamentos.infrastructure.repository;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AgendamentoRepositoryTest {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    private Agendamento agendamento;

    @BeforeEach
    public void setUp() {
        agendamento = Agendamento.builder()
                .uuidPaciente("paciente123")
                .nomePaciente("João")
                .uuidMedico("medico123")
                .nomeMedico("Dr. Silva")
                .descricaoEspecialidade("Cardiologia")
                .tipoEspecialidade(TipoEspecialidade.CONSULTA)
                .data(LocalDate.of(2025, 4, 13))
                .hora(LocalTime.of(8, 0))
                .status(StatusAgendamento.AGENDADO)
                .build();

        agendamentoRepository.save(agendamento);
    }

    @Test
    public void testExistsByUuidPacienteAndDataAndHoraAndStatusNot() {
        boolean exists = agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                "paciente123",
                LocalDate.of(2025, 4, 13),
                LocalTime.of(8, 0),
                StatusAgendamento.CANCELADO
        );

        assertTrue(exists, "Deve existir um agendamento com os dados fornecidos");
    }

    @Test
    public void testDoesNotExistByUuidPacienteAndDataAndHoraAndStatusNot() {
        boolean exists = agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                "paciente123",
                LocalDate.of(2025, 4, 13),
                LocalTime.of(9, 0),
                StatusAgendamento.CANCELADO
        );

        assertFalse(exists, "Não deve existir um agendamento com esses dados");
    }

    @Test
    public void testExistsWithDifferentStatus() {
        boolean exists = agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                "paciente123",
                LocalDate.of(2025, 4, 13),
                LocalTime.of(8, 0),
                StatusAgendamento.CANCELADO
        );

        assertTrue(exists, "Deve existir um agendamento com status AGENDADO");
    }

    @Test
    public void testNotExistsWithDifferentPatient() {
        boolean exists = agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                "paciente999",
                LocalDate.of(2025, 4, 13),
                LocalTime.of(8, 0),
                StatusAgendamento.CANCELADO
        );

        assertFalse(exists, "Não deve existir um agendamento para outro paciente");
    }

    @Test
    public void testNotExistsWithDifferentTime() {
        boolean exists = agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                "paciente123",
                LocalDate.of(2025, 4, 13),
                LocalTime.of(10, 0),
                StatusAgendamento.CANCELADO
        );

        assertFalse(exists, "Não deve existir um agendamento para esse horário");
    }
}
