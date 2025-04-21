package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AgendamentoRequestDTO(
        @Schema(description = "ID do paciente", example = "12345")
        @NotBlank String idPaciente,

        @Schema(description = "ID da especialidade", example = "1L")
        @NotNull Long idEspecialidade,

        @Schema(description = "ID do Médico", example = "1")
        @NotBlank String idMedico,

        @NotNull
        @Schema(description = "Data do agendamento", example = "2025-04-13")
        LocalDate data,

        @NotBlank
        @Schema(description = "Hora do agendamento", example = "08:00")
        String hora
) {}
