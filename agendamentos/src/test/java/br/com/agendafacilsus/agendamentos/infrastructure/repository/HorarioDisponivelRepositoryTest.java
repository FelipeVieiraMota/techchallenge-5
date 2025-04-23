package br.com.agendafacilsus.agendamentos.infrastructure.repository;

import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HorarioDisponivelRepositoryTest {

    @Autowired
    private HorarioDisponivelRepository horarioDisponivelRepository;

    private HorarioDisponivel horario;

    @BeforeEach
    public void setUp() {
        horario = new HorarioDisponivel();
        horario.setMedicoId("medico123");
        horario.setData(LocalDate.of(2025, 4, 13));
        horario.setHora(LocalTime.of(9, 0));
        horario.setReservado(false);

        horarioDisponivelRepository.save(horario);
    }

    @Test
    public void testFindByMedicoIdAndDataAndReservadoFalse() {
        List<HorarioDisponivel> horarios = horarioDisponivelRepository.findByMedicoIdAndDataAndReservadoFalse(
                "medico123", LocalDate.of(2025, 4, 13)
        );

        assertFalse(horarios.isEmpty(), "Deve retornar ao menos um horário disponível");
        assertEquals(1, horarios.size(), "Deve retornar exatamente um horário disponível");
        assertFalse(horarios.get(0).isReservado(), "O horário deve estar disponível (reservado = false)");
    }

    @Test
    public void testFindByMedicoIdAndDataAndHora() {
        Optional<HorarioDisponivel> horarioOptional = horarioDisponivelRepository.findByMedicoIdAndDataAndHora(
                "medico123", LocalDate.of(2025, 4, 13), LocalTime.of(9, 0)
        );

        assertTrue(horarioOptional.isPresent(), "O horário deve ser encontrado");
        assertEquals(LocalTime.of(9, 0), horarioOptional.get().getHora(), "A hora do horário deve ser 09:00");
    }

    @Test
    public void testFindByMedicoIdAndDataAndHoraNotFound() {
        Optional<HorarioDisponivel> horarioOptional = horarioDisponivelRepository.findByMedicoIdAndDataAndHora(
                "medico123", LocalDate.of(2025, 4, 13), LocalTime.of(10, 0));

        assertFalse(horarioOptional.isPresent(), "O horário não deve ser encontrado");
    }

    @Test
    public void testExistsByMedicoIdAndDataAndHora() {
        boolean exists = horarioDisponivelRepository.existsByMedicoIdAndDataAndHora(
                "medico123", LocalDate.of(2025, 4, 13), LocalTime.of(9, 0)
        );

        assertTrue(exists, "Deve existir um horário para o médico, data e hora fornecidos");
    }

    @Test
    public void testExistsByMedicoIdAndDataAndHoraNotFound() {
        boolean exists = horarioDisponivelRepository.existsByMedicoIdAndDataAndHora(
                "medico123", LocalDate.of(2025, 4, 13), LocalTime.of(10, 0)
        );

        assertFalse(exists, "Não deve existir um horário para o médico, data e hora fornecidos");
    }

    @Test
    public void testSaveHorario() {
        HorarioDisponivel newHorario = new HorarioDisponivel();
        newHorario.setMedicoId("medico124");
        newHorario.setData(LocalDate.of(2025, 4, 14));
        newHorario.setHora(LocalTime.of(9, 0));
        newHorario.setReservado(false);

        HorarioDisponivel savedHorario = horarioDisponivelRepository.save(newHorario);

        assertNotNull(savedHorario.getId(), "O ID do horário salvo não deve ser nulo");
        assertEquals("medico124", savedHorario.getMedicoId(), "O ID do médico deve ser 'medico124'");
    }

    @Test
    public void testDeleteHorario() {
        horarioDisponivelRepository.delete(horario);

        Optional<HorarioDisponivel> deletedHorario = horarioDisponivelRepository.findByMedicoIdAndDataAndHora(
                "medico123", LocalDate.of(2025, 4, 13), LocalTime.of(9, 0));

        assertFalse(deletedHorario.isPresent(), "O horário deletado não deve existir mais");
    }
}
