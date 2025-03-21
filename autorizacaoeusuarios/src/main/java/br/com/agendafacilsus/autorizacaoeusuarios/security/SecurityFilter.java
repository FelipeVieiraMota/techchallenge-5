package br.com.agendafacilsus.autorizacaoeusuarios.security;

import br.com.agendafacilsus.autorizacaoeusuarios.controller.exceptions.ForbiddenException;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.dto.ErrorResponseDto;
import br.com.agendafacilsus.autorizacaoeusuarios.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService service;
    private static final String APPLICATION_JSON = "application/json";

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain
    ) throws ServletException, IOException {

        try
        {
            doIt(request, response, filterChain);
        }
        catch (ForbiddenException exception)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(new ErrorResponseDto(exception.getMessage(), HttpStatus.FORBIDDEN).toString());
        }
        catch (Exception exception)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(new ErrorResponseDto("Unpredictable error happened.", HttpStatus.INTERNAL_SERVER_ERROR).toString());
        }
    }

    private void doIt (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        var token = this.recoverToken(request);

        if(token != null){

            final var login = tokenService.validateToken(token);
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

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
