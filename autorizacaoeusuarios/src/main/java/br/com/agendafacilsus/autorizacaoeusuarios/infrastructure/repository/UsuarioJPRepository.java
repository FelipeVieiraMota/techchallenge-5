package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.repository;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioJPRepository extends JpaRepository<User, String> {}
