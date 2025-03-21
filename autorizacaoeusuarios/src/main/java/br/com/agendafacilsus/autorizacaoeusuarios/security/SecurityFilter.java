package br.com.agendafacilsus.autorizacaoeusuarios.security;

import br.com.agendafacilsus.autorizacaoeusuarios.controller.exceptions.ForbiddenException;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.dto.ErrorResponseDto;
import br.com.agendafacilsus.autorizacaoeusuarios.service.PermissionsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private static final String APPLICATION_JSON = "application/json";
    private final PermissionsService permissionsService;

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain
    ) throws IOException {

        try
        {
            permissionsService.checkPermissions(request, response, filterChain);
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
}