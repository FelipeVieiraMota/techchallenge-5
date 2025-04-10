package br.com.agendafacilsus.exameseconsultas.infrastructure.repository;

import br.com.agendafacilsus.exameseconsultas.domain.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {}