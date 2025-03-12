package com.rabbitmq.gateway;

import java.util.function.Consumer;

public interface ReceberNotificacao<T> {

    Consumer<T> receber();
}