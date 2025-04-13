package br.com.agendafacilsus.agendamentos.infrastructure.mapper;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.especialidades.domain.model.Especialidade;

public class AgendamentoMapper {

    private AgendamentoMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Agendamento toEntity(AgendamentoRequestDTO dto, User paciente, Especialidade especialidade) {
        return new Agendamento(
                null, // ID gerado pelo banco
                paciente,
                especialidade,
                dto.dataHora(),
                StatusAgendamento.AGENDADO // status default
        );
    }

    public static AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getPaciente().getId(),
                agendamento.getPaciente().getName(),
                agendamento.getEspecialidade().getId(),
                agendamento.getDataHora(),
                agendamento.getStatus()
        );
    }
}
