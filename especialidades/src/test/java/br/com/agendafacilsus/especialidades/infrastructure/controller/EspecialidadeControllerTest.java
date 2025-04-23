package br.com.agendafacilsus.especialidades.infrastructure.controller;

import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import br.com.agendafacilsus.especialidades.applicaton.usecase.EspecialidadeUseCase;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadeControllerTest {

    @InjectMocks
    private EspecialidadeController especialidadeController;

    @Mock
    private EspecialidadeUseCase especialidadeUseCase;

    @Mock
    private HttpServletRequest request;

    @Test
    void testCriarEspecialidade() {
        EspecialidadeRequestDTO dto = new EspecialidadeRequestDTO("Cardiologia", TipoEspecialidade.CONSULTA);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(1L, "Cardiologia", TipoEspecialidade.CONSULTA);

        when(especialidadeUseCase.criar(dto)).thenReturn(responseDTO);

        ResponseEntity<EspecialidadeResponseDTO> resultado = especialidadeController.criarEspecialidade(dto);

        assertEquals(200, resultado.getStatusCodeValue());
        assertNotNull(resultado.getBody());
        assertEquals(responseDTO, resultado.getBody());
        verify(especialidadeUseCase, times(1)).criar(dto);
    }

    @Test
    void testListarEspecialidades() {
        EspecialidadeResponseDTO dto1 = new EspecialidadeResponseDTO(1L, "Cardiologia", TipoEspecialidade.CONSULTA);
        EspecialidadeResponseDTO dto2 = new EspecialidadeResponseDTO(2L, "Dermatologia", TipoEspecialidade.EXAME);
        List<EspecialidadeResponseDTO> listaEspecialidades = Arrays.asList(dto1, dto2);

        when(especialidadeUseCase.listar()).thenReturn(listaEspecialidades);

        ResponseEntity<List<EspecialidadeResponseDTO>> resultado = especialidadeController.listarEspecialidades();

        assertEquals(200, resultado.getStatusCodeValue());
        assertNotNull(resultado.getBody());
        assertEquals(2, resultado.getBody().size());
        verify(especialidadeUseCase, times(1)).listar();
    }

    @Test
    void testBuscarEspecialidadePorId() {
        Long especialidadeId = 1L;
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidadeId, "Cardiologia", TipoEspecialidade.CONSULTA);

        when(especialidadeUseCase.buscarPorId(especialidadeId)).thenReturn(responseDTO);

        ResponseEntity<EspecialidadeResponseDTO> resultado = especialidadeController.buscarEspecialidadePorId(especialidadeId);

        assertEquals(200, resultado.getStatusCodeValue());
        assertNotNull(resultado.getBody());
        assertEquals(responseDTO, resultado.getBody());
        verify(especialidadeUseCase, times(1)).buscarPorId(especialidadeId);
    }

    @Test
    void testAtualizarEspecialidade() {
        Long especialidadeId = 1L;
        EspecialidadeRequestDTO dto = new EspecialidadeRequestDTO("Cardiologia Atualizada", TipoEspecialidade.EXAME);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidadeId, "Cardiologia Atualizada", TipoEspecialidade.EXAME);

        when(especialidadeUseCase.atualizar(especialidadeId, dto)).thenReturn(responseDTO);

        ResponseEntity<EspecialidadeResponseDTO> resultado = especialidadeController.atualizarEspecialidade(especialidadeId, dto);

        assertEquals(200, resultado.getStatusCodeValue());
        assertNotNull(resultado.getBody());
        assertEquals(responseDTO, resultado.getBody());
        verify(especialidadeUseCase, times(1)).atualizar(especialidadeId, dto);
    }

    @Test
    void testExcluirEspecialidade() {
        Long especialidadeId = 1L;

        doNothing().when(especialidadeUseCase).excluir(especialidadeId);

        ResponseEntity<Void> resultado = especialidadeController.excluirEspecialidade(especialidadeId);

        assertEquals(204, resultado.getStatusCodeValue());
        verify(especialidadeUseCase, times(1)).excluir(especialidadeId);
    }
}
