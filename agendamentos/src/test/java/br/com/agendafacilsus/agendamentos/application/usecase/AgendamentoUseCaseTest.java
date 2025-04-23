package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoDisponivelException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.AgendamentoGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.EspecialidadeRestGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.HorarioDisponivelGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.UsuarioRestGateway;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.notificacoes.infrastructure.gateway.NotificacaoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoUseCaseTest {

    private AgendamentoGateway agendamentoGateway;
    private NotificacaoGateway notificacaoGateway;
    private UsuarioRestGateway usuarioRestGateway;
    private EspecialidadeRestGateway especialidadeRestGateway;
    private HorarioDisponivelGateway horarioDisponivelGateway;

    private AgendamentoUseCase useCase;

    @BeforeEach
    void setup() {
        agendamentoGateway = mock(AgendamentoGateway.class);
        notificacaoGateway = mock(NotificacaoGateway.class);
        usuarioRestGateway = mock(UsuarioRestGateway.class);
        especialidadeRestGateway = mock(EspecialidadeRestGateway.class);
        horarioDisponivelGateway = mock(HorarioDisponivelGateway.class);

        useCase = new AgendamentoUseCase(
                agendamentoGateway,
                notificacaoGateway,
                usuarioRestGateway,
                especialidadeRestGateway,
                horarioDisponivelGateway
        );
    }

    @Test
    void deveCriarAgendamentoComSucesso() {
        var request = new AgendamentoRequestDTO("1", 3L, "1", LocalDate.now(), "14:00");

        var paciente = new UsuarioResponseDTO("1", "Paciente Teste", "paciente@login.com", UserRole.PACIENTE);
        var medico = new UsuarioResponseDTO("1", "Médico Teste", "medico@login.com", UserRole.MEDICO);
        var especialidade = new EspecialidadeResponseDTO(3L, "Cardiologia", TipoEspecialidade.CONSULTA);

        when(usuarioRestGateway.buscarPorId(eq("1"), any())).thenReturn(Optional.of(paciente));
        when(usuarioRestGateway.buscarPorId(eq("2"), any())).thenReturn(Optional.of(medico));
        when(especialidadeRestGateway.buscarPorId(eq(3L), any())).thenReturn(Optional.of(especialidade));
        when(horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(any(), any(), any())).thenReturn(false);

        var agendamento = new Agendamento();
        when(agendamentoGateway.salvar(any())).thenReturn(agendamento);

        var response = useCase.criar(request, "token");

        assertNotNull(response);
        verify(notificacaoGateway).enviar(any());
        verify(horarioDisponivelGateway).marcarComoReservado(eq("1"), eq(request.data()), eq(LocalTime.parse("14:00")));
    }

    @Test
    void deveLancarExcecaoQuandoHorarioNaoDisponivel() {
        var request = new AgendamentoRequestDTO("1", 3L, "1", LocalDate.now(), "14:00");

        when(usuarioRestGateway.buscarPorId(any(), any())).thenReturn(Optional.of(new UsuarioResponseDTO("1", "Paciente", "email", UserRole.PACIENTE)));
        when(especialidadeRestGateway.buscarPorId(any(), any())).thenReturn(Optional.of(new EspecialidadeResponseDTO(3L, "desc", TipoEspecialidade.CONSULTA)));
        when(horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(any(), any(), any())).thenReturn(true);

        assertThrows(HorarioNaoDisponivelException.class, () -> useCase.criar(request, "token"));
    }

    @Test
    void deveListarAgendamentos() {
        var agendamento = new Agendamento();
        when(agendamentoGateway.listar()).thenReturn(List.of(agendamento));

        var response = useCase.listar();

        assertEquals(1, response.size());
    }

    @Test
    void deveBuscarAgendamentoPorId() {
        var agendamento = new Agendamento();
        when(agendamentoGateway.buscarPorId(1L)).thenReturn(Optional.of(agendamento));

        var response = useCase.buscarPorId(1L);

        assertNotNull(response);
    }

    @Test
    void deveLancarExcecaoQuandoAgendamentoNaoEncontrado() {
        when(agendamentoGateway.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(AgendamentoNaoEncontradoException.class, () -> useCase.buscarPorId(1L));
    }

    @Test
    void deveAlterarStatusAgendamento() {
        var agendamento = new Agendamento();
        agendamento.setNomePaciente("Paciente");

        when(agendamentoGateway.buscarPorId(1L)).thenReturn(Optional.of(agendamento));
        when(agendamentoGateway.salvar(any())).thenReturn(agendamento);

        var response = useCase.alterarStatus(1L, StatusAgendamento.REALIZADO);

        assertEquals(StatusAgendamento.REALIZADO, agendamento.getStatus());
        verify(notificacaoGateway).enviar(any());
    }

    @Test
    void deveExcluirAgendamento() {
        var agendamento = new Agendamento();
        agendamento.setUuidMedico("medico-id");
        agendamento.setNomePaciente("Paciente");
        agendamento.setData(LocalDate.now());
        agendamento.setHora(LocalTime.NOON);

        when(agendamentoGateway.buscarPorId(1L)).thenReturn(Optional.of(agendamento));

        useCase.excluir(1L);

        verify(agendamentoGateway).excluir(1L);
        verify(horarioDisponivelGateway).marcarComoDisponivel("medico-id", agendamento.getData(), agendamento.getHora());
        verify(notificacaoGateway).enviar(any());
    }
}
