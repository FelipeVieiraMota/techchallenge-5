package br.com.agendafacilsus.autorizacaoeusuarios.repository;


import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);

    User findUserById(String id);

    Page<User> findByRoleNot(UserRole role, Pageable pageable);

    Page<User> findByRole(UserRole roles, Pageable pageable);
}
