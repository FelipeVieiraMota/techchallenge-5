package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.commonlibrary.domains.exceptions.ForbiddenException;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PermissionsService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final TokenService tokenService;
    private final UserService service;

    public void checkPermissions (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        var token = tokenService.recoverToken(request);

        if(token != null){

            final var login = tokenService.tokenSubject(secret, token);
            final var user = service.findByLogin(login);

            if (user == null || user.getAuthorities() == null) {
                throw new ForbiddenException("Not Allowed.");
            }

            final var authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
