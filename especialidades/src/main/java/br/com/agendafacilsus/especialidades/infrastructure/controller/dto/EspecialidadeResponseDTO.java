package br.com.agendafacilsus.especialidades.infrastructure.controller.dto;

import br.com.agendafacilsus.especialidades.domain.enums.TipoEspecialidade;

public record EspecialidadeResponseDTO(
        Long id,
        String descricao,
        TipoEspecialidade especialidade
) {}
