package br.com.agendafacilsus.autorizacaoeusuarios.exception;

public class UsuarioJPNaoEncontradoException extends RuntimeException {
    public UsuarioJPNaoEncontradoException(String id) {
        super("Consulta não encontrada com ID: " + id);
    }
}
