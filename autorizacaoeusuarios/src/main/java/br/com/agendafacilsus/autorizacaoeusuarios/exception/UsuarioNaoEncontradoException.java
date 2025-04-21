package br.com.agendafacilsus.autorizacaoeusuarios.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String idOuToken) {
        super("Usuário não encontrada com ID ou token de login: " + idOuToken);
    }
}
