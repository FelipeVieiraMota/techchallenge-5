package br.com.agendafacilsus.agendamentos.configuration;

import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    @Test
    void deveCriarGroupedOpenApiComNomeAgendamentos() {
        SwaggerConfig swaggerConfig = new SwaggerConfig();

        GroupedOpenApi groupedOpenApi = swaggerConfig.agendamentosApi();

        assertNotNull(groupedOpenApi);
        assertEquals("agendamentos", groupedOpenApi.getGroup());
    }
}
