package br.com.agendafacilsus.agendamentos.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoGatewayExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemECausa() {
        String mensagemEsperada = "Erro ao acessar o gateway de agendamento";
        Throwable causaEsperada = new RuntimeException("Falha na conexão");


        AgendamentoGatewayException exception = new AgendamentoGatewayException(mensagemEsperada, causaEsperada);

        assertNotNull(exception);
        assertEquals(mensagemEsperada, exception.getMessage());
        assertEquals(causaEsperada, exception.getCause());
    }
}
