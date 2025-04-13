package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.repository.UserRepository;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repositorioUsuario;

    private UserService servicoUsuario;

    @BeforeEach
    void setUp() {
        servicoUsuario = new UserService(repositorioUsuario);
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        String login = "usuario@teste.com";
        User usuario = new User();
        when(repositorioUsuario.findByLogin(login)).thenReturn(usuario);

        UserDetails resultado = servicoUsuario.findByLogin(login);

        assertNotNull(resultado);
        verify(repositorioUsuario).findByLogin(login);
    }

    @Test
    void deveSalvarNovoUsuario() {
        User novoUsuario = new User();
        when(repositorioUsuario.save(novoUsuario)).thenReturn(novoUsuario);

        User resultado = servicoUsuario.save(novoUsuario);

        assertNotNull(resultado);
        verify(repositorioUsuario).save(novoUsuario);
    }

    @Test
    void deveDeletarUsuarioPorId() {
        String id = "123";

        servicoUsuario.deleteUserById(id);

        verify(repositorioUsuario).deleteById(id);
    }

    @Test
    void deveListarTodosUsuariosExcetoAdmins() {
        Page<User> paginaUsuarios = new PageImpl<>(List.of(new User()));
        when(repositorioUsuario.findByRoleNot(eq(UserRole.ADMIN), any(PageRequest.class)))
                .thenReturn(paginaUsuarios);

        Page<User> resultado = servicoUsuario.getAllUsers(0, 5);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(repositorioUsuario).findByRoleNot(eq(UserRole.ADMIN), any(PageRequest.class));
    }

    @Test
    void deveListarTodosPacientes() {
        Page<User> paginaPacientes = new PageImpl<>(List.of(new User()));
        when(repositorioUsuario.findByRole(eq(UserRole.PACIENTE), any(PageRequest.class)))
                .thenReturn(paginaPacientes);

        Page<User> resultado = servicoUsuario.getAllPatients(0, 5);

        assertNotNull(resultado);
        verify(repositorioUsuario).findByRole(eq(UserRole.PACIENTE), any(PageRequest.class));
    }

    @Test
    void deveListarTodosMedicos() {
        Page<User> paginaMedicos = new PageImpl<>(List.of(new User()));
        when(repositorioUsuario.findByRole(eq(UserRole.MEDICO), any(PageRequest.class)))
                .thenReturn(paginaMedicos);

        Page<User> resultado = servicoUsuario.getAllDoctors(0, 5);

        assertNotNull(resultado);
        verify(repositorioUsuario).findByRole(eq(UserRole.MEDICO), any(PageRequest.class));
    }

    @Test
    void deveBuscarUsuarioPorId() {
        String id = "abc123";
        User usuario = new User();
        when(repositorioUsuario.findUserById(id)).thenReturn(usuario);

        User resultado = servicoUsuario.findUserById(id);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        verify(repositorioUsuario).findUserById(id);
    }
}
