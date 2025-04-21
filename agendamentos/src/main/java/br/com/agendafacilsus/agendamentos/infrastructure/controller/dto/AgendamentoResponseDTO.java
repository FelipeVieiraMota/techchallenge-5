package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        String nomePaciente,
        String nomeMedico,
        String descricaoEspecialidade,
        LocalDateTime dataHora,
        StatusAgendamento status
) {}
