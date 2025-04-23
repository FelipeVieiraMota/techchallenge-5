package br.com.agendafacilsus.autorizacaoeusuarios.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioDuplicadoExceptionTest {

    @Test
    void deveLancarUsuarioDuplicadoException() {
        // Verifica se a exceção é lançada
        assertThrows(UsuarioDuplicadoException.class, () -> {
            throw new UsuarioDuplicadoException();
        });
    }

    @Test
    void deveConterMensagemPadraoCorreta() {
        // Cria a exceção
        UsuarioDuplicadoException exception = new UsuarioDuplicadoException();

        // Verifica a mensagem
        assertEquals("Usuário já foi cadastrado no sistema.", exception.getMessage());
    }
}
