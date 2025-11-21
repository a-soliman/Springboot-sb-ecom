package com.ecommerce.project.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceListEmptyException extends RuntimeException {
    String resourceName;

    public ResourceListEmptyException(String resourceName) {
        super(String.format("%s list is empty", resourceName));
        this.resourceName = resourceName;
    }
}
