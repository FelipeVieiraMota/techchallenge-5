package br.com.agendafacilsus.consultas.infrastructure.controller.dto;

import br.com.agendafacilsus.consultas.domain.enums.EspecialidadeConsulta;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        Long id,
        EspecialidadeConsulta especialidade
) {}
