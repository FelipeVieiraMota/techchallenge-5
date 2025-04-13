package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.mappers.IUserMapper;
import br.com.agendafacilsus.commonlibrary.domains.dtos.AuthenticationDto;
import br.com.agendafacilsus.commonlibrary.domains.dtos.TokenDto;
import br.com.agendafacilsus.commonlibrary.domains.dtos.UserDto;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager gerenciadorAutenticacao;

    @Mock
    private TokenService servicoToken;

    @Mock
    private IUserMapper mapper;

    private AuthenticationService servicoAutenticacao;

    private final String segredo = "mock-secret";

    @BeforeEach
    void setUp() {
        servicoAutenticacao = new AuthenticationService(gerenciadorAutenticacao, servicoToken, mapper);
        injetarSegredo(servicoAutenticacao, segredo);
    }

    private void injetarSegredo(AuthenticationService servico, String segredo) {
        try {
            var campo = AuthenticationService.class.getDeclaredField("secret");
            campo.setAccessible(true);
            campo.set(servico, segredo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deveAutenticarERetornarToken() {
        var dadosAutenticacao = new AuthenticationDto("Usuário Teste", "usuario.login", "senha123");
        var usuario = new User();
        var usuarioDto = new UserDto("1", "Usuário Teste", "usuario.login", "senha123", UserRole.PACIENTE);
        var tokenEsperado = "jwt-token";

        var autenticacao = mock(Authentication.class);

        when(gerenciadorAutenticacao.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(autenticacao);
        when(autenticacao.getPrincipal()).thenReturn(usuario);
        when(mapper.toDto(usuario)).thenReturn(usuarioDto);
        when(servicoToken.generateToken(segredo, usuarioDto)).thenReturn(tokenEsperado);

        String token = servicoAutenticacao.login(dadosAutenticacao);

        assertEquals(tokenEsperado, token);
        verify(gerenciadorAutenticacao).authenticate(any());
        verify(mapper).toDto(usuario);
        verify(servicoToken).generateToken(segredo, usuarioDto);
    }

    @Test
    void deveValidarTokenComSucesso() {
        var tokenDto = new TokenDto("token-valido");
        when(servicoToken.validateToken(segredo, "token-valido")).thenReturn(true);

        boolean resultado = servicoAutenticacao.validateToken(tokenDto);

        assertTrue(resultado);
        verify(servicoToken).validateToken(segredo, "token-valido");
    }

    @Test
    void deveInvalidarTokenInvalido() {
        var tokenDto = new TokenDto("token-invalido");
        when(servicoToken.validateToken(segredo, "token-invalido")).thenReturn(false);

        boolean resultado = servicoAutenticacao.validateToken(tokenDto);

        assertFalse(resultado);
        verify(servicoToken).validateToken(segredo, "token-invalido");
    }
}
