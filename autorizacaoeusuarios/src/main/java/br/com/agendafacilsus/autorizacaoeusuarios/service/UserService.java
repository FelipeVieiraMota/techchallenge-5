package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.repository.UserRepository;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserDetails findByLogin(String username) {
        return repository.findByLogin(username);
    }

    public User save(User newUser) {
        return repository.save(newUser);
    }

    public void deleteUserById(String id) {
        repository.deleteById(id);
    }

    public Page<User> getAllUsers(int page, int size) {
        return repository.findByRoleNot(UserRole.ADMIN, PageRequest.of(page, size));
    }
}
