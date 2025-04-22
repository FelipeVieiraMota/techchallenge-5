package br.com.agendafacilsus.agendamentos.infrastructure.gateway;


import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoDisponivelException;
import br.com.agendafacilsus.agendamentos.exception.HorarioNaoEncontradoException;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.repository.HorarioDisponivelRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HorarioDisponivelGatewayImpl implements HorarioDisponivelGateway {

    private final HorarioDisponivelRepository repository;

    @Override
    public List<HorarioDisponivelDTO> listarDisponiveis(String medicoId, LocalDate data) {
        List<HorarioDisponivel> horarios = repository.findByMedicoIdAndDataAndReservadoFalse(medicoId, data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> horas = horarios.stream()
                .map(horario -> horario.getHora().format(formatter))
                .toList();

        if (horas.isEmpty()) {
            throw new HorarioNaoDisponivelException(data);
        }

        // Criando o DTO com os dados formatados
        val dto = new HorarioDisponivelDTO(medicoId, data, horas);
        return List.of(dto);
    }

    @Override
    public void marcarComoReservado(String medicoId, LocalDate data, LocalTime hora) {
        val horario = repository.findByMedicoIdAndDataAndHora(medicoId, data, hora)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado"));
        horario.setReservado(true);
        repository.save(horario);
    }

    @Override
    public void marcarComoDisponivel(String medicoId, LocalDate data, LocalTime hora) {
        val horario = repository.findByMedicoIdAndDataAndHora(medicoId, data, hora)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado"));
        horario.setReservado(false);
        repository.save(horario);
    }

    @Override
    public void salvarHorario(List<HorarioDisponivel> horarios) {
        repository.saveAll(horarios);
    }

    @Override
    public boolean existsByMedicoIdAndDataAndHora(String medicoId, LocalDate data, LocalTime hora) {
        return repository.existsByMedicoIdAndDataAndHora(medicoId, data, hora);
    }

    @Override
    public void excluir(String medicoId, LocalDate data, LocalTime hora) {
        val horario = repository.findByMedicoIdAndDataAndHora(medicoId, data, hora)
                .orElseThrow(() -> new HorarioNaoEncontradoException(medicoId, data, hora));
        repository.delete(horario);
    }
}
