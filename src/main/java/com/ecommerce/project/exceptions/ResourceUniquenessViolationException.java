package com.ecommerce.project.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceUniquenessViolationException extends RuntimeException {
    String resourceName;
    String field;
    String fieldValue;

    public ResourceUniquenessViolationException(String resourceName, String field, String fieldValue) {
        super(String.format("%s already exists with field %s : %s", resourceName, field, fieldValue));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldValue = fieldValue;
    }

}
