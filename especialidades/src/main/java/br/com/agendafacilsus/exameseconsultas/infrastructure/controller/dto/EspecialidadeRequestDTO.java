package br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto;

import br.com.agendafacilsus.exameseconsultas.domain.enums.TipoEspecialidade;
import jakarta.validation.constraints.NotBlank;

public record EspecialidadeRequestDTO(
        @NotBlank String descricao,
        @NotBlank TipoEspecialidade tipoEspecialidade
) {}

