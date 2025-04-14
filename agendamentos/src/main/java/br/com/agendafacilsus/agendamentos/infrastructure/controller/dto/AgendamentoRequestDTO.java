package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        @Schema(description = "ID do paciente", example = "12345")
        @NotNull String idPaciente,

        @Schema(description = "ID da especialidade", example = "1")
        @NotNull Long idEspecialidade,

        @Schema(description = "ID do Médico", example = "1")
        @NotNull Long idMedico,

        @Schema(description = "Data e hora do agendamento", example = "2025-04-13T14:30:00")
        @NotNull LocalDateTime dataHora
) {}
