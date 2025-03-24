package br.com.agendafacilsus.notificacoes.configs;

import com.twilio.Twilio;
import org.springframework.stereotype.Component;

@Component
public class EnvioConfig {

    public EnvioConfig(TwilioConfig twilioConfig) {
        Twilio.init(twilioConfig.getIdConta(), twilioConfig.getAuthToken());
    }
}