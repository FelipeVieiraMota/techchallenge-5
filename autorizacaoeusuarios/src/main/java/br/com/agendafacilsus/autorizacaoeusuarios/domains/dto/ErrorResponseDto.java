package br.com.agendafacilsus.autorizacaoeusuarios.domains.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto (
    String error,
    HttpStatus status
) {} 