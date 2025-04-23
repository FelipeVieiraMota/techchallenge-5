package br.com.agendafacilsus.agendamentos.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoNaoEncontradoExceptionTest {

    @Test
    void deveRetornarMensagemCorretaQuandoConsultaNaoEncontrada() {
        Long id = 123L;
        String mensagemEsperada = "Consulta não encontrada com ID: " + id;

        AgendamentoNaoEncontradoException ex = new AgendamentoNaoEncontradoException(id);

        assertEquals(mensagemEsperada, ex.getMessage());
    }
}
