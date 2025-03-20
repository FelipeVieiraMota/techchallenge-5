package com.notificacoes.gateway;

import com.notificacoes.controller.NotificacaoDTO;

public interface NotificacaoGateway {

    void enviar(NotificacaoDTO notificacaoDTO);

    int getTotalNotificacoesEnviadas(String queueName);

}
