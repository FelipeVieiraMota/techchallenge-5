package br.com.notificacoes.usecase.processador.modosenvio;

import br.com.notificacoes.usecase.processador.ProcessarNotificacao;
import br.com.notificacoes.controller.NotificacaoDTO;
import br.com.notificacoes.enums.TipoNotificacao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoWhatsapp implements ProcessarNotificacao {

    private static final Logger logger = LogManager.getLogger(NotificacaoWhatsapp.class);

    @Override
    public boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao) {
        return tipoNotificacao.equals(TipoNotificacao.WHATSAPP);
    }


    @Override
    public void processar(NotificacaoDTO notificacaoDTO) {

        logger.info("Enviando o WhatsApp para: {}", notificacaoDTO.destinatario());
        logger.info("Mensagem: {}", notificacaoDTO.mensagem());
    }


}