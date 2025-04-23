package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.HorarioDisponivelGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorarioDisponivelUseCaseTest {

    private HorarioDisponivelGateway horarioDisponivelGateway;
    private HorarioDisponivelUseCase useCase;

    @BeforeEach
    void setup() {
        horarioDisponivelGateway = mock(HorarioDisponivelGateway.class);
        useCase = new HorarioDisponivelUseCase(horarioDisponivelGateway);
    }

    @Test
    void deveCadastrarHorariosQuandoNaoExistem() {
        var dto = new HorarioDisponivelDTO("medico123", LocalDate.now(), List.of("08:00", "09:00"));

        when(horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(any(), any(), any())).thenReturn(false);

        useCase.cadastrarHorarios(dto);

        ArgumentCaptor<List<HorarioDisponivel>> captor = ArgumentCaptor.forClass(List.class);
        verify(horarioDisponivelGateway).salvarHorario(captor.capture());

        List<HorarioDisponivel> horariosSalvos = captor.getValue();
        assertEquals(2, horariosSalvos.size());
        assertEquals("medico123", horariosSalvos.get(0).getMedicoId());
        assertEquals(LocalTime.of(8, 0), horariosSalvos.get(0).getHora());
    }

    @Test
    void naoDeveCadastrarHorariosJaExistentes() {
        var dto = new HorarioDisponivelDTO("medico123", LocalDate.now(), List.of("08:00"));

        when(horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(any(), any(), any())).thenReturn(true);

        useCase.cadastrarHorarios(dto);

        verify(horarioDisponivelGateway, never()).salvarHorario(any());
    }

    @Test
    void deveListarHorarios() {
        var data = LocalDate.now();
        var esperado = List.of(new HorarioDisponivelDTO("medico123", data, List.of("08:00", "09:00")));

        when(horarioDisponivelGateway.listarDisponiveis("medico123", data)).thenReturn(esperado);

        var resultado = useCase.listarHorarios("medico123", data);

        assertEquals(esperado, resultado);
        verify(horarioDisponivelGateway).listarDisponiveis("medico123", data);
    }

    @Test
    void deveExcluirHorarioQuandoExistente() {
        var data = LocalDate.now();
        var hora = LocalTime.of(10, 0);

        when(horarioDisponivelGateway.existsByMedicoIdAndDataAndHora("medico123", data, hora)).thenReturn(true);

        useCase.excluirHorario("medico123", data, hora);

        verify(horarioDisponivelGateway).excluir("medico123", data, hora);
    }

    @Test
    void deveLancarExcecaoQuandoHorarioNaoEncontrado() {
        var data = LocalDate.now();
        var hora = LocalTime.of(10, 0);

        when(horarioDisponivelGateway.existsByMedicoIdAndDataAndHora("medico123", data, hora)).thenReturn(false);

        assertThrows(HorarioNaoEncontradoException.class, () ->
                useCase.excluirHorario("medico123", data, hora));
    }
}
