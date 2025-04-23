package br.com.agendafacilsus.agendamentos.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HorarioNaoDisponivelExceptionTest {

    @Test
    void deveRetornarMensagemCorretaQuandoHorarioNaoDisponivel() {
        LocalDate data = LocalDate.of(2025, 4, 23); // Exemplo de data
        String mensagemEsperada = "Não há horarios disponíveis para a data: " + data;

        HorarioNaoDisponivelException ex = new HorarioNaoDisponivelException(data);

        assertEquals(mensagemEsperada, ex.getMessage());
    }
}
