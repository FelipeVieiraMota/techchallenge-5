package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.AgendamentoUseCase;
import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AlteracaoStatusRequestDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoControllerTest {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private AgendamentoUseCase agendamentoUseCase;

    @Mock
    private HttpServletRequest request;

    @Test
    public void testCriarAgendamento() {
        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(
                "12345", 1L, "1", LocalDate.of(2025, 4, 13), "08:00");
        AgendamentoResponseDTO responseDTO = new AgendamentoResponseDTO(
                1L, "Paciente Teste", "Médico Teste", "Especialidade Teste", null, LocalDate.of(2025, 4, 13), LocalTime.of(8, 0), null);
        String tokenJWT = "mock-jwt-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenJWT);
        when(agendamentoUseCase.criar(dto, tokenJWT)).thenReturn(responseDTO);

        AgendamentoResponseDTO resultado = agendamentoController.criarAgendamento(dto, request);

        assertNotNull(resultado);
        assertEquals(responseDTO, resultado);
        verify(agendamentoUseCase, times(1)).criar(dto, tokenJWT);
    }

    @Test
    public void testAlterarStatusAgendamento() {
        AlteracaoStatusRequestDTO statusDTO = new AlteracaoStatusRequestDTO(StatusAgendamento.AGENDADO);
        AgendamentoResponseDTO responseDTO = new AgendamentoResponseDTO(
                1L, "Paciente Teste", "Médico Teste", "Especialidade Teste", null, LocalDate.of(2025, 4, 13), LocalTime.of(8, 0), null);
        Long idAgendamento = 1L;

        when(agendamentoUseCase.alterarStatus(idAgendamento, StatusAgendamento.AGENDADO)).thenReturn(responseDTO);

        AgendamentoResponseDTO resultado = agendamentoController.alterarStatusAgendamento(idAgendamento, statusDTO);

        assertNotNull(resultado);
        assertEquals(responseDTO, resultado);
        verify(agendamentoUseCase, times(1)).alterarStatus(idAgendamento, StatusAgendamento.AGENDADO);
    }

    @Test
    public void testBuscarEspecialidade() {
        Long especialidadeId = 1L;
        EspecialidadeResponseDTO especialidadeDTO = new EspecialidadeResponseDTO(especialidadeId, "Cardiologia", TipoEspecialidade.CONSULTA);

        String tokenJWT = "mock-jwt-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenJWT);
        when(agendamentoUseCase.buscarEspecialidade(especialidadeId, tokenJWT)).thenReturn(especialidadeDTO);

        EspecialidadeResponseDTO resultado = agendamentoController.getEspecialidade(especialidadeId, request);

        assertNotNull(resultado);
        assertEquals(especialidadeDTO, resultado);
        verify(agendamentoUseCase, times(1)).buscarEspecialidade(especialidadeId, tokenJWT);
    }

    @Test
    public void testBuscarUsuario() {
        String usuarioId = "user123";
        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO(usuarioId, "Joao", "joao", UserRole.PACIENTE);

        String tokenJWT = "mock-jwt-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenJWT);
        when(agendamentoUseCase.buscarUsuario(usuarioId, tokenJWT)).thenReturn(usuarioDTO);

        UsuarioResponseDTO resultado = agendamentoController.getUsuario(usuarioId, request);

        assertNotNull(resultado);
        assertEquals(usuarioDTO, resultado);
        verify(agendamentoUseCase, times(1)).buscarUsuario(usuarioId, tokenJWT);
    }
}
