package br.com.agendafacilsus.notificacoes.application.usecase.rabbitmq;

import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;

public interface ProcessarItemFila {

    boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao);

    void processar(NotificacaoDTO notificacaoDTO);
}
