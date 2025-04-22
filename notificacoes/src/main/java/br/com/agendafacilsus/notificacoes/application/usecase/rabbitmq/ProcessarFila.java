package br.com.agendafacilsus.notificacoes.application.usecase.rabbitmq;

import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessarFila {

    private static final Logger logger = LogManager.getLogger(ProcessarFila.class);

    private final List<ProcessarItemFila> notificacaoList;

    public void gerenciarNotificacoes(TipoNotificacao tipo, NotificacaoDTO notificacaoDTO) {
        notificacaoList.stream()
                .filter(item -> item.checaTipoNotificacao(tipo))
                .forEach(item -> item.processar(notificacaoDTO));
    }

}
