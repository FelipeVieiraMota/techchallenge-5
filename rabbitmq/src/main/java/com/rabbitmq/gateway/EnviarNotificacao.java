package com.rabbitmq.gateway;

public interface EnviarNotificacao<T> {
    void enviar(T mensagem);

}