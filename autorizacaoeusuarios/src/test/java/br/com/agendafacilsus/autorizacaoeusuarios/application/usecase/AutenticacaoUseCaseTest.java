package br.com.agendafacilsus.autorizacaoeusuarios.application.usecase;

import br.com.agendafacilsus.commonlibrary.domain.dto.LoginDto;
import br.com.agendafacilsus.commonlibrary.domain.dto.TokenDto;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AutenticacaoUseCase autenticacaoUseCase;

    //private final String secret = "mock-secret";
    private LoginDto loginDto;
    private TokenDto tokenDto;
    //private Usuario usuario;


    @BeforeEach
    void setUp() {
        loginDto = new LoginDto("user123", "password123");
        tokenDto = new TokenDto("testToken");
        //usuario = new Usuario("user", "user123", "password123", UserRole.ADMIN);

    }

    @Test
    void shouldReturnTrueWhenTokenIsValid() {
        when(tokenService.validateToken(null, tokenDto.token())).thenReturn(true);

        boolean isValid = autenticacaoUseCase.validarToken(tokenDto);

        assertTrue(isValid);
        verify(tokenService, times(1)).validateToken(null, tokenDto.token());
    }

    @Test
    void shouldReturnFalseWhenTokenIsInvalid() {
        when(tokenService.validateToken(null, tokenDto.token())).thenReturn(false);

        boolean isValid = autenticacaoUseCase.validarToken(tokenDto);

        assertFalse(isValid);
        verify(tokenService, times(1)).validateToken(null, tokenDto.token());
    }


}
