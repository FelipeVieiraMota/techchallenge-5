package br.com.agendafacilsus.autorizacaoeusuarios.filter;

import br.com.agendafacilsus.autorizacaoeusuarios.application.usecase.UsuarioUseCase;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SecurityFilterAutorizacaoEUsuariosTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioUseCase usuarioUseCase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private SecurityFilterAutorizacaoEUsuarios filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new SecurityFilterAutorizacaoEUsuarios(tokenService, usuarioUseCase);
        ReflectionTestUtils.setField(filter, "secret", "minhaChaveSecreta");
    }

    @Test
    void deveExecutarCheckTokenRolesComParametrosCorretos() throws IOException {
        filter.doFilterInternal(request, response, filterChain);

        verify(tokenService, times(1)).checkTokenRoles("minhaChaveSecreta", request, response, filterChain);
    }
}
