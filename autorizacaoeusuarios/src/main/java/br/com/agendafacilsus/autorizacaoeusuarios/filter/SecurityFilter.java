package br.com.agendafacilsus.autorizacaoeusuarios.filter;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.dtos.ErrorResponseDto;
import br.com.agendafacilsus.autorizacaoeusuarios.service.UserService;
import br.com.agendafacilsus.commonlibrary.domains.exceptions.ForbiddenException;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@NonNullApi
public class SecurityFilter extends OncePerRequestFilter {

    private static final String APPLICATION_JSON = "application/json";
    private final TokenService tokenService;
    private final UserService service;

    @Value("${api.security.token.secret}")
    private String secret;

    /*
    * This validation is different from the others because in this case we are
    * reaching also database instead only to check JWT.
    * */

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain
    ) throws IOException {

        try
        {
            var token = tokenService.recoverToken(request);

            if(token != null) {

                final var login = tokenService.tokenSubject(secret, token);
                final var user = service.findByLogin(login);
                final var claims = tokenService.extractClaims(secret, token);

                if (user == null || user.getAuthorities() == null) {
                    throw new ForbiddenException("Not Allowed.");
                }

                if (tokenService.isTokenExpired(claims)) {
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
        catch (ForbiddenException exception)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(new ErrorResponseDto(exception.getMessage(), HttpStatus.FORBIDDEN).toString());
        }
        catch (Throwable exception)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(new ErrorResponseDto("Unpredictable error happened.", HttpStatus.INTERNAL_SERVER_ERROR).toString());
        }
    }
}