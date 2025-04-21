package br.com.agendafacilsus.autorizacaoeusuarios.exception;

public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException() {
        super("Usuário já foi cadastrado no sistema.");
    }
}
