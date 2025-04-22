package br.com.agendafacilsus.autorizacaoeusuarios.domain.dto;

import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;

import java.util.List;

public record ListaUsuariosResponseDTO(
        long total,
        List<UsuarioResponseDTO> usuarios
) {}

