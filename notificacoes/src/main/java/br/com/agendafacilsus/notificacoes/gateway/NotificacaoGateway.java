package br.com.notificacoes.gateway;

import br.com.notificacoes.controller.NotificacaoDTO;

public interface NotificacaoGateway {

    void enviar(NotificacaoDTO notificacaoDTO);

    int getTotalNotificacoesEnviadas(String queueName);

}
