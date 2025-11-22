package com.ecommerce.project.payload.ApiResponse;

public class SuccessfulApiResponse<T> extends ApiResponse<T> {
    public SuccessfulApiResponse(T data) {
        super(data, null, true);
    }
}
