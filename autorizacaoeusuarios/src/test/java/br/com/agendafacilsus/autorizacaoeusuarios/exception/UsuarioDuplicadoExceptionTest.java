package br.com.agendafacilsus.autorizacaoeusuarios.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioDuplicadoExceptionTest {

    @Test
    void deveLancarUsuarioDuplicadoException() {
        assertThrows(UsuarioDuplicadoException.class, () -> {
            throw new UsuarioDuplicadoException();
        });
    }

    @Test
    void deveConterMensagemPadraoCorreta() {
        UsuarioDuplicadoException exception = new UsuarioDuplicadoException();

        assertEquals("Usuário já foi cadastrado no sistema.", exception.getMessage());
    }
}
