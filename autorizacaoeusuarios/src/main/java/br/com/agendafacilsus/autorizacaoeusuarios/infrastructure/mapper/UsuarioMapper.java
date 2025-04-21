package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioRequestDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;

public class UsuarioMapper {
    private UsuarioMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        return Usuario.builder()
                .nome(dto.nome())
                .login(dto.login())
                .senha(dto.senha())
                .role(dto.role())
                .build();
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getRole()
        );
    }
}
