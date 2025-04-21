package br.com.agendafacilsus.especialidades.exception;

public class EspecialidadeDuplicadaException extends RuntimeException {
    public EspecialidadeDuplicadaException() {
        super("Já existe uma especialidade cadastrada com essa descrição.");
    }
}
