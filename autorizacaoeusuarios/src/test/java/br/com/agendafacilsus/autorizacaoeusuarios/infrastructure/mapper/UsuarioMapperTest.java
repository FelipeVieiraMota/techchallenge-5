package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioRequestDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    void toEntity_DeveConverterRequestDTOParaEntidade() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("João da Silva", "joao", "senha123", UserRole.ADMIN);

        Usuario usuario = UsuarioMapper.toEntity(dto);

        assertNotNull(usuario);
        assertEquals(dto.nome(), usuario.getNome());
        assertEquals(dto.login(), usuario.getLogin());
        assertEquals(dto.senha(), usuario.getSenha());
        assertEquals(dto.role(), usuario.getRole());
    }

    @Test
    void toResponseDTO_DeveConverterEntidadeParaResponseDTO() {
        Usuario usuario = Usuario.builder()
                .id("abc123")
                .nome("Maria")
                .login("maria")
                .senha("segredo")
                .role(UserRole.MEDICO)
                .build();

        UsuarioResponseDTO responseDTO = UsuarioMapper.toResponseDTO(usuario);

        assertNotNull(responseDTO);
        assertEquals("abc123", responseDTO.id());
        assertEquals("Maria", responseDTO.nome());
        assertEquals("maria", responseDTO.login());
        assertEquals(UserRole.MEDICO, responseDTO.role());
    }

    @Test
    void construtor_DeveLancarExcecaoQuandoInstanciado() {
        assertThrows(UnsupportedOperationException.class, UsuarioMapper::new);
    }
}
