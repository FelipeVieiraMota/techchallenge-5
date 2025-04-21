package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoResponseDTO(
        Long id,
        String nomePaciente,
        String nomeMedico,
        String descricaoEspecialidade,
        TipoEspecialidade tipoEspecialidade,
        LocalDate data,
        LocalTime hora,
        StatusAgendamento status
) {}
