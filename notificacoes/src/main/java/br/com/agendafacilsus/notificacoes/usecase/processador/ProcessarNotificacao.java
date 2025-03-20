package br.com.notificacoes.usecase.processador;

import br.com.notificacoes.controller.NotificacaoDTO;
import br.com.notificacoes.enums.TipoNotificacao;

public interface ProcessarNotificacao {

    boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao);

    void processar(NotificacaoDTO notificacaoDTO);
}
