package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadeRestGatewayTest {

    @InjectMocks
    private EspecialidadeRestGateway especialidadeRestGateway;

    @Mock
    private RestTemplate restTemplate;

    private EspecialidadeResponseDTO especialidadeResponseDTO;
    private String token;

    @BeforeEach
    public void setUp() {
        token = "valid-token";
        especialidadeResponseDTO = new EspecialidadeResponseDTO(1L, "Cardiologia", TipoEspecialidade.CONSULTA);
    }

    @Test
    public void testBuscarPorIdComSucesso() {
        when(restTemplate.exchange(
                eq("http://localhost:8080/especialidades/{id}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(EspecialidadeResponseDTO.class),
                eq(1L)
        )).thenReturn(ResponseEntity.ok(especialidadeResponseDTO));

        Optional<EspecialidadeResponseDTO> especialidade = especialidadeRestGateway.buscarPorId(1L, token);

        assertTrue(especialidade.isPresent());
        assertEquals(especialidadeResponseDTO, especialidade.get());
        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8080/especialidades/{id}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(EspecialidadeResponseDTO.class),
                eq(1L)
        );
    }

    @Test
    public void testBuscarPorIdComErro() {
        when(restTemplate.exchange(
                eq("http://localhost:8080/especialidades/{id}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(EspecialidadeResponseDTO.class),
                eq(1L)
        )).thenThrow(new RuntimeException("Erro inesperado"));

        Optional<EspecialidadeResponseDTO> especialidade = especialidadeRestGateway.buscarPorId(1L, token);

        assertFalse(especialidade.isPresent());
        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8080/especialidades/{id}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(EspecialidadeResponseDTO.class),
                eq(1L)
        );
    }

    @Test
    public void testBuscarPorIdComRespostaVazia() {
        when(restTemplate.exchange(
                eq("http://localhost:8080/especialidades/{id}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(EspecialidadeResponseDTO.class),
                eq(1L)
        )).thenReturn(ResponseEntity.ok().body(null));

        Optional<EspecialidadeResponseDTO> especialidade = especialidadeRestGateway.buscarPorId(1L, token);

        assertFalse(especialidade.isPresent());
        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8080/especialidades/{id}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(EspecialidadeResponseDTO.class),
                eq(1L)
        );
    }
}
