package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;

public record AlteracaoStatusRequestDTO (
        StatusAgendamento novoStatus
) {}
