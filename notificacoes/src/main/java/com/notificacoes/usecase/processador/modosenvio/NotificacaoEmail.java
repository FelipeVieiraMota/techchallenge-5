package com.notificacoes.usecase.processador.modosenvio;

import com.notificacoes.controller.NotificacaoDTO;
import com.notificacoes.enums.TipoNotificacao;
import com.notificacoes.usecase.processador.ProcessarNotificacao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoEmail implements ProcessarNotificacao {

    private static final Logger logger = LogManager.getLogger(NotificacaoEmail.class);

    @Override
    public boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao) {
        return tipoNotificacao.equals(TipoNotificacao.EMAIL);
    }

    @Override
    public void processar(NotificacaoDTO notificacaoDTO) {

        logger.info("Enviando e-mail para: {}", notificacaoDTO.destinatario());
        logger.info("Mensagem: {}", notificacaoDTO.mensagem());
    }


}