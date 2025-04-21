package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.AgendamentoGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.HorarioDisponivelGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.mapper.AgendamentoMapper;
import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioNaoEncontradoException;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.UsuarioGateway;
import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import br.com.agendafacilsus.especialidades.exception.EspecialidadeNaoEncontradaException;
import br.com.agendafacilsus.especialidades.infrastructure.gateway.EspecialidadeGateway;
import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.infrastructure.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

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
    private final UsuarioGateway usuarioGateway;
    private final EspecialidadeGateway especialidadeGateway;
    private final HorarioDisponivelGateway horarioDisponivelGateway;
    DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("HH:mm");

    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        val paciente = buscarUsuario(dto.idPaciente());
        val medico = buscarUsuario(dto.idMedico());
        val especialidade = buscarEspecialidade(dto.idEspecialidade());
        val agendamento = toEntity(dto, paciente, medico, especialidade);

        agendamento.setStatus(StatusAgendamento.AGENDADO);

        val medicoId = String.valueOf(dto.idMedico());
        val data = dto.dataHora().toLocalDate();
        val hora = LocalTime.parse(dto.dataHora().toLocalTime().format(dataFormatada));

        horarioDisponivelGateway.marcarComoReservado(medicoId, data, hora);
        val agendamentoSalvo = agendamentoGateway.salvar(agendamento);
        enviarNotificacao(paciente.getNome(), "Seu agendamento foi criado para " + dto.dataHora() + ".");
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
        val paciente = buscarUsuario(dto.idPaciente());
        val especialidade = buscarEspecialidade(dto.idEspecialidade());

        agendamento.setPaciente(paciente);
        agendamento.setEspecialidade(especialidade);
        agendamento.setDataHora(dto.dataHora());

        val medicoId = String.valueOf(dto.idMedico());

        val dataDisponivel = agendamento.getDataHora().toLocalDate();
        val horaDisponivel = LocalTime.parse(agendamento.getDataHora().toLocalTime().format(dataFormatada));
        horarioDisponivelGateway.marcarComoDisponivel(medicoId, dataDisponivel, horaDisponivel);

        val dataReservado = dto.dataHora().toLocalDate();
        val horaReservado = dto.dataHora().toLocalTime();
        horarioDisponivelGateway.marcarComoReservado(medicoId, dataReservado, horaReservado);

        val agendamentoAtualizado = agendamentoGateway.salvar(agendamento);
        enviarNotificacao(paciente.getNome(), "Seu agendamento foi atualizado para " + dto.dataHora() + ".");
        return toResponseDTO(agendamentoAtualizado);
    }

    public void excluir(Long id) {
        val agendamento = buscarAgendamento(id);
        agendamentoGateway.excluir(id);

        val medicoId = String.valueOf(agendamento.getMedico());
        val dataDisponivel = agendamento.getDataHora().toLocalDate();
        val horaDisponivel = LocalTime.parse(agendamento.getDataHora().toLocalTime().format(dataFormatada));
        horarioDisponivelGateway.marcarComoDisponivel(medicoId, dataDisponivel, horaDisponivel);

        enviarNotificacao(agendamento.getPaciente().getNome(), "Seu agendamento foi cancelado.");
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

        enviarNotificacao(agendamento.getPaciente().getNome(), mensagem);
        return toResponseDTO(agendamentoAtualizado);
    }

    private Usuario buscarUsuario(String idPaciente) {
        return usuarioGateway.buscarPorId(idPaciente)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(idPaciente));
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
