package com.notificacoes.domain.processador;

import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.domain.entidade.TipoNotificacao;

public interface Notificacao {
    void processar(NotificacaoDTO notificacaoDTO);

    TipoNotificacao getTipo();
}
