package br.com.agendafacilsus.especialidades.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadeGatewayExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECausa() {
        String mensagemEsperada = "Erro ao acessar o gateway de especialidades";
        Throwable causaEsperada = new RuntimeException("Falha na conexão");

        EspecialidadeGatewayException exception = new EspecialidadeGatewayException(mensagemEsperada, causaEsperada);

        assertNotNull(exception);
        assertEquals(mensagemEsperada, exception.getMessage());
        assertEquals(causaEsperada, exception.getCause());
    }
}
