package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.repository;

import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    boolean existsByLoginIgnoreCase(String login);
    UserDetails findByLogin(String login);
    Page<Usuario> findByRole(UserRole role, Pageable pageable);
}
