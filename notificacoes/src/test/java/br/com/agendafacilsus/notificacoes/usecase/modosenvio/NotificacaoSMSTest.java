package br.com.agendafacilsus.notificacoes.usecase.modosenvio;

import br.com.agendafacilsus.notificacoes.configs.TwilioConfig;
import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.usecase.processador.modosenvio.NotificacaoSMS;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

public class NotificacaoSMSTest {

    @Mock
    private TwilioConfig twilioConfig;

    @Mock
    private Logger logger;

    @InjectMocks
    private NotificacaoSMS notificacaoSMS;

    private NotificacaoDTO notificacaoDTO;
    private NotificacaoDTO notificacaoDTOInvalida;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notificacaoDTO = new NotificacaoDTO("Teste", "16997609550", "Mensagem de teste", TipoNotificacao.SMS);
        notificacaoDTOInvalida = new NotificacaoDTO("Teste", "1234567890", "Mensagem de teste", TipoNotificacao.SMS);

    }



}
