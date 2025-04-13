package br.com.agendafacilsus.autorizacaoeusuarios.domain.model;

import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domains.exceptions.ForbiddenException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void deveCriarUserCorretamente() {
        String loginEsperado = "UserTeste";
        String senhaEsperada = "senhaSecreta";
        UserRole roleEsperada = UserRole.PACIENTE;

        User User = new User(loginEsperado, senhaEsperada, roleEsperada);

        assertEquals(loginEsperado, User.getUsername());
        assertEquals(senhaEsperada, User.getPassword());
        assertEquals(roleEsperada, User.getRole());
    }

    @Test
    void deveRetornarAuthoritiesParaAdministrador() {
        User User = new User("admin", "adminSenha", UserRole.ADMIN);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) User.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_MEDICO")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_PACIENTE")));
    }

    @Test
    void deveRetornarAuthoritiesParaPaciente() {
        User User = new User("paciente", "senhaPaciente", UserRole.PACIENTE);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) User.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_PACIENTE")));
    }

    @Test
    void deveRetornarAuthoritiesParaMedico() {
        User User = new User("medico", "senhaMedico", UserRole.MEDICO);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) User.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_MEDICO")));
    }

    @Test
    void deveLancarForbiddenExceptionSeRoleNaoReconhecida() {
        User User = new User("UserInvalido", "senhaInvalida", null);

        ForbiddenException exception = assertThrows(ForbiddenException.class, User::getAuthorities);
        assertEquals("Not Allowed.", exception.getMessage());
    }

    @Test
    void deveVerificarSeContaNaoExpirada() {
        User User = new User("UserTeste", "senha", UserRole.PACIENTE);

        assertTrue(User.isAccountNonExpired());
    }

    @Test
    void deveVerificarSeContaNaoBloqueada() {
        User User = new User("UserTeste", "senha", UserRole.PACIENTE);

        assertTrue(User.isAccountNonLocked());
    }

    @Test
    void deveVerificarSeCredenciaisNaoExpiradas() {
        User User = new User("UserTeste", "senha", UserRole.PACIENTE);

        assertTrue(User.isCredentialsNonExpired());
    }

    @Test
    void deveVerificarSeUserEstaHabilitado() {
        User User = new User("UserTeste", "senha", UserRole.PACIENTE);

        assertTrue(User.isEnabled());
    }
}
