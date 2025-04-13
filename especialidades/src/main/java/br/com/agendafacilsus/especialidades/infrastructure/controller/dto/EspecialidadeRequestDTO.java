package br.com.agendafacilsus.especialidades.infrastructure.controller.dto;

import br.com.agendafacilsus.especialidades.domain.enums.TipoEspecialidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EspecialidadeRequestDTO(
        @Schema(description = "Descrição da especialidade", example = "Cardiologia")
        @NotBlank
        String descricao,

        @Schema(description = "Tipo da especialidade", example = "CONSULTA")
        @NotBlank
        TipoEspecialidade tipoEspecialidade
) {}
