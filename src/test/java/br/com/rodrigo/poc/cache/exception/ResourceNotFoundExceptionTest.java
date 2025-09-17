package br.com.rodrigo.poc.cache.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessage() {
        // Given
        String errorMessage = "Resource not found";

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        // Then
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void constructor_WithMessageAndCause_ShouldSetMessageAndCause() {
        // Given
        String errorMessage = "Resource not found";
        Throwable cause = new RuntimeException("Original cause");

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage, cause);

        // Then
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}