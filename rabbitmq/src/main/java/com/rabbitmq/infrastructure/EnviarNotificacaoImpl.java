package com.rabbitmq.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.exception.PublicadorException;
import com.rabbitmq.gateway.EnviarNotificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


@Service
public class EnviarNotificacaoImpl<T> implements EnviarNotificacao<T> {

    private final StreamBridge streamBridge;

    public EnviarNotificacaoImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void enviar(T mensagem) {
        try {

            streamBridge.send("enviar-out-0", mensagem);
            System.out.println("Mensagem enviada : " + new ObjectMapper().writeValueAsString(mensagem));
        } catch (Exception e) {
            throw new PublicadorException("Erro ao publicar mensagem", e);
        }
    }
}
