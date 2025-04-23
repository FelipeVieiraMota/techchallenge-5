package br.com.agendafacilsus.agendamentos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AgendamentoGlobalExceptionHandler {
    @ExceptionHandler(AgendamentoNaoEncontradoException.class)
    public ResponseEntity<String> handleAgendamentoNotFound(AgendamentoNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HorarioNaoDisponivelException.class)
    public ResponseEntity<String> handleHorarioNotFound(HorarioNaoDisponivelException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AgendamentoDuplicadoException.class)
    public ResponseEntity<String> handleDuplicateAgendamento(AgendamentoDuplicadoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
