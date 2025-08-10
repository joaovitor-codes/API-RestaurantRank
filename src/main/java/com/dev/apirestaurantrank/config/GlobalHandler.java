package com.dev.apirestaurantrank.config;

import com.dev.apirestaurantrank.exception.DataIntegrityViolationException;
import com.dev.apirestaurantrank.exception.ExceptionResponse;
import com.dev.apirestaurantrank.exception.MethodArgumentNotValidException;
import com.dev.apirestaurantrank.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.NotActiveException;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(NotActiveException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotActiveException e) {
        var exception = new ExceptionResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        var exception = new ExceptionResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var exception = new ExceptionResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException() {
        String message = "Violação de integridade de dados. Verifique os dados de entrada.";
        var exception = new ExceptionResponse(
                message,
                HttpStatus.CONFLICT.name(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        var exception = new ExceptionResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
