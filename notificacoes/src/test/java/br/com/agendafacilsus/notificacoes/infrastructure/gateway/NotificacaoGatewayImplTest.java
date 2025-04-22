package br.com.agendafacilsus.notificacoes.infrastructure.gateway;

import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.exception.PublicadorException;
import br.com.agendafacilsus.notificacoes.infrastructure.gateway.NotificacaoGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.stream.function.StreamBridge;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoGatewayImplTest {

    @Mock
    private StreamBridge streamBridge;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private NotificacaoGatewayImpl notificacaoGateway;

    private NotificacaoDTO notificacaoDTO;

    @BeforeEach
    void setUp() {
        notificacaoDTO = new NotificacaoDTO("Teste Nome", "teste@email.com", "Mensagem de teste", TipoNotificacao.SMS);
    }

    @Test
    void deveEnviarMensagemComSucesso() {
        when(streamBridge.send(anyString(), any())).thenReturn(true);

        assertDoesNotThrow(() -> notificacaoGateway.enviar(notificacaoDTO));
        verify(streamBridge, times(1)).send("enviar-out-0", notificacaoDTO);
    }

    @Test
    void deveLancarPublicadorExceptionQuandoStreamBridgeFalhar() {
        when(streamBridge.send(anyString(), any())).thenThrow(new RuntimeException("Falha ao enviar mensagem"));

        PublicadorException exception = assertThrows(PublicadorException.class, () -> notificacaoGateway.enviar(notificacaoDTO));
        assertEquals("Erro ao publicar mensagem", exception.getMessage());
    }
}
