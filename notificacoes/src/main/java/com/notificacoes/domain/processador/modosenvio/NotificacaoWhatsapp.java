package com.notificacoes.domain.processador.modosenvio;

import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.domain.entidade.TipoNotificacao;
import com.notificacoes.domain.processador.Notificacao;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoWhatsapp implements Notificacao {
    @Override
    public TipoNotificacao getTipo() {
        return TipoNotificacao.WHATSAPP;
    }

    @Override
    public void processar(NotificacaoDTO notificacaoDTO) {

        System.out.println("Enviando o whatsapp para: " + notificacaoDTO.destinatario());
        System.out.println("Mensagem: " + notificacaoDTO.mensagem());
    }


}