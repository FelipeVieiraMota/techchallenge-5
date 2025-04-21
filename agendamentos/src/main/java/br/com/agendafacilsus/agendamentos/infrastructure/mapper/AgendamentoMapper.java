package br.com.agendafacilsus.agendamentos.infrastructure.mapper;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;

public class AgendamentoMapper {

    private AgendamentoMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Agendamento toEntity(AgendamentoRequestDTO dto, Usuario paciente, Usuario medico, Especialidade especialidade) {
        return new Agendamento(
                null, // ID gerado pelo banco
                paciente,
                medico,
                especialidade,
                dto.dataHora(),
                StatusAgendamento.AGENDADO // status default
        );
    }

    public static AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getPaciente().getNome(),
                agendamento.getMedico().getNome(),
                agendamento.getEspecialidade().getDescricao(),
                agendamento.getDataHora(),
                agendamento.getStatus()
        );
    }
}
