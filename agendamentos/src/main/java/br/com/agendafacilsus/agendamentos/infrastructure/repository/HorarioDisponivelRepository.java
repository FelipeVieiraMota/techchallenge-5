package br.com.agendafacilsus.agendamentos.infrastructure.repository;

import br.com.agendafacilsus.agendamentos.domain.model.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findByMedicoIdAndDataAndReservadoFalse(String medicoId, LocalDate data);
    Optional<HorarioDisponivel> findByMedicoIdAndDataAndHora(String medicoId, LocalDate data, LocalTime hora);
    boolean existsByMedicoIdAndDataAndHora(String medicoId, LocalDate data, LocalTime hora);
}
