package br.com.agendafacilsus.agendamentos.exception;

public class AgendamentoGatewayException extends RuntimeException {
    public AgendamentoGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
