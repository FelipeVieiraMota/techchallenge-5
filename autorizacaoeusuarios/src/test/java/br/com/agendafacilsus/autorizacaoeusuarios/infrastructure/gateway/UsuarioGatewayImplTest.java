package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway;

import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioDuplicadoException;
import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioGatewayException;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.repository.UsuarioRepository;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioGatewayImplTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioGatewayImpl usuarioGateway;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioGateway = new UsuarioGatewayImpl(usuarioRepository);
    }

    @Test
    void salvar_DeveSalvarUsuarioComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");

        when(usuarioRepository.existsByLoginIgnoreCase("admin")).thenReturn(false);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioGateway.salvar(usuario);

        assertEquals(usuario, result);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void salvar_DeveLancarUsuarioDuplicadoException_SeUsuarioJaExistir() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");

        when(usuarioRepository.existsByLoginIgnoreCase("admin")).thenReturn(true);

        assertThrows(UsuarioDuplicadoException.class, () -> usuarioGateway.salvar(usuario));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void salvar_DeveLancarUsuarioGatewayException_SeErroInterno() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");

        when(usuarioRepository.existsByLoginIgnoreCase("admin")).thenThrow(new RuntimeException("Erro no banco"));

        assertThrows(UsuarioGatewayException.class, () -> usuarioGateway.salvar(usuario));
    }

    @Test
    void buscarPorId_DeveRetornarOptionalUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioGateway.buscarPorId("123");

        assertTrue(result.isPresent());
        assertEquals(usuario, result.get());
    }

    @Test
    void listar_DeveRetornarListaDeUsuarios() {
        List<Usuario> usuarios = List.of(new Usuario(), new Usuario());
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioGateway.listar();

        assertEquals(2, result.size());
    }

    @Test
    void excluir_DeveExcluirUsuarioComSucesso() {
        assertDoesNotThrow(() -> usuarioGateway.excluir("123"));
        verify(usuarioRepository).deleteById("123");
    }

    @Test
    void excluir_DeveLancarUsuarioGatewayException_SeErro() {
        doThrow(new RuntimeException("Erro")).when(usuarioRepository).deleteById("123");

        assertThrows(UsuarioGatewayException.class, () -> usuarioGateway.excluir("123"));
    }

    @Test
    void buscarPorLogin_DeveRetornarUserDetails() {
        UserDetails userDetails = mock(UserDetails.class);
        when(usuarioRepository.findByLogin("admin")).thenReturn(userDetails);

        UserDetails result = usuarioGateway.buscarPorLogin("admin");

        assertEquals(userDetails, result);
    }

    @Test
    void buscarPorPapel_DeveRetornarUsuariosPorRolePaginado() {
        Usuario usuario = new Usuario();
        Page<Usuario> page = new PageImpl<>(List.of(usuario));

        when(usuarioRepository.findByRole(UserRole.MEDICO, PageRequest.of(0, 10))).thenReturn(page);

        Page<Usuario> result = usuarioGateway.buscarPorPapel(UserRole.MEDICO, 0, 10);

        assertEquals(1, result.getTotalElements());
    }
}
