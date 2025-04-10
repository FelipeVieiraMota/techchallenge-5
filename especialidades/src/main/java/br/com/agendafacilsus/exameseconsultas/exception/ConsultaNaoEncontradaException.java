package br.com.agendafacilsus.exameseconsultas.exception;

public class ConsultaNaoEncontradaException extends RuntimeException {
    public ConsultaNaoEncontradaException(Long id) {
        super("Consulta não encontrada com ID: " + id);
    }
}
