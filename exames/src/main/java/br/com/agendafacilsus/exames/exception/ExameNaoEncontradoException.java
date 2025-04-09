package br.com.agendafacilsus.exames.exception;

public class ExameNaoEncontradoException extends RuntimeException {
    public ExameNaoEncontradoException(Long id) {
        super("Exame não encontrado com ID: " + id);
    }
}
