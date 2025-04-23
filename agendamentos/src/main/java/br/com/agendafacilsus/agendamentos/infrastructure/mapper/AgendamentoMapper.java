package br.com.agendafacilsus.agendamentos.infrastructure.mapper;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import lombok.val;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AgendamentoMapper {

    AgendamentoMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Agendamento toEntity(AgendamentoRequestDTO dto, UsuarioResponseDTO paciente,
                                       UsuarioResponseDTO medico, EspecialidadeResponseDTO especialidade) {
        val horaConvertida = LocalTime.parse(dto.hora(), DateTimeFormatter.ofPattern("HH:mm"));

        return new Agendamento(
                null, // ID gerado pelo banco
                paciente.id(),
                paciente.nome(),
                medico.id(),
                medico.nome(),
                especialidade.descricao(),
                especialidade.especialidade(),
                dto.data(),
                horaConvertida,
                StatusAgendamento.AGENDADO // status default
        );
    }

    public static AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getNomePaciente(),
                agendamento.getNomeMedico(),
                agendamento.getDescricaoEspecialidade(),
                agendamento.getTipoEspecialidade(),
                agendamento.getData(),
                agendamento.getHora(),
                agendamento.getStatus()
        );
    }
}
