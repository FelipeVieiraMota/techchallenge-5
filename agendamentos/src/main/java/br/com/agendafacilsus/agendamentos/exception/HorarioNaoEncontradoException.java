package br.com.agendafacilsus.agendamentos.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class HorarioNaoEncontradoException extends RuntimeException {
    public HorarioNaoEncontradoException(String medicoId, LocalDate data, LocalTime hora) {
        super(String.format("Horário não encontrado para o médico %s na data %s e hora %s", medicoId, data, hora));
    }
}

