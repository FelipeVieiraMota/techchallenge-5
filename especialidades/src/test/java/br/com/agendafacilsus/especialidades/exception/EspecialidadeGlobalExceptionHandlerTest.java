package br.com.agendafacilsus.especialidades.exception;

import br.com.agendafacilsus.commonlibrary.domain.exception.EspecialidadeNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadeGlobalExceptionHandlerTest {

    private EspecialidadeGlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new EspecialidadeGlobalExceptionHandler();
    }

    @Test
    void deveRetornarNotFoundParaEspecialidadeNaoEncontrada() {
        Long id = 42L;
        EspecialidadeNaoEncontradaException ex = new EspecialidadeNaoEncontradaException(id);

        ResponseEntity<String> response = handler.handleEspecialidadeNotFound(ex);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Especialidade não encontrada com ID: " + id, response.getBody());
    }

    @Test
    void deveRetornarConflictParaEspecialidadeDuplicada() {
        EspecialidadeDuplicadaException ex = new EspecialidadeDuplicadaException();

        ResponseEntity<String> response = handler.handleDuplicateEspecialidade(ex);

        assertNotNull(response);
        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Já existe uma especialidade cadastrada com essa descrição.", response.getBody());
    }
}
