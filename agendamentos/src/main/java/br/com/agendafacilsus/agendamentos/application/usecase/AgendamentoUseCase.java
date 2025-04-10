package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.AgendamentoGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.mapper.AgendamentoMapper;
import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.agendafacilsus.agendamentos.infrastructure.mapper.AgendamentoMapper.*;

@Service
@RequiredArgsConstructor
public class AgendamentoUseCase {

    private final AgendamentoGateway agendamentoGateway;
    private final NotificacaoGateway notificacaoGateway;

    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        val agendamento = toEntity(dto);
        agendamento.setStatus(StatusAgendamento.AGENDADO);
        val salvo = agendamentoGateway.salvar(agendamento);

        enviarNotificacao(dto.nomePaciente(), "Seu agendamento foi criado para " + dto.dataHora() + ".");

        return toResponseDTO(salvo);
    }

    public List<AgendamentoResponseDTO> listar() {
        return agendamentoGateway.listarAgendamentos().stream()
                .map(AgendamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        return agendamentoGateway.buscarPorId(id)
                .map(AgendamentoMapper::toResponseDTO)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException(id));
    }

    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        agendamentoGateway.buscarPorId(id)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException(id));

        val agendamento = toEntity(dto);
        agendamento.setId(id);
        val atualizado = agendamentoGateway.salvar(agendamento);

        enviarNotificacao(dto.nomePaciente(), "Seu agendamento foi atualizado para " + dto.dataHora() + ".");

        return toResponseDTO(atualizado);
    }

    public void excluir(Long id) {
        val agendamento = agendamentoGateway.buscarPorId(id)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException(id));

        agendamentoGateway.excluir(id);

        enviarNotificacao(agendamento.getNomePaciente(), "Seu agendamento foi cancelado.");
    }

    public AgendamentoResponseDTO alterarStatus(Long id, StatusAgendamento novoStatus) {
        val agendamento = agendamentoGateway.buscarPorId(id)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException(id));

        agendamento.setStatus(novoStatus);
        val atualizado = agendamentoGateway.salvar(agendamento);

        val mensagem = switch (novoStatus) {
            case REALIZADO -> "Seu agendamento foi marcado como realizado.";
            case CANCELADO -> "Seu agendamento foi cancelado.";
            default -> "O status do seu agendamento foi alterado para: " + novoStatus;
        };

        enviarNotificacao(agendamento.getNomePaciente(), mensagem);

        return toResponseDTO(atualizado);
    }

    private void enviarNotificacao(String paciente, String mensagem) {
        final var dto = new NotificacaoDTO(
                paciente,
                "11970583685",
                mensagem,
                TipoNotificacao.SMS
        );

        notificacaoGateway.enviar(dto);
    }
}
