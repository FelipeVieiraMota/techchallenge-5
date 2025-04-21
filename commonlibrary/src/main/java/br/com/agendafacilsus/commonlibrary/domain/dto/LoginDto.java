package br.com.agendafacilsus.commonlibrary.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDto(
        @Schema(description = "Login do usuário", example = "admin")
        String login,

        @Schema(description = "Senha do usuário", example = "test123")
        String senha
) {}
