package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRestGateway {

    private final RestTemplate restTemplate;

    public Optional<UsuarioResponseDTO> buscarPorId(String idUsuario, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            var response = restTemplate.exchange(
                    "http://localhost:8080/autorizacao-usuarios/{id}",
                    HttpMethod.GET,
                    new org.springframework.http.HttpEntity<>(headers),
                    UsuarioResponseDTO.class,
                    idUsuario
            );

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
