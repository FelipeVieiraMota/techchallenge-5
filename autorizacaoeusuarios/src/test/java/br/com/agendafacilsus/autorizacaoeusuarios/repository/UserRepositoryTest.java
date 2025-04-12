package br.com.agendafacilsus.autorizacaoeusuarios.repository;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository repositorioUsuario;

    private User usuarioPaciente;
    private User usuarioMedico;
    private User usuarioAdmin;

    @BeforeEach
    void setUp() {
        usuarioPaciente = new User("paciente@teste.com", "senha123", UserRole.PACIENTE);
        usuarioMedico = new User("medico@teste.com", "senha456", UserRole.MEDICO);
        usuarioAdmin = new User("admin@teste.com", "senha789", UserRole.ADMIN);
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        when(repositorioUsuario.findByLogin("paciente@teste.com")).thenReturn(usuarioPaciente);
        UserDetails resultado = repositorioUsuario.findByLogin("paciente@teste.com");

        assertNotNull(resultado);
        assertEquals("paciente@teste.com", resultado.getUsername());

        verify(repositorioUsuario, times(1)).findByLogin("paciente@teste.com");
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(repositorioUsuario.findUserById("1")).thenReturn(usuarioPaciente);

        User resultado = repositorioUsuario.findUserById("1");

        assertNotNull(resultado);
        assertEquals("paciente@teste.com", resultado.getLogin());

        verify(repositorioUsuario, times(1)).findUserById("1");
    }

    @Test
    void deveBuscarUsuariosQueNaoSaoAdmins() {
        Page<User> pagina = new PageImpl<>(List.of(usuarioPaciente, usuarioMedico));
        when(repositorioUsuario.findByRoleNot(eq(UserRole.ADMIN), any(Pageable.class))).thenReturn(pagina);

        Page<User> resultado = repositorioUsuario.findByRoleNot(UserRole.ADMIN, PageRequest.of(0, 10));

        assertEquals(2, resultado.getTotalElements());
        resultado.forEach(usuario -> assertNotEquals(UserRole.ADMIN, usuario.getRole()));

        verify(repositorioUsuario, times(1)).findByRoleNot(UserRole.ADMIN, PageRequest.of(0, 10));
    }

    @Test
    void deveBuscarApenasUsuariosComRolePaciente() {
        Page<User> paginaPacientes = new PageImpl<>(List.of(usuarioPaciente));
        when(repositorioUsuario.findByRole(eq(UserRole.PACIENTE), any(Pageable.class))).thenReturn(paginaPacientes);

        Page<User> resultado = repositorioUsuario.findByRole(UserRole.PACIENTE, PageRequest.of(0, 10));

        assertEquals(1, resultado.getTotalElements());
        assertEquals(UserRole.PACIENTE, resultado.getContent().get(0).getRole());

        verify(repositorioUsuario, times(1)).findByRole(UserRole.PACIENTE, PageRequest.of(0, 10));
    }

    @Test
    void deveBuscarApenasUsuariosComRoleMedico() {
        Page<User> paginaMedicos = new PageImpl<>(List.of(usuarioMedico));
        when(repositorioUsuario.findByRole(eq(UserRole.MEDICO), any(Pageable.class))).thenReturn(paginaMedicos);

        Page<User> resultado = repositorioUsuario.findByRole(UserRole.MEDICO, PageRequest.of(0, 10));

        assertEquals(1, resultado.getTotalElements());
        assertEquals(UserRole.MEDICO, resultado.getContent().get(0).getRole());

        verify(repositorioUsuario, times(1)).findByRole(UserRole.MEDICO, PageRequest.of(0, 10));
    }
}
