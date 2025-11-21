package com.ecommerce.project.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.constraints.NotNull;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        final Map<String, String> response = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });

        logger.error(response.toString());
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<JsonException> handleResourceNotFoundException(ResourceNotFoundException ex) {
        final JsonException jsonException = new JsonException(ex.getMessage(), HttpStatus.NOT_FOUND);
        logger.error("Resource Not found exception, " + jsonException.toString());
        return new ResponseEntity<JsonException>(jsonException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceUniquenessViolationException.class)
    public ResponseEntity<JsonException> handleResourceUniquenessViolationException(
            ResourceUniquenessViolationException ex) {
        final JsonException jsonException = new JsonException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        logger.error("Resource Uniqueness exception, " + ex.getMessage());
        return new ResponseEntity<JsonException>(jsonException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceListEmptyException.class)
    public ResponseEntity<JsonException> handleResourceListEmptyException(
            ResourceListEmptyException ex) {
        final JsonException jsonException = new JsonException(ex.getMessage(), HttpStatus.NOT_FOUND);
        logger.error("Resource List Empty exception, " + ex.getMessage());
        return new ResponseEntity<JsonException>(jsonException, HttpStatus.NOT_FOUND);
    }

}
