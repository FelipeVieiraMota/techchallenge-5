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

        for (int i = 1; i <= 1; i++) {
            final var dto = new NotificacaoDTO(
                    "Lucas Alves",
                    "11970583685",
                    i + " mensagem enviada do microserviço de agendamento.",
                    TipoNotificacao.SMS
            );

            notificacaoGateway.enviar(dto);
        }
    }
}
