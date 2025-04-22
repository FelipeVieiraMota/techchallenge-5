package br.com.agendafacilsus.agendamentos.exception;

public class AgendamentoDuplicadoException extends RuntimeException {
    public AgendamentoDuplicadoException() {
        super("Já existe um agendamento com essas informações.");
    }
}
