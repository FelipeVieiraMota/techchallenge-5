package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.controller.exceptions.UserAlreadyExistsException;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.mappers.IFetchMapper;
import br.com.agendafacilsus.commonlibrary.domains.dtos.FetchUserDto;
import br.com.agendafacilsus.commonlibrary.domains.dtos.RegisterDto;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private UserService servicoUsuario;

    @Mock
    private IFetchMapper conversor;

    private AuthorizationService servicoAutorizacao;

    @BeforeEach
    void setUp() {
        servicoAutorizacao = new AuthorizationService(servicoUsuario, conversor);
    }

    @Test
    void deveCarregarUsuarioPorLogin() {
        String login = "usuario@teste.com";
        User usuario = new User();
        when(servicoUsuario.findByLogin(login)).thenReturn(usuario);

        var resultado = servicoAutorizacao.loadUserByUsername(login);

        assertNotNull(resultado);
        verify(servicoUsuario).findByLogin(login);
    }

    @Test
    void deveLancarErroSeUsuarioJaExiste() {
        RegisterDto dados = new RegisterDto("João", "joao", "senha123", UserRole.PACIENTE);
        when(servicoUsuario.findByLogin(dados.login())).thenReturn(new User());

        assertThrows(UserAlreadyExistsException.class, () -> servicoAutorizacao.register(dados));
        verify(servicoUsuario).findByLogin(dados.login());
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        RegisterDto dados = new RegisterDto("Maria", "maria", "senhaSegura", UserRole.MEDICO);
        when(servicoUsuario.findByLogin(dados.login())).thenReturn(null);
        when(servicoUsuario.save(any(User.class))).thenReturn(new User());

        var usuarioSalvo = servicoAutorizacao.register(dados);

        assertNotNull(usuarioSalvo);
        verify(servicoUsuario).save(any(User.class));
    }

    @Test
    void deveExcluirUsuarioPorId() {
        String id = "123";

        servicoAutorizacao.deleteUserById(id);

        verify(servicoUsuario).deleteUserById(id);
    }

    @Test
    void deveListarTodosUsuarios() {
        Page<User> paginaUsuarios = new PageImpl<>(List.of(new User()));
        when(servicoUsuario.getAllUsers(0, 10)).thenReturn(paginaUsuarios);
        when(conversor.toDto(any(User.class))).thenReturn(
                new FetchUserDto("1", "Fulano", "fulano", UserRole.PACIENTE)
        );

        Page<FetchUserDto> resultado = servicoAutorizacao.getAllUsers(0, 10);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(servicoUsuario).getAllUsers(0, 10);
    }

    @Test
    void deveListarTodosPacientes() {
        Page<User> paginaPacientes = new PageImpl<>(List.of(new User()));
        when(servicoUsuario.getAllPatients(0, 10)).thenReturn(paginaPacientes);
        when(conversor.toDto(any(User.class))).thenReturn(
                new FetchUserDto("2", "Paciente", "paciente", UserRole.PACIENTE)
        );

        Page<FetchUserDto> resultado = servicoAutorizacao.getAllPatients(0, 10);

        assertNotNull(resultado);
        verify(servicoUsuario).getAllPatients(0, 10);
    }

    @Test
    void deveListarTodosMedicos() {
        Page<User> paginaMedicos = new PageImpl<>(List.of(new User()));
        when(servicoUsuario.getAllDoctors(0, 10)).thenReturn(paginaMedicos);
        when(conversor.toDto(any(User.class))).thenReturn(
                new FetchUserDto("3", "Médico", "medico", UserRole.MEDICO)
        );

        Page<FetchUserDto> resultado = servicoAutorizacao.getAllDoctors(0, 10);

        assertNotNull(resultado);
        verify(servicoUsuario).getAllDoctors(0, 10);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        String id = "abc123";
        User usuario = new User();
        FetchUserDto dto = new FetchUserDto("abc123", "Nome", "login", UserRole.ADMIN);

        when(servicoUsuario.findUserById(id)).thenReturn(usuario);
        when(conversor.toDto(usuario)).thenReturn(dto);

        FetchUserDto resultado = servicoAutorizacao.findUserById(id);

        assertNotNull(resultado);
        assertEquals("abc123", resultado.id());
        verify(servicoUsuario).findUserById(id);
    }
}
