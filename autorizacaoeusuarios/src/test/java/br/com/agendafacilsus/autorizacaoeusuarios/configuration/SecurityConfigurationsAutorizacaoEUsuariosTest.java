package br.com.agendafacilsus.autorizacaoeusuarios.configuration;

import br.com.agendafacilsus.autorizacaoeusuarios.filter.SecurityFilterAutorizacaoEUsuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationsAutorizacaoEUsuariosTest {

    @InjectMocks
    private SecurityConfigurationsAutorizacaoEUsuarios securityConfigurations;

    @Mock
    private SecurityFilterAutorizacaoEUsuarios securityFilter;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mockManager);

        AuthenticationManager authenticationManager = securityConfigurations.authenticationManager(authenticationConfiguration);

        assertNotNull(authenticationManager);
        verify(authenticationConfiguration, times(1)).getAuthenticationManager();
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder encoder = securityConfigurations.passwordEncoder();
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void testSecurityFilter() {
        assertNotNull(securityFilter);
    }
}
