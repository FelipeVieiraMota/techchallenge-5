package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoDuplicadoException;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoGatewayException;
import br.com.agendafacilsus.agendamentos.infrastructure.repository.AgendamentoRepository;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoGatewayImplTest {

    @InjectMocks
    private AgendamentoGatewayImpl agendamentoGatewayImpl;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    private Agendamento agendamento;

    @BeforeEach
    public void setUp() {
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setUuidPaciente("uuid-paciente");
        agendamento.setNomePaciente("Paciente Teste");
        agendamento.setUuidMedico("uuid-medico");
        agendamento.setNomeMedico("Médico Teste");
        agendamento.setDescricaoEspecialidade("Cardiologia");
        agendamento.setTipoEspecialidade(TipoEspecialidade.CONSULTA);
        agendamento.setData(LocalDateTime.of(2025, 4, 13, 9, 0).toLocalDate());
        agendamento.setHora(LocalDateTime.of(2025, 4, 13, 9, 0).toLocalTime());
        agendamento.setStatus(StatusAgendamento.AGENDADO);
    }

    @Test
    public void testSalvarAgendamentoComSucesso() {
        when(agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                agendamento.getUuidPaciente(),
                agendamento.getData(),
                agendamento.getHora(),
                StatusAgendamento.CANCELADO))
                .thenReturn(false);
        when(agendamentoRepository.save(agendamento)).thenReturn(agendamento);

        Agendamento agendamentoSalvo = agendamentoGatewayImpl.salvar(agendamento);

        assertNotNull(agendamentoSalvo);
        assertEquals(agendamento, agendamentoSalvo);
        verify(agendamentoRepository, times(1)).save(agendamento);
    }

    @Test
    public void testSalvarAgendamentoDuplicado() {
        when(agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                agendamento.getUuidPaciente(),
                agendamento.getData(),
                agendamento.getHora(),
                StatusAgendamento.CANCELADO))
                .thenReturn(true);

        assertThrows(AgendamentoDuplicadoException.class, () -> agendamentoGatewayImpl.salvar(agendamento));

        verify(agendamentoRepository, never()).save(agendamento);
    }

    @Test
    public void testSalvarAgendamentoErro() {
        when(agendamentoRepository.existsByUuidPacienteAndDataAndHoraAndStatusNot(
                agendamento.getUuidPaciente(),
                agendamento.getData(),
                agendamento.getHora(),
                StatusAgendamento.CANCELADO))
                .thenReturn(false);
        when(agendamentoRepository.save(agendamento)).thenThrow(new RuntimeException("Erro inesperado"));

        assertThrows(AgendamentoGatewayException.class, () -> agendamentoGatewayImpl.salvar(agendamento));
    }

    @Test
    public void testBuscarPorId() {
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));

        Optional<Agendamento> agendamentoEncontrado = agendamentoGatewayImpl.buscarPorId(1L);

        assertTrue(agendamentoEncontrado.isPresent());
        assertEquals(agendamento, agendamentoEncontrado.get());
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Agendamento> agendamentoEncontrado = agendamentoGatewayImpl.buscarPorId(1L);

        assertFalse(agendamentoEncontrado.isPresent());
    }

    @Test
    public void testListarAgendamentos() {
        when(agendamentoRepository.findAll()).thenReturn(List.of(agendamento));

        List<Agendamento> agendamentos = agendamentoGatewayImpl.listar();

        assertNotNull(agendamentos);
        assertFalse(agendamentos.isEmpty());
        assertEquals(1, agendamentos.size());
        assertEquals(agendamento, agendamentos.get(0));
    }

    @Test
    public void testExcluirAgendamentoComSucesso() {
        doNothing().when(agendamentoRepository).deleteById(1L);

        agendamentoGatewayImpl.excluir(1L);

        verify(agendamentoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testExcluirAgendamentoErro() {
        doThrow(new RuntimeException("Erro inesperado")).when(agendamentoRepository).deleteById(1L);

        assertThrows(AgendamentoGatewayException.class, () -> agendamentoGatewayImpl.excluir(1L));
    }
}
