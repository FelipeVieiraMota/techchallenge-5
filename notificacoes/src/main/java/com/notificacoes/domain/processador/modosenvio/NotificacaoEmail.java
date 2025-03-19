package com.notificacoes.domain.processador.modosenvio;

import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.domain.entidade.TipoNotificacao;
import com.notificacoes.domain.processador.Notificacao;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoEmail implements Notificacao {

    @Override
    public TipoNotificacao getTipo() {
        return TipoNotificacao.EMAIL;
    }

    @Override
    public void processar(NotificacaoDTO notificacaoDTO) {

        System.out.println("Enviando e-mail para: " + notificacaoDTO.destinatario());
        System.out.println("Mensagem: " + notificacaoDTO.mensagem());
    }


}