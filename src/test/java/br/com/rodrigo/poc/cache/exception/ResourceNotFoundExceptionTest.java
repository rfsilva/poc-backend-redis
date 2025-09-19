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
}