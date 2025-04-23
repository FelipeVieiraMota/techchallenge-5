package br.com.agendafacilsus.agendamentos.infrastructure.mapper;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AgendamentoMapperTest {

    @Test
    public void testToEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        UsuarioResponseDTO paciente = new UsuarioResponseDTO("paciente123", "João", "joao@exemplo.com", UserRole.PACIENTE);
        UsuarioResponseDTO medico = new UsuarioResponseDTO("medico123", "Dr. Silva", "silva@exemplo.com", UserRole.MEDICO);
        EspecialidadeResponseDTO especialidade = new EspecialidadeResponseDTO(1L, "Cardiologia", TipoEspecialidade.CONSULTA);

        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(paciente.id(), especialidade.id(), medico.id(),
                LocalDate.of(2025, 4, 13), "08:00");

        Agendamento agendamento = AgendamentoMapper.toEntity(dto, paciente, medico, especialidade);

        assertNotNull(agendamento);
        assertEquals(paciente.id(), agendamento.getUuidPaciente());
        assertEquals(medico.id(), agendamento.getUuidMedico());
        assertEquals(especialidade.descricao(), agendamento.getDescricaoEspecialidade());
        assertEquals(especialidade.especialidade(), agendamento.getTipoEspecialidade());
        assertEquals(dto.data(), agendamento.getData());
        assertEquals(LocalTime.parse("08:00", formatter), agendamento.getHora());
        assertEquals(StatusAgendamento.AGENDADO, agendamento.getStatus());
    }

    @Test
    public void testToResponseDTO() {
        Agendamento agendamento = new Agendamento(
                1L,
                "paciente123",
                "João",
                "medico123",
                "Dr. Silva",
                "Cardiologia",
                TipoEspecialidade.CONSULTA,
                LocalDate.of(2025, 4, 13),
                LocalTime.parse("08:00"),
                StatusAgendamento.AGENDADO
        );

        AgendamentoResponseDTO responseDTO = AgendamentoMapper.toResponseDTO(agendamento);

        assertNotNull(responseDTO);
        assertEquals(agendamento.getId(), responseDTO.id());
        assertEquals(agendamento.getNomePaciente(), responseDTO.nomePaciente());
        assertEquals(agendamento.getNomeMedico(), responseDTO.nomeMedico());
        assertEquals(agendamento.getDescricaoEspecialidade(), responseDTO.descricaoEspecialidade());
        assertEquals(agendamento.getTipoEspecialidade(), responseDTO.tipoEspecialidade());
        assertEquals(agendamento.getData(), responseDTO.data());
        assertEquals(agendamento.getHora(), responseDTO.hora());
        assertEquals(agendamento.getStatus(), responseDTO.status());
    }
}
