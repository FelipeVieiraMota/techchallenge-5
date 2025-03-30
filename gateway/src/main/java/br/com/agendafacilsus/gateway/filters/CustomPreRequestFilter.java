package br.com.agendafacilsus.gateway.filters;

import br.com.agendafacilsus.commonlibrary.domains.dtos.TokenDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
    public GatewayFilter apply(final Config config) {

        return (exchange, chain) -> {

            final var request = exchange.getRequest();
            final var path = request.getPath().value();

            for (var ignoredPath : IGNORED_PATHS) {
                if (path.startsWith(ignoredPath)) {
                    return chain.filter(exchange);
                }
            }

            final var token = checkTokenBeforeSend(request);

            if(token == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.post()
                    .uri(END_POINT)
                    .headers(headers -> headers.addAll(request.getHeaders()))
                    .bodyValue(new TokenDto(token))
                    .retrieve()
                    .toEntity(Boolean.class)
                    .flatMap(responseEntity -> checkAnswer(exchange, chain, responseEntity))
                    .onErrorResume(error -> {
                        var statusCode = extractHttpStatusCode(error);
                        exchange.getResponse().setStatusCode(statusCode);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    private HttpStatus extractHttpStatusCode(final Throwable error) {

        var statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        if (error instanceof org.springframework.web.reactive.function.client.WebClientResponseException exception) {
            statusCode = HttpStatus.valueOf(exception.getStatusCode().value());
        }

        return statusCode;
    }

    private Mono<Void> checkAnswer(final ServerWebExchange exchange,
                                   final GatewayFilterChain chain,
                                   final ResponseEntity<Boolean> responseEntity) {
        if (Boolean.TRUE.equals(responseEntity.getBody())) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }


    private String checkTokenBeforeSend(final ServerHttpRequest request) {

        final var nonFilteredToken = request.getHeaders().getFirst("Authorization");

        if(nonFilteredToken == null || !nonFilteredToken.contains("Bearer")) {
            return null;
        }

        final var token = nonFilteredToken.split("Bearer")[1];

        return token == null || token.isBlank() || token.isEmpty() ? null : token.trim();
    }

    public static class Config { }
}