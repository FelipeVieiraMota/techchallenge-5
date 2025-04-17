package br.com.agendafacilsus.agendamentos.application.usecase;

import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.gateway.HorarioDisponivelGateway;
import lombok.RequiredArgsConstructor;
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
                    LocalTime localTime = LocalTime.parse(hora, formatter);

                    // Verificar se o horário já está cadastrado
                    boolean horarioExistente = horarioDisponivelGateway.existsByMedicoIdAndDataAndHora(dto.medicoId(), dto.data(), localTime);

                    if (horarioExistente) {
                        // Se o horário já existir, não cadastrar
                        return null;
                    }

                    // Se o horário não existir, cria o novo horário
                    HorarioDisponivel horario = new HorarioDisponivel();
                    horario.setMedicoId(dto.medicoId());
                    horario.setData(dto.data());
                    horario.setHora(localTime);
                    horario.setReservado(false);
                    return horario;
                })
                .filter(horario -> horario != null) // Filtra os valores nulos (horários que já estavam cadastrados)
                .toList();

        if (!horarios.isEmpty()) {
            horarioDisponivelGateway.saveAll(horarios); // Salva os novos horários
        }
    }

    public List<HorarioDisponivelDTO> listarHorarios(String medicoId, LocalDate data) {
        return horarioDisponivelGateway.listarDisponiveis(medicoId, data);
    }
}
