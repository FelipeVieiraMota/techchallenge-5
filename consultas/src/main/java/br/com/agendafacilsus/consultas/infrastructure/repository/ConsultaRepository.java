package br.com.agendafacilsus.consultas.infrastructure.repository;

import br.com.agendafacilsus.consultas.domain.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {}