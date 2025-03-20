package br.com.agendafacilsus.notificacoes.usecase.processador;

import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;

public interface ProcessarNotificacao {

    boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao);

    void processar(NotificacaoDTO notificacaoDTO);
}
