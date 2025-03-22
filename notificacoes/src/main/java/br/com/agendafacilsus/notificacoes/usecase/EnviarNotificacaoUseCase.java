package br.com.agendafacilsus.notificacoes.usecase;

import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.gateway.NotificacaoGateway;
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
