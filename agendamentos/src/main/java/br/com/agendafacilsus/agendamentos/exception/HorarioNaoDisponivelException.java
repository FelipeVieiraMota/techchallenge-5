package br.com.agendafacilsus.agendamentos.exception;

import java.time.LocalDate;

public class HorarioNaoDisponivelException extends RuntimeException {
    public HorarioNaoDisponivelException(LocalDate data) {
        super("Não há horarios disponíveis para a data: " + data);
    }
}
