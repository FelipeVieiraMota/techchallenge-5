package br.com.agendafacilsus.notificacoes.application.usecase.envionotificacao;

import br.com.agendafacilsus.notificacoes.application.usecase.rabbitmq.ProcessarItemFila;
import br.com.agendafacilsus.notificacoes.configuration.TwilioConfig;
import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemFilaSMS implements ProcessarItemFila {

    private static final Logger logger = LogManager.getLogger(ItemFilaSMS.class);
    public static final String CODIGO_PAIS = "+55";
    public final TwilioConfig twilioConfig;

    @Override
    public boolean checaTipoNotificacao(TipoNotificacao tipoNotificacao) {
        return tipoNotificacao.equals(TipoNotificacao.SMS);
    }

    @Override
    public void processar(NotificacaoDTO notificacaoDTO) {
        String remetente = "De: " + notificacaoDTO.nome() + "\n";

        if (!validarNumero(notificacaoDTO.destinatario())) {
            logger.warn("Número inválido: {}", notificacaoDTO.destinatario());
            return;
        }

        Message.creator(
                        new PhoneNumber(CODIGO_PAIS + notificacaoDTO.destinatario()),
                        new PhoneNumber(twilioConfig.getNumero()),
                        remetente + notificacaoDTO.mensagem())
                .create();

        logger.info("Enviando para o celular: {}", notificacaoDTO.destinatario());
        logger.info("Remetente: {}", remetente);
        logger.info("Mensagem: {}", notificacaoDTO.mensagem());
    }

    private boolean validarNumero(String numero) {
        return numero.matches("^[1-9]{2}9\\d{8}$");
    }

}