package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        @NotBlank String nomePaciente,
        @NotNull Long pacienteId,
        @NotNull Long referenciaId,
        @NotNull LocalDateTime dataHora
) {}
