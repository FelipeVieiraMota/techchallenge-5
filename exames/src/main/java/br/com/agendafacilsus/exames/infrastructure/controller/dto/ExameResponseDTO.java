package br.com.agendafacilsus.exames.infrastructure.controller.dto;

import br.com.agendafacilsus.exames.domain.enums.TipoExame;

import java.time.LocalDateTime;

public record ExameResponseDTO(
        Long id,
        TipoExame tipoExame
) {}
