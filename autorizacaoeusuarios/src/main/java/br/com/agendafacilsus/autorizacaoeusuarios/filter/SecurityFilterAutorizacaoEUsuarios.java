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
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws IOException {
        tokenService.checkTokenRoles(secret, request, response, filterChain);
    }
}