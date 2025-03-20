package br.com.notificacoes.usecase.processador.modosenvio;

import br.com.notificacoes.controller.NotificacaoDTO;
import br.com.notificacoes.enums.TipoNotificacao;
import br.com.notificacoes.usecase.processador.ProcessarNotificacao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoSMS implements ProcessarNotificacao {

    private static final Logger logger = LogManager.getLogger(NotificacaoSMS.class);

    @Override
    public boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao) {
        return tipoNotificacao.equals(TipoNotificacao.SMS);
    }

    @Override
    public void processar(NotificacaoDTO notificacaoDTO) {

        logger.info("Enviando para o celular: {}", notificacaoDTO.destinatario());
        logger.info("Mensagem: {}", notificacaoDTO.mensagem());
    }


}