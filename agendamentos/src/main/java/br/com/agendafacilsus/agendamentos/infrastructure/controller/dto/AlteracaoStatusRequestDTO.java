package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AlteracaoStatusRequestDTO (
        @Schema(description = "Novo status do agendamento", example = "AGENDADO")
        @NotNull StatusAgendamento novoStatus
) {}
