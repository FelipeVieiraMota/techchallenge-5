package com.rabbitmq.gateway;

import com.rabbitmq.infrastructure.EnviarNotificacaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.stream.function.StreamBridge;;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class EnviarNotificacaoTest {

    @Test
    void testEnviarNotificacao() {
        StreamBridge streamBridge = mock(StreamBridge.class);
        EnviarNotificacaoImpl<Object> producer = new EnviarNotificacaoImpl<>(streamBridge);

        Object mensagem = "Teste de Mensagem";
        producer.enviar(mensagem);

        verify(streamBridge, times(1)).send("enviarNotificacao-out-0", mensagem);
    }

}