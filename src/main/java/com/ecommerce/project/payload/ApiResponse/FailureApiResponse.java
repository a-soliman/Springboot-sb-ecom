package com.ecommerce.project.payload.ApiResponse;

public class FailureApiResponse extends ApiResponse<Void> {
    public FailureApiResponse(ApiResponseError error) {
        super(null, error, false);
    }

}
