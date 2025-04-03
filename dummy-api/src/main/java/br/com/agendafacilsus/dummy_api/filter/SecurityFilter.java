package br.com.agendafacilsus.dummy_api.filter;

import br.com.agendafacilsus.commonlibrary.service.TokenService;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@NonNullApi
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws IOException, ServletException {
        final var path = request.getRequestURI();
        if ("/dummy-api/ping".equals(path) || "/dummy-api/welcome".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        tokenService.checkTokenRoles(secret, request, response, filterChain);
    }
}