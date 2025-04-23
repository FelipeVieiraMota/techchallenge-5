package br.com.agendafacilsus.agendamentos.configuration;

import br.com.agendafacilsus.agendamentos.filter.SecurityFilterAgendamentos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@Import(SecurityConfigurationAgendamentos.class)
class SecurityConfigurationAgendamentosTest {

    @MockBean
    private SecurityFilterAgendamentos securityFilter;

    @MockBean
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private SecurityConfigurationAgendamentos securityConfig;

    @Test
    void deveCriarPasswordEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoderAgendamentos();
        assertNotNull(encoder);
        String encoded = encoder.encode("senha123");
        assertTrue(encoder.matches("senha123", encoded));
    }

    @Test
    void deveObterAuthenticationManagerDoAuthenticationConfiguration() throws Exception {
        AuthenticationManager managerMock = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(managerMock);

        AuthenticationManager result = new SecurityConfigurationAgendamentos(securityFilter)
                .authenticationManagerAgendamentos(authenticationConfiguration);

        assertNotNull(result);
        assertEquals(managerMock, result);
    }


}
