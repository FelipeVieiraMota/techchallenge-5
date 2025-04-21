package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EspecialidadeRestGateway {

    private final RestTemplate restTemplate;

    public Optional<EspecialidadeResponseDTO> buscarPorId(Long idEspecialidade, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            var response = restTemplate.exchange(
                    "http://localhost:8080/especialidades/{id}",
                    HttpMethod.GET,
                    new org.springframework.http.HttpEntity<>(headers),
                    EspecialidadeResponseDTO.class,
                    idEspecialidade
            );

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
