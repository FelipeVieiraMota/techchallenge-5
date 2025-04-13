package br.com.agendafacilsus.especialidades.infrastructure.repository;

import br.com.agendafacilsus.especialidades.domain.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {}