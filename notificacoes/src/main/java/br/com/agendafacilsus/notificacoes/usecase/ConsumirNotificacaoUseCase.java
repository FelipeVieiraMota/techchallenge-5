package br.com.agendafacilsus.notificacoes.usecase;

import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.usecase.processador.ProcessarNotificacaoImpl;
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
