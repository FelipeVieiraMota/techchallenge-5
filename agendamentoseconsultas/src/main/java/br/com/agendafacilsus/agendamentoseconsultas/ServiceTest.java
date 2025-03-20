package br.com.agendafacilsus.agendamentoseconsultas;

import br.com.agendafacilsus.notificacoes.controller.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceTest {

    private final NotificacaoGateway notificacaoGateway;

    public void test() {

        final var dto = new NotificacaoDTO(
            "Joao da Silva",
            "Test",
            "Test",
            TipoNotificacao.EMAIL
        );

        notificacaoGateway.enviar(dto);
    }
}
