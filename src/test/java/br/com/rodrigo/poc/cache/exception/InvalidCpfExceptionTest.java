package br.com.rodrigo.poc.cache.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InvalidCpfExceptionTest {

    @Test
    void constructor_ShouldSetMessage() {
        String errorMessage = "Invalid CPF";
        InvalidCpfException exception = new InvalidCpfException(errorMessage);
        
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
    
    @Test
    void annotation_ShouldHaveCorrectHttpStatus() {
        HttpStatus status = InvalidCpfException.class.getAnnotation(org.springframework.web.bind.annotation.ResponseStatus.class).value();
        assertEquals(HttpStatus.BAD_REQUEST, status);
    }
}