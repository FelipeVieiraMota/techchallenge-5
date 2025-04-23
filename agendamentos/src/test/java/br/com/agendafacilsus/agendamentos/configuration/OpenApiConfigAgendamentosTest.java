package br.com.agendafacilsus.agendamentos.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigAgendamentosTest {

    @Test
    void deveCriarBeanOpenAPIComInformacoesEsperadas() {
        OpenApiConfigAgendamentos config = new OpenApiConfigAgendamentos();
        OpenAPI openAPI = config.customOpenAPIAgendamentos();

        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("Agendamentos-API", info.getTitle());
        assertEquals("v1", info.getVersion());

        assertFalse(openAPI.getSecurity().isEmpty());
        SecurityRequirement securityRequirement = openAPI.getSecurity().get(0);
        assertTrue(securityRequirement.containsKey("Authorization"));

        SecurityScheme scheme = openAPI.getComponents().getSecuritySchemes().get("Authorization");
        assertNotNull(scheme);
        assertEquals(SecurityScheme.Type.APIKEY, scheme.getType());
        assertEquals(SecurityScheme.In.HEADER, scheme.getIn());
        assertEquals("Authorization", scheme.getName());
        assertEquals("Bearer + Token JWT", scheme.getDescription());
    }
}
