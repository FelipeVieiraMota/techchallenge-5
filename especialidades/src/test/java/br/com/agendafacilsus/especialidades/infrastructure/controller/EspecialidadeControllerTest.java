package br.com.agendafacilsus.especialidades.infrastructure.controller;

import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import br.com.agendafacilsus.especialidades.applicaton.usecase.EspecialidadeUseCase;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeResponseDTO;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EspecialidadeControllerTest {

    private EspecialidadeUseCase especialidadeUseCase;
    private EspecialidadeController especialidadeController;

    @BeforeEach
    void setUp() {
        especialidadeUseCase = mock(EspecialidadeUseCase.class);
        especialidadeController = new EspecialidadeController(especialidadeUseCase);
    }

    @Test
    void deveCriarEspecialidadeComSucesso() {
        val requestDTO = new EspecialidadeRequestDTO("Cardiologia", TipoEspecialidade.CONSULTA);
        val responseDTO = new EspecialidadeResponseDTO(1L, "Cardiologia", TipoEspecialidade.CONSULTA);
        when(especialidadeUseCase.criar(requestDTO)).thenReturn(responseDTO);

        val resposta = especialidadeController.criarEspecialidade(requestDTO);

        assertEquals(HttpStatus.OK.value(), resposta.getStatusCode().value());
        assertEquals(responseDTO, resposta.getBody());
    }

    @Test
    void deveRetornarErroAoCriarEspecialidadeComDadosInvalidos() {
        val requestDTO = new EspecialidadeRequestDTO("", TipoEspecialidade.CONSULTA);
        when(especialidadeUseCase.criar(requestDTO)).thenThrow(new IllegalArgumentException("Nome da especialidade não pode ser vazio"));

        val resposta = especialidadeController.criarEspecialidade(requestDTO);

        assertEquals(HttpStatus.BAD_REQUEST.value(), resposta.getStatusCode().value());
        assertNull(resposta.getBody());
    }

    @Test
    void deveListarTodasEspecialidadesComSucesso() {
        val responseDTO = new EspecialidadeResponseDTO(1L, "Cardiologia", TipoEspecialidade.CONSULTA);
        when(especialidadeUseCase.listar()).thenReturn(Collections.singletonList(responseDTO));

        val resposta = especialidadeController.listarEspecialidades();

        assertEquals(HttpStatus.OK.value(), resposta.getStatusCode().value());

        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().contains(responseDTO));
    }


    @Test
    void deveRetornarErroAoListarEspecialidadesQuandoNaoExistem() {
        when(especialidadeUseCase.listar()).thenReturn(Collections.emptyList());

        val resposta = especialidadeController.listarEspecialidades();

        assertEquals(HttpStatus.NO_CONTENT.value(), resposta.getStatusCode().value());

        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }

    @Test
    void deveBuscarEspecialidadePorIdComSucesso() {
        val id = 1L;
        val responseDTO = new EspecialidadeResponseDTO(id, "Cardiologia", TipoEspecialidade.CONSULTA);
        when(especialidadeUseCase.buscarPorId(id)).thenReturn(responseDTO);

        val resposta = especialidadeController.buscarEspecialidadePorId(id);

        assertEquals(HttpStatus.OK.value(), resposta.getStatusCode().value());
        assertEquals(responseDTO, resposta.getBody());
    }

    @Test
    void deveRetornarErroAoBuscarEspecialidadePorIdQuandoNaoEncontrada() {
        val id = 1L;
        when(especialidadeUseCase.buscarPorId(id)).thenReturn(null);

        val resposta = especialidadeController.buscarEspecialidadePorId(id);

        assertEquals(HttpStatus.NOT_FOUND.value(), resposta.getStatusCode().value());
        assertNull(resposta.getBody());
    }

    @Test
    void deveAtualizarEspecialidadeComSucesso() {
        val id = 1L;
        val requestDTO = new EspecialidadeRequestDTO("Cardiologia", TipoEspecialidade.CONSULTA);
        val responseDTO = new EspecialidadeResponseDTO(id, "Cardiologia", TipoEspecialidade.CONSULTA);
        when(especialidadeUseCase.atualizar(id, requestDTO)).thenReturn(responseDTO);

        val resposta = especialidadeController.atualizarEspecialidade(id, requestDTO);

        assertEquals(HttpStatus.OK.value(), resposta.getStatusCode().value());
        assertEquals(responseDTO, resposta.getBody());
    }

    @Test
    void deveRetornarErroAoAtualizarEspecialidadeComIdInexistente() {
        val id = 999L;
        val requestDTO = new EspecialidadeRequestDTO("Cardiologia", TipoEspecialidade.CONSULTA);
        when(especialidadeUseCase.atualizar(id, requestDTO)).thenThrow(new IllegalArgumentException("Especialidade não encontrada"));

        val resposta = especialidadeController.atualizarEspecialidade(id, requestDTO);

        assertEquals(HttpStatus.NOT_FOUND.value(), resposta.getStatusCode().value());
        assertNull(resposta.getBody());
    }

    @Test
    void deveExcluirEspecialidadeComSucesso() {
        val id = 1L;
        doNothing().when(especialidadeUseCase).excluir(id);

        val resposta = especialidadeController.excluirEspecialidade(id);

        assertEquals(HttpStatus.NO_CONTENT.value(), resposta.getStatusCode().value());
        verify(especialidadeUseCase, times(1)).excluir(id);
    }

    @Test
    void deveRetornarErroAoExcluirEspecialidadeComIdInexistente() {
        val id = 999L;
        doThrow(new IllegalArgumentException("Especialidade não encontrada")).when(especialidadeUseCase).excluir(id);

        val resposta = especialidadeController.excluirEspecialidade(id);

        assertEquals(HttpStatus.NOT_FOUND.value(), resposta.getStatusCode().value());
    }

    @Test
    void deveRetornarErroInternoAoCriarEspecialidadeQuandoFalhaDeServico() {
        val requestDTO = new EspecialidadeRequestDTO("Cardiologia", TipoEspecialidade.CONSULTA);
        when(especialidadeUseCase.criar(requestDTO)).thenThrow(new RuntimeException("Erro interno no servidor"));

        val resposta = especialidadeController.criarEspecialidade(requestDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resposta.getStatusCode().value());
        assertNull(resposta.getBody());
    }
}
