package com.rabbitmq.gateway;

import com.rabbitmq.infrastructure.ReceberNotificacaoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceberNotificacaoTest {

    @Test
    void testReceber() {
        ReceberNotificacaoImpl<Object> consumer = new ReceberNotificacaoImpl<>();
        Object mensagemRecebida = "Teste de Consumo";

        consumer.receber().accept(mensagemRecebida);

        assertNotNull(mensagemRecebida);
    }

}