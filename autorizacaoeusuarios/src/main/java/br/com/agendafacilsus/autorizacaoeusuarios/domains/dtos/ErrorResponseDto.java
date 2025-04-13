package br.com.agendafacilsus.autorizacaoeusuarios.domains.dtos;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(
        String error,
        HttpStatus status
) {

    @Override
    public String toString() {
        return String.format("""
                            {
                                "error": "%s",
                                "status": "%s"
                            }
                        """,
                error, status.name());
    }
}