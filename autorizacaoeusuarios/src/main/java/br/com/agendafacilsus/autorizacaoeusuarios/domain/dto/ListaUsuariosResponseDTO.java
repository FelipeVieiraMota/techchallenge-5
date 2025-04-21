package br.com.agendafacilsus.autorizacaoeusuarios.domain.dto;

import java.util.List;

public record ListaUsuariosResponseDTO(
        long total,
        List<UsuarioResponseDTO> usuarios
) {}

