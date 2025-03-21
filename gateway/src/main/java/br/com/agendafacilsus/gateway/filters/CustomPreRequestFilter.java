package br.com.agendafacilsus.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class CustomPreRequestFilter extends AbstractGatewayFilterFactory<CustomPreRequestFilter.Config> {

    private final WebClient webClient;
    private static final String URL = "http://localhost:9000";
    private static final String END_POINT = "/auth/validation";

    public CustomPreRequestFilter() {
        super(Config.class);
        this.webClient = WebClient.builder().baseUrl(URL).build();
    }

    private static final List<String> IGNORED_PATHS = List.of("/auth/login");

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            final ServerHttpRequest request = exchange.getRequest();
            final String path = request.getPath().value();

            // Ignore some endpoints as necessary.
            for (String ignoredPath : IGNORED_PATHS) {
                if (path.startsWith(ignoredPath)) {
                    return chain.filter(exchange);
                }
            }

            // TODO: Adicionar o body na requisição, e validar se o token está presente antes mesmo de
            //      chamar o endpoint.

            System.out.println(request.getHeaders());


            return webClient.post()
                    .uri(END_POINT)
                    .headers(headers -> headers.addAll(request.getHeaders()))
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<>() {})
                    .flatMap(responseEntity -> {
                        if (responseEntity.getStatusCode().is2xxSuccessful()) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(error -> {
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    public static class Config { }
}