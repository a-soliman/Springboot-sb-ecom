package com.ecommerce.project.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class JsonException {
    @Getter
    private String message;

    @Getter
    private HttpStatus statusCode;
}
