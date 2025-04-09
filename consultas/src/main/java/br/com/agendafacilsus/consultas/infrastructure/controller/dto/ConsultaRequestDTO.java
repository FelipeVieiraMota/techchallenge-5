package br.com.agendafacilsus.consultas.infrastructure.controller.dto;

import br.com.agendafacilsus.consultas.domain.enums.EspecialidadeConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRequestDTO(
        @NotNull EspecialidadeConsulta especialidade
) {}

