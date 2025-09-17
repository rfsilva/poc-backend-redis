package br.com.rodrigo.poc.cache.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void constructor_ShouldSetAllFields() {
        // Given
        int status = 404;
        String message = "Resource not found";
        LocalDateTime timestamp = LocalDateTime.now();
        String path = "/api/persons/1";

        // When
        GlobalExceptionHandler.ErrorResponse errorResponse = 
                new GlobalExceptionHandler.ErrorResponse(status, message, timestamp, path);

        // Then
        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(path, errorResponse.getPath());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Given
        GlobalExceptionHandler.ErrorResponse errorResponse = 
                new GlobalExceptionHandler.ErrorResponse(0, null, null, null);
        
        int status = 404;
        String message = "Resource not found";
        LocalDateTime timestamp = LocalDateTime.now();
        String path = "/api/persons/1";

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

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        // Given
        GlobalExceptionHandler.ErrorResponse errorResponse = 
                new GlobalExceptionHandler.ErrorResponse(404, "Not found", LocalDateTime.now(), "/api/persons/1");

        // When & Then
        assertEquals(errorResponse, errorResponse);
    }

    @Test
    void equals_WithEqualObject_ShouldReturnTrue() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        GlobalExceptionHandler.ErrorResponse errorResponse1 = 
                new GlobalExceptionHandler.ErrorResponse(404, "Not found", timestamp, "/api/persons/1");
        GlobalExceptionHandler.ErrorResponse errorResponse2 = 
                new GlobalExceptionHandler.ErrorResponse(404, "Not found", timestamp, "/api/persons/1");

        // When & Then
        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
    }

    @Test
    void equals_WithDifferentObject_ShouldReturnFalse() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        GlobalExceptionHandler.ErrorResponse errorResponse1 = 
                new GlobalExceptionHandler.ErrorResponse(404, "Not found", timestamp, "/api/persons/1");
        GlobalExceptionHandler.ErrorResponse errorResponse2 = 
                new GlobalExceptionHandler.ErrorResponse(500, "Server error", timestamp, "/api/persons/2");

        // When & Then
        assertNotEquals(errorResponse1, errorResponse2);
        assertNotEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        LocalDateTime timestamp = LocalDateTime.of(2023, 1, 1, 12, 0);
        GlobalExceptionHandler.ErrorResponse errorResponse = 
                new GlobalExceptionHandler.ErrorResponse(404, "Not found", timestamp, "/api/persons/1");

        // When
        String toString = errorResponse.toString();

        // Then
        assertTrue(toString.contains("status=404"));
        assertTrue(toString.contains("message=Not found"));
        assertTrue(toString.contains("timestamp=" + timestamp));
        assertTrue(toString.contains("path=/api/persons/1"));
    }
}