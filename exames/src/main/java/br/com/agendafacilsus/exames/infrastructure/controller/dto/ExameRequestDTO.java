package br.com.agendafacilsus.exames.infrastructure.controller.dto;

import br.com.agendafacilsus.exames.domain.enums.TipoExame;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ExameRequestDTO(
        @NotBlank TipoExame tipoExame
) {}
