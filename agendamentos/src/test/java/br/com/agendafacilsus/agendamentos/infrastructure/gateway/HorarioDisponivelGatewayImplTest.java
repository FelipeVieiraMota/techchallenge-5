package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoDisponivelException;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.repository.HorarioDisponivelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioDisponivelGatewayImplTest {

    @InjectMocks
    private HorarioDisponivelGatewayImpl horarioDisponivelGateway;

    @Mock
    private HorarioDisponivelRepository repository;

    private HorarioDisponivel horarioDisponivel;
    private LocalDate data;
    private LocalTime hora;
    private String medicoId;

    @BeforeEach
    public void setUp() {
        medicoId = "medico123";
        data = LocalDate.of(2025, 4, 13);
        hora = LocalTime.of(9, 0);
        horarioDisponivel = new HorarioDisponivel(null, medicoId, data, hora, false);
    }

    @Test
    public void testListarDisponiveisComHorarios() {
        when(repository.findByMedicoIdAndDataAndReservadoFalse(medicoId, data))
                .thenReturn(List.of(horarioDisponivel));

        List<HorarioDisponivelDTO> horarios = horarioDisponivelGateway.listarDisponiveis(medicoId, data);

        assertNotNull(horarios);
        assertEquals(1, horarios.size());
        assertEquals("09:00", horarios.get(0).horarios().get(0));
        verify(repository, times(1)).findByMedicoIdAndDataAndReservadoFalse(medicoId, data);
    }

    @Test
    public void testListarDisponiveisSemHorarios() {
        when(repository.findByMedicoIdAndDataAndReservadoFalse(medicoId, data))
                .thenReturn(List.of());

        assertThrows(HorarioNaoDisponivelException.class, () ->
                horarioDisponivelGateway.listarDisponiveis(medicoId, data)
        );
        verify(repository, times(1)).findByMedicoIdAndDataAndReservadoFalse(medicoId, data);
    }

    @Test
    public void testMarcarComoReservadoComSucesso() {
        when(repository.findByMedicoIdAndDataAndHora(medicoId, data, hora))
                .thenReturn(Optional.of(horarioDisponivel));

        horarioDisponivelGateway.marcarComoReservado(medicoId, data, hora);

        verify(repository, times(1)).save(horarioDisponivel);
    }

    @Test
    public void testMarcarComoReservadoHorarioNaoEncontrado() {
        when(repository.findByMedicoIdAndDataAndHora(medicoId, data, hora))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                horarioDisponivelGateway.marcarComoReservado(medicoId, data, hora)
        );
    }

    @Test
    public void testMarcarComoDisponivelComSucesso() {
        when(repository.findByMedicoIdAndDataAndHora(medicoId, data, hora))
                .thenReturn(Optional.of(horarioDisponivel));

        horarioDisponivelGateway.marcarComoDisponivel(medicoId, data, hora);

        verify(repository, times(1)).save(horarioDisponivel);
    }

    @Test
    public void testMarcarComoDisponivelHorarioNaoEncontrado() {
        when(repository.findByMedicoIdAndDataAndHora(medicoId, data, hora))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                horarioDisponivelGateway.marcarComoDisponivel(medicoId, data, hora)
        );
    }

    @Test
    public void testSalvarHorario() {
        horarioDisponivelGateway.salvarHorario(List.of(horarioDisponivel));

        verify(repository, times(1)).saveAll(List.of(horarioDisponivel));
    }

    @Test
    public void testExistsByMedicoIdAndDataAndHora() {
        when(repository.existsByMedicoIdAndDataAndHora(medicoId, data, hora)).thenReturn(true);

        boolean exists = horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(medicoId, data, hora);

        assertTrue(exists);
        verify(repository, times(1)).existsByMedicoIdAndDataAndHora(medicoId, data, hora);
    }

    @Test
    public void testExcluirComSucesso() {
        when(repository.findByMedicoIdAndDataAndHora(medicoId, data, hora))
                .thenReturn(Optional.of(horarioDisponivel));

        horarioDisponivelGateway.excluir(medicoId, data, hora);

        verify(repository, times(1)).delete(horarioDisponivel);
    }

    @Test
    public void testExcluirHorarioNaoEncontrado() {
        when(repository.findByMedicoIdAndDataAndHora(medicoId, data, hora))
                .thenReturn(Optional.empty());

        assertThrows(HorarioNaoEncontradoException.class, () ->
                horarioDisponivelGateway.excluir(medicoId, data, hora)
        );
    }
}
