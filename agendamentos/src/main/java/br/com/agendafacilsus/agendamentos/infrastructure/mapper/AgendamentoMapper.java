package br.com.agendafacilsus.agendamentos.infrastructure.mapper;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;

public class AgendamentoMapper {

    private AgendamentoMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Agendamento toEntity(AgendamentoRequestDTO dto) {
        return new Agendamento(
                null, // ID será gerado pelo banco
                dto.nomePaciente(),
                dto.referenciaId(),
                dto.dataHora(),
                StatusAgendamento.AGENDADO // status default ao criar
        );
    }

    public static AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getNomePaciente(),
                agendamento.getReferenciaId(),
                agendamento.getDataHora(),
                agendamento.getStatus()
        );
    }
}
