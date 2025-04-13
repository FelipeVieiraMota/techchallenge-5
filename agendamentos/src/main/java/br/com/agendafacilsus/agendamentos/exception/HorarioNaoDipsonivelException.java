package br.com.agendafacilsus.agendamentos.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class HorarioNaoDipsonivelException extends RuntimeException {
    public HorarioNaoDipsonivelException(LocalDate data) {
        super("Não há horarios disponíveis para a data: " + data);
    }
}
