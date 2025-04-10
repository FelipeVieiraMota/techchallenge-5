package br.com.agendafacilsus.notificacoes.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwilioConfigTest {

    @Test
    void deveRetornarValoresCorretosDoConstrutor() {
        //
        String idConta = "AC123456789";
        String authToken = "auth-token-123";
        String numero = "+5511999999999";

        //
        TwilioConfig config = new TwilioConfig(idConta, authToken, numero);

        //
        assertEquals(idConta, config.getIdConta());
        assertEquals(authToken, config.getAuthToken());
        assertEquals(numero, config.getNumero());
    }

    @Test
    void devePermitirAlterarValoresComSetters() {
        // Arrange
        TwilioConfig config = new TwilioConfig("id", "token", "numero");

        // Act
        config.setIdConta("novaId");
        config.setAuthToken("novoToken");
        config.setNumero("novoNumero");

        // Assert
        assertEquals("novaId", config.getIdConta());
        assertEquals("novoToken", config.getAuthToken());
        assertEquals("novoNumero", config.getNumero());
    }
}