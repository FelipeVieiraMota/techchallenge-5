package com.rabbitmq.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.gateway.ReceberNotificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class ReceberNotificacaoImpl<T> implements ReceberNotificacao<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    @Override
    public Consumer<T> receber() {
        return mensagem -> {
            String json =  new String((byte[]) mensagem, StandardCharsets.UTF_8);
            System.out.println("Mensagem recebida : " + json);
        };

    }

}
