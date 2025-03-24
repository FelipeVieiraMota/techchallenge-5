package br.com.agendafacilsus.notificacoes.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    private String idConta;
    private String authToken;
    private String numero;

    public TwilioConfig(
            @Value("${twilio.conta_sid}") String idConta,
            @Value("${twilio.auth_token}") String authToken,
            @Value("${twilio.numero}") String numero) {
        this.idConta = idConta;
        this.authToken = authToken;
        this.numero = numero;
    }

    public String getIdConta() {
        return idConta;
    }

    public void setIdConta(String idConta) {
        this.idConta = idConta;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
