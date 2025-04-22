package br.com.agendafacilsus.agendamentos.infrastructure.repository;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByUuidPacienteAndDataAndHoraAndStatusNot(String uuidPaciente, LocalDate data, LocalTime hora, StatusAgendamento status);
}
