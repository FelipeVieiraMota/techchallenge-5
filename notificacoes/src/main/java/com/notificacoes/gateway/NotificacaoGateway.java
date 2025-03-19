package com.notificacoes.gateway;

import com.notificacoes.domain.entidade.NotificacaoDTO;

public interface NotificacaoGateway {

    void enviar(NotificacaoDTO notificacaoDTO);
}
