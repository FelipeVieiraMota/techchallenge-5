package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserDetails findByLogin(String username) {
        return repository.findByLogin(username);
    }
}
