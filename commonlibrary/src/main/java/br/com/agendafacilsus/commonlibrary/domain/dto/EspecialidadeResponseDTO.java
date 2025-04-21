package br.com.agendafacilsus.commonlibrary.domain.dto;

import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;

public record EspecialidadeResponseDTO(
        Long id,
        String descricao,
        TipoEspecialidade especialidade
) {}
