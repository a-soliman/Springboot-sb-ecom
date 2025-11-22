package com.ecommerce.project.exceptions;

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

import com.ecommerce.project.payload.ApiResponse.ApiResponse;
import com.ecommerce.project.payload.ApiResponse.ApiResponseError;
import com.ecommerce.project.payload.ApiResponse.FailureApiResponse;

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
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        final ApiResponseError apiResponseError = ApiResponseError.builder().message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND).build();
        final FailureApiResponse response = new FailureApiResponse(apiResponseError);
        logger.error("❌ Resource Not found exception, " + response.getError().toString());
        return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceUniquenessViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceUniquenessViolationException(
            ResourceUniquenessViolationException ex) {
        final ApiResponseError apiResponseError = ApiResponseError.builder().message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST).build();
        final FailureApiResponse response = new FailureApiResponse(apiResponseError);
        logger.error("❌ Resource Uniqueness exception, " + response.getError().toString());
        return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceListEmptyException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceListEmptyException(
            ResourceListEmptyException ex) {
        final ApiResponseError apiResponseError = ApiResponseError.builder().message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND).build();
        final FailureApiResponse response = new FailureApiResponse(apiResponseError);
        logger.error("❌ Resource List Empty exception, " + response.getError().toString());
        return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.NOT_FOUND);
    }

}
