package br.com.agendafacilsus.dummy_api.filter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import br.com.agendafacilsus.dummy_api.util.JwtUtil;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@NonNullApi
public class SecurityFilter extends OncePerRequestFilter {

    private static final String APPLICATION_JSON = "application/json";
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws IOException {
        try {

            final var token = request.getHeader("authorization");

            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                Claims claims = jwtUtil.extractClaims(jwt);

                if (!jwtUtil.isTokenExpired(claims)) {
                    final var roles = claims.get("roles", List.class);
                    final List<SimpleGrantedAuthority> authorities =
                            roles.stream()
                            .map(role -> new SimpleGrantedAuthority(role.toString()))
                            .toList();

                    final var authentication = new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            authorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write("{\"error\":\"Unauthorized access\"}");
        }
    }
}