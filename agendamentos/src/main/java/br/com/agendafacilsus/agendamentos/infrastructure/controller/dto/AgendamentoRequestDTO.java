package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import br.com.agendafacilsus.agendamentos.domain.enums.TipoAgendamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        @NotBlank String paciente,
        @NotNull TipoAgendamento tipo,
        @NotNull Long referenciaId,
        @NotNull LocalDateTime dataHora
) {}
