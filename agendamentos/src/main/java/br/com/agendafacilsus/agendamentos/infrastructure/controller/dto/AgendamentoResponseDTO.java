package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import br.com.agendafacilsus.agendamentos.domain.enums.TipoAgendamento;
import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        String paciente,
        TipoAgendamento tipo,
        Long referenciaId,
        LocalDateTime dataHora,
        StatusAgendamento status
) {}
