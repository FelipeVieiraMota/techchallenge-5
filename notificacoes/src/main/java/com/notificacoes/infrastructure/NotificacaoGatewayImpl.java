package com.notificacoes.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.exception.PublicadorException;
import com.notificacoes.gateway.NotificacaoGateway;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoGatewayImpl implements NotificacaoGateway {

    private final StreamBridge streamBridge;

    public NotificacaoGatewayImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void enviar(NotificacaoDTO notificacaoDTO) {
        try {
            streamBridge.send("enviar-out-0", notificacaoDTO);
            System.out.println("Mensagem enviada : " + new ObjectMapper().writeValueAsString(notificacaoDTO));
        } catch (Exception e) {
            throw new PublicadorException("Erro ao publicar mensagem", e);
        }
    }
}
