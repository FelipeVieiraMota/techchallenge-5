package br.com.agendafacilsus.commonlibrary.domain.dto;

import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;

public record UsuarioResponseDTO(
        String id,
        String nome,
        String login,
        UserRole role
) {}
