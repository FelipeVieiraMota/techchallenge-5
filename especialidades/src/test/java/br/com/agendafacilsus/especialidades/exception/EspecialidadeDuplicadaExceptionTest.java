package br.com.agendafacilsus.especialidades.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadeDuplicadaExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemPadrao() {
        EspecialidadeDuplicadaException exception = new EspecialidadeDuplicadaException();

        assertNotNull(exception);
        assertEquals("Já existe uma especialidade cadastrada com essa descrição.", exception.getMessage());
    }
}
