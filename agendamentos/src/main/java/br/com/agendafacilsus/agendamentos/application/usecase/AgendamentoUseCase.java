package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.AgendamentoGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.HorarioDisponivelGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.mapper.AgendamentoMapper;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioJPNaoEncontradoException;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.user.UsuarioJPGateway;
import br.com.agendafacilsus.especialidades.domain.model.Especialidade;
import br.com.agendafacilsus.especialidades.exception.EspecialidadeNaoEncontradaException;
import br.com.agendafacilsus.especialidades.infrastructure.gateway.EspecialidadeGateway;
import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.infrastructure.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.agendafacilsus.agendamentos.infrastructure.mapper.AgendamentoMapper.toEntity;
import static br.com.agendafacilsus.agendamentos.infrastructure.mapper.AgendamentoMapper.toResponseDTO;

@Service
@RequiredArgsConstructor
public class AgendamentoUseCase {

    private final AgendamentoGateway agendamentoGateway;
    private final NotificacaoGateway notificacaoGateway;
    private final UsuarioJPGateway usuarioGateway;
    private final EspecialidadeGateway especialidadeGateway;
    private final HorarioDisponivelGateway horarioDisponivelGateway;
    DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("HH:mm");

    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        val paciente = buscarPaciente(dto.idPaciente());
        val especialidade = buscarEspecialidade(dto.idEspecialidade());
        val agendamento = toEntity(dto, paciente, especialidade);

        agendamento.setStatus(StatusAgendamento.AGENDADO);

        String medicoId = String.valueOf(dto.idMedico());
        LocalDate data = dto.dataHora().toLocalDate();
        LocalTime hora = LocalTime.parse(dto.dataHora().toLocalTime().format(dataFormatada));

        horarioDisponivelGateway.marcarComoReservado(medicoId, data, hora);

        enviarNotificacao(paciente.getName(), "Seu agendamento foi criado para " + dto.dataHora() + ".");


        val agendamentoSalvo = agendamentoGateway.salvar(agendamento);
        enviarNotificacao(paciente.getName(), "Seu agendamento foi criado para " + dto.dataHora() + ".");
        return toResponseDTO(agendamentoSalvo);
    }

    public List<AgendamentoResponseDTO> listar() {
        return agendamentoGateway.listar().stream()
                .map(AgendamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        return agendamentoGateway.buscarPorId(id)
                .map(AgendamentoMapper::toResponseDTO)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException(id));
    }

    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        val agendamento = buscarAgendamento(id);
        val paciente = buscarPaciente(dto.idPaciente());
        val especialidade = buscarEspecialidade(dto.idEspecialidade());

        agendamento.setPaciente(paciente);
        agendamento.setEspecialidade(especialidade);
        agendamento.setDataHora(dto.dataHora());

        String medicoId = String.valueOf(dto.idMedico());

        LocalDate dataDisponivel = agendamento.getDataHora().toLocalDate();
        LocalTime horaDisponivel = LocalTime.parse(agendamento.getDataHora().toLocalTime().format(dataFormatada));
        horarioDisponivelGateway.marcarComoDisponivel(medicoId, dataDisponivel, horaDisponivel);

        LocalDate dataReservado = dto.dataHora().toLocalDate();
        LocalTime horaReservado = dto.dataHora().toLocalTime();

        horarioDisponivelGateway.marcarComoReservado(medicoId, dataReservado, horaReservado);

        val agendamentoAtualizado = agendamentoGateway.salvar(agendamento);
        enviarNotificacao(paciente.getName(), "Seu agendamento foi atualizado para " + dto.dataHora() + ".");
        return toResponseDTO(agendamentoAtualizado);
    }

    public void excluir(Long id) {
        val agendamento = buscarAgendamento(id);
        agendamentoGateway.excluir(id);

        String medicoId = String.valueOf(agendamento.getMedico());
        LocalDate dataDisponivel = agendamento.getDataHora().toLocalDate();
        LocalTime horaDisponivel = LocalTime.parse(agendamento.getDataHora().toLocalTime().format(dataFormatada));
        horarioDisponivelGateway.marcarComoDisponivel(medicoId, dataDisponivel, horaDisponivel);

        enviarNotificacao(agendamento.getPaciente().getName(), "Seu agendamento foi cancelado.");
    }

    public AgendamentoResponseDTO alterarStatus(Long id, StatusAgendamento novoStatus) {
        val agendamento = buscarAgendamento(id);
        agendamento.setStatus(novoStatus);

        val agendamentoAtualizado = agendamentoGateway.salvar(agendamento);
        val mensagem = switch (novoStatus) {
            case REALIZADO -> "Seu agendamento foi marcado como realizado.";
            case CANCELADO -> "Seu agendamento foi cancelado.";
            default -> "O status do seu agendamento foi alterado para: " + novoStatus;
        };

        enviarNotificacao(agendamento.getPaciente().getName(), mensagem);
        return toResponseDTO(agendamentoAtualizado);
    }

    private User buscarPaciente(String idPaciente) {
        return usuarioGateway.buscarPorId(idPaciente)
                .orElseThrow(() -> new UsuarioJPNaoEncontradoException(idPaciente));
    }

    private Especialidade buscarEspecialidade(Long idEspecialidade) {
        return especialidadeGateway.buscarPorId(idEspecialidade)
                .orElseThrow(() -> new EspecialidadeNaoEncontradaException(idEspecialidade));
    }

    private Agendamento buscarAgendamento(Long idAgendamento) {
        return agendamentoGateway.buscarPorId(idAgendamento)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException(idAgendamento));
    }

    private void enviarNotificacao(String nomePaciente, String mensagem) {
        final var dto = new NotificacaoDTO(
                nomePaciente,
                "11970583685",
                mensagem,
                TipoNotificacao.SMS
        );

        notificacaoGateway.enviar(dto);
    }
}
