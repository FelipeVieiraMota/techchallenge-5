package br.com.agendafacilsus.agendamentos.exception;

public class AgendamentoNaoEncontradoException extends RuntimeException {
    public AgendamentoNaoEncontradoException(Long id) {
        super("Consulta não encontrada com ID: " + id);
    }
}
