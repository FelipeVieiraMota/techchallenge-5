package br.com.agendafacilsus.agendamentos.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoDuplicadoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemPadrao() {
        AgendamentoDuplicadoException exception = new AgendamentoDuplicadoException();

        assertNotNull(exception);
        assertEquals("Já existe um agendamento com essas informações.", exception.getMessage());
    }
}
