package br.com.agendafacilsus.agendamentos.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi agendamentosApi() {
        return GroupedOpenApi.builder()
                .group("agendamentos")
                .packagesToScan("br.com.agendafacilsus.agendamentos")
                .build();
    }
}
