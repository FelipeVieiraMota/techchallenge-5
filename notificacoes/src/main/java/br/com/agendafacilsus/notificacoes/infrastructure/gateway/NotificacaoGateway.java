package br.com.agendafacilsus.notificacoes.infrastructure.gateway;

import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;

public interface NotificacaoGateway {

    void enviar(NotificacaoDTO notificacaoDTO);

}
