package br.com.agendafacilsus.agendamentos.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AgendamentoGlobalExceptionHandlerTest {

    private AgendamentoGlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AgendamentoGlobalExceptionHandler();
    }

    @Test
    void deveRetornarNotFoundParaAgendamentoNaoEncontrado() {
        Long mensagem = 1L;
        AgendamentoNaoEncontradoException ex = new AgendamentoNaoEncontradoException(mensagem);

        ResponseEntity<String> response = handler.handleAgendamentoNotFound(ex);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Consulta não encontrada com ID: " + mensagem, response.getBody());
    }

    @Test
    void deveRetornarNotFoundParaHorarioNaoDisponivel() {
        LocalDate mensagem = LocalDate.of(2025, 4, 23);
        HorarioNaoDisponivelException ex = new HorarioNaoDisponivelException(mensagem);

        ResponseEntity<String> response = handler.handleHorarioNotFound(ex);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Não há horarios disponíveis para a data: " + mensagem, response.getBody());
    }

    @Test
    void deveRetornarConflictParaAgendamentoDuplicado() {
        AgendamentoDuplicadoException ex = new AgendamentoDuplicadoException();

        ResponseEntity<String> response = handler.handleDuplicateAgendamento(ex);

        assertNotNull(response);
        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Já existe um agendamento com essas informações.", response.getBody());
    }
}
