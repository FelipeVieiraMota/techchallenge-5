package com.notificacoes.infrastructure;

import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.domain.processador.ProcessarNotificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class ConsumirNotificacao {

    private final ProcessarNotificacao strategyFactory;

    @Bean
    public Consumer<NotificacaoDTO> receber() {
        return notificacao -> strategyFactory.getNotificacao(notificacao.tipo()).processar(notificacao);
    }

}
