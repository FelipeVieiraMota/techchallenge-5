package br.com.agendafacilsus.commonlibrary.domain.exception;

public class EspecialidadeNaoEncontradaException extends RuntimeException {
    public EspecialidadeNaoEncontradaException(Long id) {
        super("Especialidade não encontrada com ID: " + id);
    }
}
