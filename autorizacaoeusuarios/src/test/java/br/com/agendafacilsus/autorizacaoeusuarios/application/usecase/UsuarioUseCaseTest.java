package br.com.agendafacilsus.autorizacaoeusuarios.application.usecase;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioRequestDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.UsuarioGateway;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.UsuarioMapper;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.exception.UsuarioNaoEncontradoException;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private UsuarioRequestDTO usuarioRequestDTO;
    private UsuarioResponseDTO usuarioResponseDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioRequestDTO = new UsuarioRequestDTO("USER", "user123", "password123", UserRole.ADMIN);
        usuarioResponseDTO = new UsuarioResponseDTO("1L", "user123", "password123", UserRole.ADMIN);
        usuario = new Usuario("USER", "user123", "password123", UserRole.ADMIN);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(usuarioGateway.salvar(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO response = usuarioUseCase.criar(usuarioRequestDTO);

        assertNotNull(response);
        assertEquals("user123", response.login());
        verify(usuarioGateway, times(1)).salvar(any(Usuario.class));
    }

    @Test
    void shouldListUsersSuccessfully() {
        List<Usuario> usuarios = List.of(usuario);
        when(usuarioGateway.listar()).thenReturn(usuarios);

        List<UsuarioResponseDTO> response = usuarioUseCase.listar();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("user123", response.get(0).login());
        verify(usuarioGateway, times(1)).listar();
    }

    @Test
    void shouldThrowUsuarioNaoEncontradoExceptionWhenUserNotFoundById() {
        String userId = "nonExistentUserId";
        when(usuarioGateway.buscarPorId(userId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioUseCase.buscarPorId(userId));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        String userId = "user123";
        when(usuarioGateway.buscarPorId(userId)).thenReturn(Optional.of(usuario));
        when(usuarioGateway.salvar(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO response = usuarioUseCase.atualizar(userId, usuarioRequestDTO);

        assertNotNull(response);
        assertEquals("user123", response.login());
        verify(usuarioGateway, times(1)).buscarPorId(userId);
        verify(usuarioGateway, times(1)).salvar(any(Usuario.class));
    }

    @Test
    void shouldThrowUsuarioNaoEncontradoExceptionWhenUpdateUserNotFound() {
        String userId = "nonExistentUserId";
        when(usuarioGateway.buscarPorId(userId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioUseCase.atualizar(userId, usuarioRequestDTO));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        String userId = "user123";
        when(usuarioGateway.buscarPorId(userId)).thenReturn(Optional.of(usuario));

        usuarioUseCase.excluir(userId);

        verify(usuarioGateway, times(1)).buscarPorId(userId);
        verify(usuarioGateway, times(1)).excluir(userId);
    }

    @Test
    void shouldThrowUsuarioNaoEncontradoExceptionWhenDeleteUserNotFound() {
        String userId = "nonExistentUserId";
        when(usuarioGateway.buscarPorId(userId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioUseCase.excluir(userId));
    }

    @Test
    void shouldReturnUserDetailsByLogin() {
        String login = "user123";
        UserDetails userDetails = mock(UserDetails.class);
        when(usuarioGateway.buscarPorLogin(login)).thenReturn(userDetails);

        UserDetails response = usuarioUseCase.buscarPorLogin(login);

        assertNotNull(response);
        verify(usuarioGateway, times(1)).buscarPorLogin(login);
    }

    @Test
    void shouldReturnUsersByRole() {
        UserRole role = UserRole.ADMIN;
        Usuario usuario = new Usuario();
        Page<Usuario> usuarioPage = new PageImpl<>(List.of(usuario));
        when(usuarioGateway.buscarPorPapel(role, 0, 10)).thenReturn(usuarioPage);

        Page<UsuarioResponseDTO> response = usuarioUseCase.buscarPorPapel(role, 0, 10);

        assertNotNull(response);
        verify(usuarioGateway, times(1)).buscarPorPapel(role, 0, 10);
    }
}
