package com.notificacoes.domain.processador;

import com.notificacoes.domain.entidade.NotificacaoDTO;
import com.notificacoes.domain.entidade.TipoNotificacao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProcessarNotificacao {

    private final Map<TipoNotificacao, Notificacao> strategies;

    public ProcessarNotificacao(List<Notificacao> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(Notificacao::getTipo, Function.identity()));
    }

    public Notificacao getNotificacao(TipoNotificacao tipo) {

        return strategies.getOrDefault(tipo, new Notificacao() {

            @Override
            public TipoNotificacao getTipo() {
                return tipo;
            }

            @Override
            public void processar(NotificacaoDTO notificacaoDTO) {
                System.out.println("Tipo de notificação não suportado: " + tipo);
            }
        });
    }

}
