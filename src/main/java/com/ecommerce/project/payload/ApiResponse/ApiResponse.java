package com.ecommerce.project.payload.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApiResponse<T> {
    private T data;
    private ApiResponseError error;
    private boolean success;
}
