package com.notificacoes.usecase;

import com.notificacoes.domain.entidade.NotificacaoDTO;

public interface EnviarNotificacaoUseCase {
    void enviar(NotificacaoDTO notificacaoDTO);
}
