package br.com.agendafacilsus.especialidades.exception;

public class EspecialidadeNaoEncontradaException extends RuntimeException {
    public EspecialidadeNaoEncontradaException(Long id) {
        super("Especialidade não encontrada com ID: " + id);
    }
}
