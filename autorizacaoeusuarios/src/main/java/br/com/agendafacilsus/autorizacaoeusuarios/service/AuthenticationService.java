package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.IUserMapper;
import br.com.agendafacilsus.commonlibrary.domains.dtos.AuthenticationDto;
import br.com.agendafacilsus.commonlibrary.domains.dtos.TokenDto;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final IUserMapper mapper;

    @Value("${api.security.token.secret}")
    private String secret;

    public String login(AuthenticationDto data) {
        final var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        final var authenticate = this.authenticationManager.authenticate(usernamePassword);
        final var dto = mapper.toDto((User) authenticate.getPrincipal());
        return tokenService.generateToken(secret, dto);
    }

    public boolean validateToken(final TokenDto data) {
        return tokenService.validateToken(secret, data.token());
    }
}
