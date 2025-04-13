package br.com.agendafacilsus.autorizacaoeusuarios.mappers;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.FetchUserDto;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FetchMapperTest {

    private FetchMapper fetchMapper;
    private User usuario;

    @BeforeEach
    void setUp() {
        fetchMapper = new FetchMapper();
        usuario = new User("123", "João da Silva", "joao@teste.com", "senha123",UserRole.PACIENTE);
    }

    @Test
    void deveMapearUsuarioParaFetchUserDto() {
        FetchUserDto dto = fetchMapper.toDto(usuario);

        assertNotNull(dto);
        assertEquals(usuario.getId(), dto.id());
        assertEquals(usuario.getName(), dto.name());
        assertEquals(usuario.getLogin(), dto.login());
        assertEquals(usuario.getRole(), dto.role());
    }

    @Test
    void deveMapearUsuarioComCamposCorretos() {
        FetchUserDto dto = fetchMapper.toDto(usuario);

        assertEquals("123", dto.id());
        assertEquals("João da Silva", dto.name());
        assertEquals("joao@teste.com", dto.login());
        assertEquals(UserRole.PACIENTE, dto.role());
    }
}
