package br.com.agendafacilsus.exames.infrastructure.repository;

import br.com.agendafacilsus.exames.domain.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<Exame, Long> {}