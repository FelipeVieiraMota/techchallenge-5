package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.controller.exceptions.UserAlreadyExistsException;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return service.findByLogin(username);
    }

    public User register(@RequestBody @Valid RegisterDto data) {

        final var user = service.findByLogin(data.login());

        if( user != null ) {
            throw new UserAlreadyExistsException();
        }

        final String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        final User newUser = new User(data.login(), encryptedPassword, data.role());
        return service.save(newUser);
    }
}
