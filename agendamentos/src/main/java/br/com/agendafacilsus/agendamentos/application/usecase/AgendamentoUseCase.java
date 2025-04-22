package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoDisponivelException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.AgendamentoGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.EspecialidadeRestGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.HorarioDisponivelGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.UsuarioRestGateway;
import br.com.agendafacilsus.agendamentos.infrastructure.mapper.AgendamentoMapper;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.exception.EspecialidadeNaoEncontradaException;
import br.com.agendafacilsus.commonlibrary.domain.exception.UsuarioNaoEncontradoException;
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
    private final UsuarioRestGateway usuarioRestGateway;
    private final EspecialidadeRestGateway especialidadeRestGateway;
    private final HorarioDisponivelGateway horarioDisponivelGateway;

    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto, String tokenJWT) {
        val paciente = buscarUsuario(dto.idPaciente(), tokenJWT);
        val medico = buscarUsuario(dto.idMedico(), tokenJWT);
        val especialidade = buscarEspecialidade(dto.idEspecialidade(), tokenJWT);
        val horaConvertida = LocalTime.parse(dto.hora(), DateTimeFormatter.ofPattern("HH:mm"));

        boolean horarioDisponivel = !horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(medico.id(), dto.data(), horaConvertida);
        if (!horarioDisponivel) {
            throw new HorarioNaoDisponivelException(dto.data());
        }

        val agendamento = toEntity(dto, paciente, medico, especialidade);
        agendamento.setStatus(StatusAgendamento.AGENDADO);
        horarioDisponivelGateway.marcarComoReservado(medico.id(), dto.data(), horaConvertida);
        val agendamentoSalvo = agendamentoGateway.salvar(agendamento);
        enviarNotificacao(
                paciente.nome(),
                String.format(
                        "Seu agendamento com o Dr. %s foi criado para o dia %s às %s.",
                        medico.nome(),
                        dto.data().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        horaConvertida.format(DateTimeFormatter.ofPattern("HH:mm"))
                )
        );
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

    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto, String tokenJWT) {
        val agendamento = buscarAgendamento(id);
        val paciente = buscarUsuario(dto.idPaciente(), tokenJWT);
        val especialidade = buscarEspecialidade(dto.idEspecialidade(), tokenJWT);
        val horaConvertida = LocalTime.parse(dto.hora(), DateTimeFormatter.ofPattern("HH:mm"));

        agendamento.setUuidPaciente(paciente.id());
        agendamento.setNomePaciente(paciente.nome());
        agendamento.setDescricaoEspecialidade(especialidade.descricao());
        agendamento.setTipoEspecialidade(especialidade.especialidade());
        agendamento.setData(dto.data());
        agendamento.setHora(horaConvertida);

        horarioDisponivelGateway.marcarComoDisponivel(dto.idMedico(), agendamento.getData(), agendamento.getHora());
        horarioDisponivelGateway.marcarComoReservado(dto.idMedico(), dto.data(), horaConvertida);

        val agendamentoAtualizado = agendamentoGateway.salvar(agendamento);
        val medico = buscarUsuario(dto.idMedico(), tokenJWT);

        enviarNotificacao(
                paciente.nome(),
                String.format(
                        "Seu agendamento com o Dr. %s foi atualizado para o dia %s às %s.",
                        medico.nome(),
                        dto.data().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        horaConvertida.format(DateTimeFormatter.ofPattern("HH:mm"))
                )
        );
        return toResponseDTO(agendamentoAtualizado);
    }

    public void excluir(Long id) {
        val agendamento = buscarAgendamento(id);
        agendamentoGateway.excluir(id);
        horarioDisponivelGateway.marcarComoDisponivel(agendamento.getUuidMedico(), agendamento.getData(), agendamento.getHora());
        enviarNotificacao(agendamento.getNomePaciente(), "Seu agendamento foi cancelado.");
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

        enviarNotificacao(agendamento.getNomePaciente(), mensagem);
        return toResponseDTO(agendamentoAtualizado);
    }

    public UsuarioResponseDTO buscarUsuario(String idPaciente, String tokenJWT) {
        return usuarioRestGateway.buscarPorId(idPaciente, tokenJWT)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(idPaciente));
    }

    public EspecialidadeResponseDTO buscarEspecialidade(Long idEspecialidade, String tokenJWT) {
        return especialidadeRestGateway.buscarPorId(idEspecialidade, tokenJWT)
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
