package br.com.agendafacilsus.notificacoes.gateway;

import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;

public interface NotificacaoGateway {

    void enviar(NotificacaoDTO notificacaoDTO);

    int getTotalNotificacoesEnviadas(String queueName);

}
