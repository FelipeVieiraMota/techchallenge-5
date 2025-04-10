package br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto;

import br.com.agendafacilsus.exameseconsultas.domain.enums.TipoEspecialidade;

public record EspecialidadeResponseDTO(
        Long id,
        String descricao,
        TipoEspecialidade especialidade
) {}
