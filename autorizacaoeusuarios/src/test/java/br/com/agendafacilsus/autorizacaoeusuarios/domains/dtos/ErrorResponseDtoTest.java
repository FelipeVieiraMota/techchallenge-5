package br.com.agendafacilsus.autorizacaoeusuarios.domains.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseDtoTest {

    @Test
    void deveCriarErrorResponseDtoCorretamente() {
        String erroEsperado = "Erro de autenticação";
        HttpStatus statusEsperado = HttpStatus.UNAUTHORIZED;

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(erroEsperado, statusEsperado);

        assertEquals(erroEsperado, errorResponseDto.error());
        assertEquals(statusEsperado, errorResponseDto.status());
    }

    @Test
    void deveRetornarToStringCorretamente() {
        String erro = "Erro de autenticação";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(erro, status);

        String resultado = errorResponseDto.toString();

        String esperado = """
                            {
                                "error": "Erro de autenticação",
                                "status": "UNAUTHORIZED"
                            }
                        """;

        assertEquals(esperado, resultado);
    }

    @Test
    void deveRetornarToStringComErroGenerico() {
        String erro = "Erro desconhecido";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(erro, status);

        String resultado = errorResponseDto.toString();

        String esperado = """
                            {
                                "error": "Erro desconhecido",
                                "status": "INTERNAL_SERVER_ERROR"
                            }
                        """;

        assertEquals(esperado, resultado);
    }

    @Test
    void deveRetornarStatusCorreto() {
        String erro = "Erro de validação";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(erro, status);

        HttpStatus statusResultado = errorResponseDto.status();

        assertEquals(status, statusResultado);
    }

    @Test
    void deveRetornarErroCorreto() {
        String erroEsperado = "Erro de validação";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(erroEsperado, status);

        String erroResultado = errorResponseDto.error();

        assertEquals(erroEsperado, erroResultado);
    }
}
