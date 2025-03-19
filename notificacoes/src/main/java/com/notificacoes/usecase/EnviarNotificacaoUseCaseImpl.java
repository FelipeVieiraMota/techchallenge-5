package com.notificacoes.usecase;

import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarNotificacaoUseCaseImpl implements EnviarNotificacaoUseCase {

    private final NotificacaoGateway notificacaoGateway;

    @Override
    public void enviar(NotificacaoDTO notificacaoDTO) {
        notificacaoGateway.enviar(notificacaoDTO);
    }
}
