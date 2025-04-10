package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import jakarta.validation.constraints.NotBlank;

public record AlteracaoStatusRequestDTO (@NotBlank StatusAgendamento novoStatus) {}
