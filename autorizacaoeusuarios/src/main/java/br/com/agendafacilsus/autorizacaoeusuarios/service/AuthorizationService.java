package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.controller.exceptions.UserAlreadyExistsException;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.mappers.IFetchMapper;
import br.com.agendafacilsus.commonlibrary.domains.dtos.FetchUserDto;
import br.com.agendafacilsus.commonlibrary.domains.dtos.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final IFetchMapper mapper;

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
        final User newUser = new User(data.name(), data.login(), encryptedPassword, data.role());
        return service.save(newUser);
    }

    public void deleteUserById(final String id) {
        service.deleteUserById(id);
    }

    public Page<FetchUserDto> getAllUsers(final int page, final int size) {
        return service.getAllUsers(page, size).map(mapper::toDto);
    }

    public Page<FetchUserDto> getAllPatients(final int page, final int size) {
        return service.getAllPatients(page, size).map(mapper::toDto);
    }

    public Page<FetchUserDto> getAllDoctors(final int page, final int size) {
        return service.getAllDoctors(page, size).map(mapper::toDto);
    }

    public FetchUserDto findUserById(final String id) {
        return mapper.toDto(service.findUserById(id));
    }
}
