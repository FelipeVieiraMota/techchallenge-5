package br.com.agendafacilsus.notificacoes.usecase.processador;

import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessarNotificacaoImpl {

    private static final Logger logger = LogManager.getLogger(ProcessarNotificacaoImpl.class);

    private final List<ProcessarNotificacao> notificacaoList;

    public void gerenciarNotificacoes(TipoNotificacao tipo, NotificacaoDTO notificacaoDTO) {
        notificacaoList.stream()
                .filter(item -> item.checaTipoNotificacao(tipo))
                .forEach(item -> item.processar(notificacaoDTO));
    }

}
