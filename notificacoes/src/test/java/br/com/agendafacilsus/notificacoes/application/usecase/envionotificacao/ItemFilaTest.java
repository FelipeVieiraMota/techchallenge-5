package br.com.agendafacilsus.notificacoes.application.usecase.envionotificacao;

import br.com.agendafacilsus.notificacoes.configuration.TwilioConfig;
import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import com.twilio.rest.api.v2010.account.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ItemFilaTest {

    private ItemFilaSMS itemFilaSMS;
    private TwilioConfig twilioConfig;

    @BeforeEach
    void setup() {
        twilioConfig = mock(TwilioConfig.class);
        itemFilaSMS = new ItemFilaSMS(twilioConfig);
    }

    @Test
    void deveRetornarTrueParaTipoSMS() {
        assertTrue(itemFilaSMS.checaTipoNotificacao(TipoNotificacao.SMS));
    }

    @Test
    void deveRetornarFalseParaOutroTipo() {
        assertFalse(itemFilaSMS.checaTipoNotificacao(TipoNotificacao.EMAIL));
    }

    @Test
    void naoDeveProcessarSeNumeroForInvalido() {
        NotificacaoDTO dto = new NotificacaoDTO("Usuario", "97012901643", "Mensagem teste", TipoNotificacao.SMS);

        try (MockedStatic<Message> messageMockedStatic = mockStatic(Message.class)) {
            itemFilaSMS.processar(dto);
            messageMockedStatic.verifyNoInteractions();
        }
    }
}
