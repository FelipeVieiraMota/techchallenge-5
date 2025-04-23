package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.controller;

import br.com.agendafacilsus.autorizacaoeusuarios.application.usecase.UsuarioUseCase;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.ListaUsuariosResponseDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioRequestDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    private UsuarioUseCase usuarioUseCase;
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioUseCase = mock(UsuarioUseCase.class);
        usuarioController = new UsuarioController(usuarioUseCase);
    }

    @Test
    void criarUsuario_deveRetornarUsuarioResponseDTO() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("João da Silva", "admin", "test123", UserRole.ADMIN);
        UsuarioResponseDTO response = new UsuarioResponseDTO("1", "João da Silva", "admin", UserRole.ADMIN);

        when(usuarioUseCase.criar(request)).thenReturn(response);

        ResponseEntity<UsuarioResponseDTO> result = usuarioController.criarUsuario(request);

        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void atualizarUsuario_deveRetornarUsuarioAtualizado() {
        String id = "123";
        UsuarioRequestDTO request = new UsuarioRequestDTO("Maria", "maria", "senha123", UserRole.MEDICO);
        UsuarioResponseDTO response = new UsuarioResponseDTO(id, "Maria", "maria", UserRole.MEDICO);

        when(usuarioUseCase.atualizar(id, request)).thenReturn(response);

        ResponseEntity<UsuarioResponseDTO> result = usuarioController.atualizarUsuario(id, request);

        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void deletarUsuario_deveRetornarNoContent() {
        String id = "123";

        ResponseEntity<Void> result = usuarioController.deletarUsuario(id);

        verify(usuarioUseCase, times(1)).excluir(id);
        assertEquals(204, result.getStatusCodeValue());
    }

    @Test
    void listarTodosUsuarios_deveRetornarListaDeUsuarios() {
        List<UsuarioResponseDTO> usuarios = List.of(
                new UsuarioResponseDTO("1", "João", "joao", UserRole.ADMIN),
                new UsuarioResponseDTO("2", "Maria", "maria", UserRole.MEDICO)
        );

        when(usuarioUseCase.listar()).thenReturn(usuarios);

        ResponseEntity<List<UsuarioResponseDTO>> result = usuarioController.listarTodosUsuarios();

        assertEquals(usuarios, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void listarTodosPacientes_deveRetornarListaDePacientes() {
        List<UsuarioResponseDTO> pacientes = List.of(
                new UsuarioResponseDTO("3", "Carlos", "carlos", UserRole.PACIENTE)
        );
        Page<UsuarioResponseDTO> page = new PageImpl<>(pacientes);

        when(usuarioUseCase.buscarPorPapel(UserRole.PACIENTE, 0, 10)).thenReturn(page);

        ResponseEntity<ListaUsuariosResponseDTO> result = usuarioController.listarTodosPacientes(0, 10);

        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().total());
        assertEquals(pacientes, result.getBody().usuarios());
    }

    @Test
    void listarTodosMedicos_deveRetornarListaDeMedicos() {
        List<UsuarioResponseDTO> medicos = List.of(
                new UsuarioResponseDTO("4", "Dr. House", "house", UserRole.MEDICO)
        );
        Page<UsuarioResponseDTO> page = new PageImpl<>(medicos);

        when(usuarioUseCase.buscarPorPapel(UserRole.MEDICO, 0, 10)).thenReturn(page);

        ResponseEntity<ListaUsuariosResponseDTO> result = usuarioController.listarTodosMedicos(0, 10);

        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().total());
        assertEquals(medicos, result.getBody().usuarios());
    }

    @Test
    void buscarUsuarioPorId_deveRetornarUsuario() {
        String id = "1";
        UsuarioResponseDTO response = new UsuarioResponseDTO("1", "João", "joao", UserRole.ADMIN);

        when(usuarioUseCase.buscarPorId(id)).thenReturn(response);

        ResponseEntity<UsuarioResponseDTO> result = usuarioController.buscarUsuarioPorId(id);

        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }
}
