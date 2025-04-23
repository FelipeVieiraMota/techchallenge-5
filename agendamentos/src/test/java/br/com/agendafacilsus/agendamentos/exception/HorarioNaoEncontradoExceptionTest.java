package br.com.agendafacilsus.agendamentos.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class HorarioNaoEncontradoExceptionTest {

    @Test
    void deveRetornarMensagemCorretaQuandoHorarioNaoEncontrado() {
        String medicoId = "12345";
        LocalDate data = LocalDate.of(2025, 4, 23);
        LocalTime hora = LocalTime.of(14, 30);
        String mensagemEsperada = String.format("Horário não encontrado para o médico %s na data %s e hora %s", medicoId, data, hora);

        HorarioNaoEncontradoException ex = new HorarioNaoEncontradoException(medicoId, data, hora);

        assertEquals(mensagemEsperada, ex.getMessage());
    }
}
