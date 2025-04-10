package br.com.agendafacilsus.notificacoes.application.usecase.envionotificacao;

import br.com.agendafacilsus.notificacoes.configuration.TwilioConfig;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
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
    private ItemFilaSMS notificacaoSMS;

    private NotificacaoDTO notificacaoDTO;
    private NotificacaoDTO notificacaoDTOInvalida;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notificacaoDTO = new NotificacaoDTO("Teste", "16997609550", "Mensagem de teste", TipoNotificacao.SMS);
        notificacaoDTOInvalida = new NotificacaoDTO("Teste", "1234567890", "Mensagem de teste", TipoNotificacao.SMS);

    }



}
