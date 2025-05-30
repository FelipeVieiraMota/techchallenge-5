package br.com.agendafacilsus.especialidades.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfigEspecialidades {

    @Bean
    public OpenAPI customOpenAPIEspecialidades() {
        return new OpenAPI()
                .info(new Info().title("Especialidades-API").version("v1"))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components().addSecuritySchemes("Authorization",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("Bearer + Token JWT")));
    }
}