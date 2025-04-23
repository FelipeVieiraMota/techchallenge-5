package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRestGatewayTest {

    @InjectMocks
    private UsuarioRestGateway usuarioRestGateway;

    @Mock
    private RestTemplate restTemplate;

    private String idUsuario;
    private String token;

    @BeforeEach
    public void setUp() {
        idUsuario = "usuario123";
        token = "token-secreto";
    }

    @Test
    public void testBuscarPorIdComSucesso() {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO("usuario123", "João", "joao@exemplo.com", UserRole.PACIENTE);
        ResponseEntity<UsuarioResponseDTO> responseEntity = ResponseEntity.ok(usuarioResponseDTO);
        when(restTemplate.exchange(
                eq("http://localhost:8080/autorizacao-usuarios/{id}"),
                eq(HttpMethod.GET),
                any(),
                eq(UsuarioResponseDTO.class),
                eq(idUsuario)
        )).thenReturn(responseEntity);

        Optional<UsuarioResponseDTO> resultado = usuarioRestGateway.buscarPorId(idUsuario, token);

        assertTrue(resultado.isPresent());
        assertEquals("usuario123", resultado.get().id());
        assertEquals("João", resultado.get().nome());
        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8080/autorizacao-usuarios/{id}"),
                eq(HttpMethod.GET),
                any(),
                eq(UsuarioResponseDTO.class),
                eq(idUsuario)
        );
    }

    @Test
    public void testBuscarPorIdComFalha() {
        when(restTemplate.exchange(
                eq("http://localhost:8080/autorizacao-usuarios/{id}"),
                eq(HttpMethod.GET),
                any(),
                eq(UsuarioResponseDTO.class),
                eq(idUsuario)
        )).thenThrow(new RuntimeException("Erro ao chamar o serviço"));

        Optional<UsuarioResponseDTO> resultado = usuarioRestGateway.buscarPorId(idUsuario, token);

        assertFalse(resultado.isPresent());
        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8080/autorizacao-usuarios/{id}"),
                eq(HttpMethod.GET),
                any(),
                eq(UsuarioResponseDTO.class),
                eq(idUsuario)
        );
    }

    @Test
    public void testBuscarPorIdSemUsuario() {
        ResponseEntity<UsuarioResponseDTO> responseEntity = ResponseEntity.ok(null);
        when(restTemplate.exchange(
                eq("http://localhost:8080/autorizacao-usuarios/{id}"),
                eq(HttpMethod.GET),
                any(),
                eq(UsuarioResponseDTO.class),
                eq(idUsuario)
        )).thenReturn(responseEntity);

        Optional<UsuarioResponseDTO> resultado = usuarioRestGateway.buscarPorId(idUsuario, token);

        assertFalse(resultado.isPresent());
        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8080/autorizacao-usuarios/{id}"),
                eq(HttpMethod.GET),
                any(),
                eq(UsuarioResponseDTO.class),
                eq(idUsuario)
        );
    }
}
