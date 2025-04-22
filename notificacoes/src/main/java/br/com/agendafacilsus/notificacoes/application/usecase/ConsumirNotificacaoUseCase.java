package br.com.agendafacilsus.notificacoes.application.usecase;

import br.com.agendafacilsus.notificacoes.application.usecase.rabbitmq.ProcessarFila;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class ConsumirNotificacaoUseCase {

    private final ProcessarFila processarFila;

    @Bean
    public Consumer<NotificacaoDTO> receber() {
        return notificacao -> processarFila.gerenciarNotificacoes(notificacao.tipo(), notificacao);
    }

}
