package br.com.agendafacilsus.autorizacaoeusuarios.filter;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.service.UserService;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private SecurityFilter securityFilter;

    @BeforeEach
    void setUp() {
        securityFilter = new SecurityFilter(tokenService, userService);
    }

    @Test
    void deveAutenticarUsuarioQuandoTokenValido() throws Exception {
        String token = "validToken";
        String login = "usuario@teste.com";
        var usuarioMock = mock(User.class);

        when(tokenService.recoverToken(request)).thenReturn(token);
        when(tokenService.tokenSubject(any(), eq(token))).thenReturn(login);
        when(userService.findByLogin(login)).thenReturn(usuarioMock);
        when(tokenService.extractClaims(any(), eq(token))).thenReturn(null);
        when(tokenService.isTokenExpired(any())).thenReturn(false);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(tokenService).recoverToken(request);
        verify(userService).findByLogin(login);
    }

    @Test
    void deveRetornarErroQuandoTokenExpirado() throws Exception {
        String token = "expiredToken";
        String login = "usuario@teste.com";
        var usuarioMock = mock(User.class);

        when(tokenService.recoverToken(request)).thenReturn(token);
        when(tokenService.tokenSubject(any(), eq(token))).thenReturn(login);
        when(userService.findByLogin(login)).thenReturn(usuarioMock);
        when(tokenService.extractClaims(any(), eq(token))).thenReturn(null);
        when(tokenService.isTokenExpired(any())).thenReturn(true);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write(Mockito.contains("Not Allowed."));
    }

    @Test
    void deveRetornarErroQuandoUsuarioNaoEncontrado() throws Exception {
        String token = "validToken";
        String login = "usuario@teste.com";

        when(tokenService.recoverToken(request)).thenReturn(token);
        when(tokenService.tokenSubject(any(), eq(token))).thenReturn(login);
        when(userService.findByLogin(login)).thenReturn(null);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write(Mockito.contains("Not Allowed."));
    }

    @Test
    void deveRetornarErroQuandoTokenNaoForFornecido() throws Exception {
        when(tokenService.recoverToken(request)).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deveRetornarErroGenericoQuandoExcecaoNaoEsperada() throws Exception {
        doThrow(new RuntimeException("Erro inesperado")).when(tokenService).recoverToken(request);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(writer).write(Mockito.contains("Unpredictable error happened."));
    }
}
