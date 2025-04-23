package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.UsuarioGateway;
import br.com.agendafacilsus.commonlibrary.domain.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AutenticacaoServiceTest {

    private UsuarioGateway usuarioGateway;
    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        usuarioGateway = mock(UsuarioGateway.class);
        autenticacaoService = new AutenticacaoService(usuarioGateway);
    }

    @Test
    void shouldReturnUserDetailsWhenUsernameExists() throws UsuarioNaoEncontradoException {
        // Arrange
        String username = "user123";
        UserDetails expectedUserDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();

        when(usuarioGateway.buscarPorLogin(username)).thenReturn(expectedUserDetails);

        // Act
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void shouldThrowUsuarioNaoEncontradoExceptionWhenUsernameDoesNotExist() {
        // Arrange
        String username = "nonExistentUser";

        when(usuarioGateway.buscarPorLogin(username)).thenThrow(UsuarioNaoEncontradoException.class);

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            autenticacaoService.loadUserByUsername(username);
        });
    }
}
