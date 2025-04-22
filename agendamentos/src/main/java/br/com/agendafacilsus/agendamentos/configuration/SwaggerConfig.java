package br.com.agendafacilsus.agendamentos.configuration;

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
