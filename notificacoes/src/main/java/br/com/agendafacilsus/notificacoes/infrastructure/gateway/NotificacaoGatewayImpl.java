package br.com.agendafacilsus.notificacoes.infrastructure.gateway;

import br.com.agendafacilsus.notificacoes.exception.PublicadorException;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificacaoGatewayImpl implements NotificacaoGateway {

    private static final Logger logger = LogManager.getLogger(NotificacaoGatewayImpl.class);

    private final StreamBridge streamBridge;
    private final RabbitTemplate rabbitTemplate;


    @Override
    public void enviar(NotificacaoDTO notificacaoDTO) {
        try {
            streamBridge.send("enviar-out-0", notificacaoDTO);
            inclusaoDeLog(notificacaoDTO);

        } catch (Exception e) {
            throw new PublicadorException("Erro ao publicar mensagem", e);
        }
    }

    private static void inclusaoDeLog(NotificacaoDTO notificacaoDTO) {
        logger.info("Mensagem enviada: {}", () -> {
            try {
                return new ObjectMapper().writeValueAsString(notificacaoDTO);
            } catch (JsonProcessingException e) {
                return "Erro ao converter objeto para JSON";
            }
        });
    }

}
