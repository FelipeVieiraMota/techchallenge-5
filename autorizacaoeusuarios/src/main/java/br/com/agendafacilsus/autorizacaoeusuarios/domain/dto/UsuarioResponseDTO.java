package br.com.agendafacilsus.autorizacaoeusuarios.domain.dto;

import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;

public record UsuarioResponseDTO(
        String id,
        String nome,
        String login,
        UserRole role
) {}
