package com.notificacoes.exception;

public class PublicadorException extends RuntimeException {
    public PublicadorException(String message) {
        super(message);
    }

    public PublicadorException(String message, Throwable cause) {
        super(message, cause);
    }
}