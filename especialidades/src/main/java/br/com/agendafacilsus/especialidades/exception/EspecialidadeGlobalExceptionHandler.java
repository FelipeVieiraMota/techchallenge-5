package br.com.agendafacilsus.especialidades.exception;

import br.com.agendafacilsus.commonlibrary.domain.exception.EspecialidadeNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EspecialidadeGlobalExceptionHandler {
    @ExceptionHandler(EspecialidadeNaoEncontradaException.class)
    public ResponseEntity<String> handleEspecialidadeNotFound(EspecialidadeNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EspecialidadeDuplicadaException.class)
    public ResponseEntity<String> handleDuplicateEspecialidade(EspecialidadeDuplicadaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
