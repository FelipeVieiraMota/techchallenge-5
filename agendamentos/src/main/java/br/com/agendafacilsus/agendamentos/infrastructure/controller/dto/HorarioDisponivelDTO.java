package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record HorarioDisponivelDTO(
        @Schema(description = "ID do médico", example = "1")
        String medicoId,

        @Schema(description = "Data disponível para agendamento", example = "2025-04-13")
        LocalDate data,

        @Schema(description = "Lista de horários disponíveis", example = "[\"08:00\", \"09:30\"]")
        List<String> horarios
) {}
