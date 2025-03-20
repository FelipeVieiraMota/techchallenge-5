package com.notificacoes.usecase.processador;

import com.notificacoes.controller.NotificacaoDTO;
import com.notificacoes.enums.TipoNotificacao;

public interface ProcessarNotificacao {

    boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao);

    void processar(NotificacaoDTO notificacaoDTO);
}
