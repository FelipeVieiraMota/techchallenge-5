package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.HorarioDisponivelUseCase;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioDisponivelControllerTest {

    @InjectMocks
    private HorarioDisponivelController horarioDisponivelController;

    @Mock
    private HorarioDisponivelUseCase horarioDisponivelUseCase;


    @Test
    public void testCadastrarHorarios() {
        HorarioDisponivelDTO dto = new HorarioDisponivelDTO("1", LocalDate.of(2025, 4, 13), List.of("08:00", "09:30"));

        doNothing().when(horarioDisponivelUseCase).cadastrarHorarios(dto);

        horarioDisponivelController.cadastrarHorarios(dto);

        verify(horarioDisponivelUseCase, times(1)).cadastrarHorarios(dto);
    }

    @Test
    public void testListarHorarios() {
        HorarioDisponivelDTO dto = new HorarioDisponivelDTO("1", LocalDate.of(2025, 4, 13), List.of("08:00", "09:30"));
        List<HorarioDisponivelDTO> horarios = List.of(dto);

        when(horarioDisponivelUseCase.listarHorarios("1", LocalDate.of(2025, 4, 13))).thenReturn(horarios);

        ResponseEntity<List<HorarioDisponivelDTO>> response = horarioDisponivelController.listarHorarios("1", "2025-04-13");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(horarios, response.getBody());
        verify(horarioDisponivelUseCase, times(1)).listarHorarios("1", LocalDate.of(2025, 4, 13));
    }

    @Test
    public void testExcluirHorario() {
        doNothing().when(horarioDisponivelUseCase).excluirHorario("1", LocalDate.of(2025, 4, 13), LocalTime.of(9, 0));

        horarioDisponivelController.excluirHorario("1", "2025-04-13", "09:00");

        verify(horarioDisponivelUseCase, times(1)).excluirHorario("1", LocalDate.of(2025, 4, 13), LocalTime.of(9, 0));
    }
}
