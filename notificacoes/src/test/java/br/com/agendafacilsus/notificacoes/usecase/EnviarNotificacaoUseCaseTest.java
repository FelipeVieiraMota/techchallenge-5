package br.com.agendafacilsus.notificacoes.usecase;

import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.gateway.NotificacaoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EnviarNotificacaoUseCaseTest {

    @Mock
    private NotificacaoGateway notificacaoGateway;

    @InjectMocks
    private EnviarNotificacaoUseCase enviarNotificacaoUseCase;

    private NotificacaoDTO notificacaoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notificacaoDTO = new NotificacaoDTO("Teste", "16997609550", "Mensagem de teste", TipoNotificacao.SMS);
    }

    @Test
    void testEnviarNotificacao() {
        // Arrange
        doNothing().when(notificacaoGateway).enviar(notificacaoDTO);

        // Act
        enviarNotificacaoUseCase.enviar(notificacaoDTO);

        // Assert
        verify(notificacaoGateway, times(1)).enviar(notificacaoDTO);
    }

    @Test
    void testGetTotalNotificacoesEnviadas() {
        // Arrange
        String queueName = "filaTeste";
        int totalNotificacoes = 5;
        when(notificacaoGateway.getTotalNotificacoesEnviadas(queueName)).thenReturn(totalNotificacoes);

        // Act
        int result = enviarNotificacaoUseCase.getTotalNotificacoesEnviadas(queueName);

        // Assert
        assertEquals(totalNotificacoes, result);
        verify(notificacaoGateway, times(1)).getTotalNotificacoesEnviadas(queueName);
    }
}
