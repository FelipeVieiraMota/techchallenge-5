package br.com.agendafacilsus.autorizacaoeusuarios.mappers;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.UserMapper;
import br.com.agendafacilsus.commonlibrary.domains.dtos.UserDto;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;
    private User usuario;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        usuario = new User("123", "João da Silva", "joao@teste.com", "senha123", UserRole.PACIENTE);
    }

    @Test
    void deveMapearUsuarioParaUserDto() {
        UserDto dto = userMapper.toDto(usuario);

        assertNotNull(dto);
        assertEquals(usuario.getId(), dto.id());
        assertEquals(usuario.getName(), dto.name());
        assertEquals(usuario.getLogin(), dto.login());
        assertEquals(usuario.getPassword(), dto.password());
        assertEquals(usuario.getRole(), dto.role());
    }

    @Test
    void deveMapearCamposCorretosDoUsuario() {
        UserDto dto = userMapper.toDto(usuario);

        assertEquals("123", dto.id());
        assertEquals("João da Silva", dto.name());
        assertEquals("joao@teste.com", dto.login());
        assertEquals("senha123", dto.password());
        assertEquals(UserRole.PACIENTE, dto.role());
    }
}
