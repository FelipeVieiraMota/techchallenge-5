package br.com.agendafacilsus.agendamentos.infrastructure.repository;

import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {}
