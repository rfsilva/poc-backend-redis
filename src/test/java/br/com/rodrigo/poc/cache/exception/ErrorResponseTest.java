package br.com.rodrigo.poc.cache.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void errorResponse_ShouldHaveCorrectProperties() {
        // Given
        int status = 404;
        String message = "Resource not found";
        LocalDateTime timestamp = LocalDateTime.now();
        String path = "/api/persons/1";
        
        // When
        GlobalExceptionHandler.ErrorResponse errorResponse = new GlobalExceptionHandler.ErrorResponse(
                status, message, timestamp, path);
        
        // Then
        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(path, errorResponse.getPath());
    }
    
    @Test
    void errorResponse_ShouldSetProperties() {
        // Given
        GlobalExceptionHandler.ErrorResponse errorResponse = new GlobalExceptionHandler.ErrorResponse(
                0, null, null, null);
        
        int status = 500;
        String message = "Internal server error";
        LocalDateTime timestamp = LocalDateTime.now();
        String path = "/api/persons";
        
        // When
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(timestamp);
        errorResponse.setPath(path);
        
        // Then
        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(path, errorResponse.getPath());
    }
}