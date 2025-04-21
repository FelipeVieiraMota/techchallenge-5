package br.com.agendafacilsus.especialidades.infrastructure.repository;

import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    boolean existsByDescricaoIgnoreCase(String descricao);
}