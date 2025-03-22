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

        for (int i = 1; i <= 2; i++) {
            final var dto = new NotificacaoDTO(
                    "Lucas Alves",
                    "lcalves012@gmail.com",
                    i + " mensagem enviada do microserviço de agendamento.",
                    TipoNotificacao.EMAIL
            );

            notificacaoGateway.enviar(dto);
        }
    }
}
