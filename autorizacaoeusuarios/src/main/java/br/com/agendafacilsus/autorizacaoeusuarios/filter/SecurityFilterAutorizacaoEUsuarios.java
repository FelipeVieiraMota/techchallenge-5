package br.com.agendafacilsus.autorizacaoeusuarios.filter;

import br.com.agendafacilsus.autorizacaoeusuarios.application.usecase.UsuarioUseCase;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.ErrorResponseDto;
import br.com.agendafacilsus.commonlibrary.domain.exception.ForbiddenException;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
public class SecurityFilterAutorizacaoEUsuarios extends OncePerRequestFilter {

    private static final String APPLICATION_JSON = "application/json";
    private final TokenService tokenService;
    private final UsuarioUseCase usuarioUseCase;

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {

        try {
            val token = tokenService.recoverToken(request);

            if (token != null) {
                val login = tokenService.tokenSubject(secret, token);
                val usuario = usuarioUseCase.buscarPorLogin(login);
                val claims = tokenService.extractClaims(secret, token);

                if (usuario == null || usuario.getAuthorities() == null) {
                    throw new ForbiddenException("Sem permissão.");
                }

                if (tokenService.isTokenExpired(claims)) {
                    throw new ForbiddenException("Sem permissão.");
                }

                val autenticacao = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }

            filterChain.doFilter(request, response);
        } catch (ForbiddenException exception) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(new ErrorResponseDto(exception.getMessage(), HttpStatus.FORBIDDEN).toString());
        } catch (Throwable exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(new ErrorResponseDto("Ocorreu um erro imprevisível.", HttpStatus.INTERNAL_SERVER_ERROR).toString());
        }
    }
}