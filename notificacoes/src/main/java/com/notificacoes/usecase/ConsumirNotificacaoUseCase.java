package com.notificacoes.usecase;

import com.notificacoes.controller.NotificacaoDTO;
import com.notificacoes.usecase.processador.ProcessarNotificacaoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class ConsumirNotificacaoUseCase {

    private final ProcessarNotificacaoImpl processarNotificacaoImpl;

    @Bean
    public Consumer<NotificacaoDTO> receber() {
        return notificacao -> processarNotificacaoImpl.gerenciarNotificacoes(notificacao.tipo(), notificacao);
    }

}
