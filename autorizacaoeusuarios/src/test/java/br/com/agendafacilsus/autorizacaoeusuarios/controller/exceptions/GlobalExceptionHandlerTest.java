package br.com.agendafacilsus.autorizacaoeusuarios.controller.exceptions;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.dtos.ErrorResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void deveTratarUserAlreadyExistsException() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException();

        ResponseEntity<ErrorResponseDto> resposta = globalExceptionHandler.handleUserAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals("Unable to process registration request.", resposta.getBody().error());
        assertEquals(HttpStatus.CONFLICT, resposta.getBody().status());
    }

}
