package com.example.prestamos.models.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomResponse<T> {
    private HttpStatus status;
    private T data;
    private ApiError error;

    private CustomResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
        this.error = null;
    }

    private CustomResponse(HttpStatus status, String errorMessage) {
        this.status = status;
        this.data = null;
        this.error = new ApiError(errorMessage);
    }

    public static <T> CustomResponse<T> success(T data) {
        return new CustomResponse<>(HttpStatus.OK, data);
    }

    public static <T> CustomResponse<T> error(HttpStatus status, String errorMessage) {
        return new CustomResponse<>(status, errorMessage);
    }

}