package br.com.agendafacilsus.autorizacaoeusuarios.domain.dto;

import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @Schema(description = "Nome do usuário", example = "João da Silva")
        @NotBlank
        String nome,

        @Schema(description = "Login do usuário", example = "admin")
        @NotBlank
        String login,

        @Schema(description = "Senha do usuário", example = "test123")
        @NotBlank
        String senha,

        @Schema(description = "Papel do usuário", example = "ADMIN")
        @NotNull
        UserRole role
) {}
