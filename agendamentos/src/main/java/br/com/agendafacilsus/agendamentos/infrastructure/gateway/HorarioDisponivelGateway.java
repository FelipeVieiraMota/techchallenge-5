package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HorarioDisponivelGateway {

    List<HorarioDisponivelDTO> listarDisponiveis(String medicoId, LocalDate data);

    void marcarComoReservado(String medicoId, LocalDate data, LocalTime hora);

    void marcarComoDisponivel(String medicoId, LocalDate data, LocalTime hora);


    void saveAll(List<HorarioDisponivel> horarios);

    boolean existsByMedicoIdAndDataAndHora(String medicoId, LocalDate data, LocalTime hora);

}
