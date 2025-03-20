package com.notificacoes.usecase;

import com.notificacoes.controller.NotificacaoDTO;
import com.notificacoes.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarNotificacaoUseCase {

    private final NotificacaoGateway notificacaoGateway;


    public void enviar(NotificacaoDTO notificacaoDTO) {
        notificacaoGateway.enviar(notificacaoDTO);
    }

    public int getTotalNotificacoesEnviadas(String queueName) {
        return notificacaoGateway.getTotalNotificacoesEnviadas(queueName);
    }
}
