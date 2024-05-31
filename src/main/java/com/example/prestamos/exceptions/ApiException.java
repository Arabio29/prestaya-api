package com.example.prestamos.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private HttpStatus statusCode;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
