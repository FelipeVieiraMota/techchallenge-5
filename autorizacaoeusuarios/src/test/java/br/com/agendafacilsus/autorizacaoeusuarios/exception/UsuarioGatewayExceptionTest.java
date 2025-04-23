package br.com.agendafacilsus.autorizacaoeusuarios.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioGatewayExceptionTest {

    @Test
    void deveLancarUsuarioGatewayException() {
        assertThrows(UsuarioGatewayException.class, () -> {
            throw new UsuarioGatewayException("Erro ao acessar gateway", new RuntimeException("Falha interna"));
        });
    }

    @Test
    void deveConterMensagemCorreta() {
        String mensagemEsperada = "Erro ao acessar gateway";
        UsuarioGatewayException exception = new UsuarioGatewayException(mensagemEsperada, new RuntimeException());

        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void deveConterCausaCorreta() {
        Throwable causa = new RuntimeException("Causa original");
        UsuarioGatewayException exception = new UsuarioGatewayException("Erro ao acessar gateway", causa);

        assertEquals(causa, exception.getCause());
    }
}
