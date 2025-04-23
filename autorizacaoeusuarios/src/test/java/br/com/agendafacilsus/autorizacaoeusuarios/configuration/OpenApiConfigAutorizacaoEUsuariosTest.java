package br.com.agendafacilsus.autorizacaoeusuarios.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenApiConfigAutorizacaoEUsuariosTest {

    @Autowired
    private OpenAPI openAPI;

    @Test
    void shouldContainAuthorizationSecurityScheme() {
        // Verificando se o SecurityScheme "Authorization" foi configurado corretamente
        assertNotNull(openAPI.getComponents());
        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("Authorization");

        assertNotNull(securityScheme);
        assertEquals(SecurityScheme.Type.APIKEY, securityScheme.getType());
        assertEquals(SecurityScheme.In.HEADER, securityScheme.getIn());
        assertEquals("Authorization", securityScheme.getName());
        assertEquals("Bearer + Token JWT", securityScheme.getDescription());
    }

    @Test
    void shouldContainCorrectOpenApiInfo() {
        // Verificando as informações do OpenAPI (título e versão)
        assertNotNull(openAPI.getInfo());
        assertEquals("Autorização-Usuários-API", openAPI.getInfo().getTitle());
        assertEquals("v1", openAPI.getInfo().getVersion());
    }

    @Test
    void shouldContainSecurityRequirement() {
        // Verificando se o SecurityRequirement foi adicionado corretamente
        assertNotNull(openAPI.getSecurity());
        assertFalse(openAPI.getSecurity().isEmpty());
        assertTrue(openAPI.getSecurity().get(0).containsKey("Authorization"));
    }
}
