package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.HorarioDisponivelGateway;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioDisponivelUseCase {
    private final HorarioDisponivelGateway horarioDisponivelGateway;

    public void cadastrarHorarios(HorarioDisponivelDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<HorarioDisponivel> horarios = dto.horarios().stream()
                .map(hora -> {
                    val localTime = LocalTime.parse(hora, formatter);
                    val horarioExistente = horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(dto.medicoId(), dto.data(), localTime);

                    if (horarioExistente) {
                        return null; // Se o horário já existir, não cadastrar
                    }

                    // Se o horário não existir, cria o novo horário
                    val horario = new HorarioDisponivel();
                    horario.setMedicoId(dto.medicoId());
                    horario.setData(dto.data());
                    horario.setHora(localTime);
                    horario.setReservado(false);
                    return horario;
                })
                .filter(horario -> horario != null) // Filtra os valores nulos (horários que já estavam cadastrados)
                .toList();

        if (!horarios.isEmpty()) {
            horarioDisponivelGateway.salvarHorario(horarios);
        }
    }

    public List<HorarioDisponivelDTO> listarHorarios(String medicoId, LocalDate data) {
        return horarioDisponivelGateway.listarDisponiveis(medicoId, data);
    }

    public void excluirHorario(String medicoId, LocalDate data, LocalTime hora) {
        val horarioExistente = horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(medicoId, data, hora);

        if (!horarioExistente) {
            throw new HorarioNaoEncontradoException(medicoId, data, hora);
        }

        horarioDisponivelGateway.excluir(medicoId, data, hora);
    }

}
