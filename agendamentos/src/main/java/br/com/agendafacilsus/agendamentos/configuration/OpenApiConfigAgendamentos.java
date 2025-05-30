package br.com.agendafacilsus.agendamentos.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OpenApiConfigAgendamentos {

    @Bean
    @Primary
    public OpenAPI customOpenAPIAgendamentos() {
        return new OpenAPI()
                .info(new Info().title("Agendamentos-API").version("v1"))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(
                        new Components().addSecuritySchemes("Authorization",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                                        .description("Bearer + Token JWT")
                        )
                );
    }
}
