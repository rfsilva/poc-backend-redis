package br.com.rodrigo.poc.cache.exception;

import br.com.rodrigo.poc.cache.service.MessageService;
import br.com.rodrigo.poc.cache.util.Constants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Test
    void handleResourceNotFoundException_ShouldReturnNotFoundStatus() {
        // Given
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        when(webRequest.getDescription(false)).thenReturn("test-request");

        // When
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = exceptionHandler.handleResourceNotFoundException(ex, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("Resource not found", response.getBody().getMessage());
        assertEquals("test-request", response.getBody().getPath());
    }
    
    @Test
    void handleInvalidCpfException_ShouldReturnBadRequestStatus() {
        // Given
        InvalidCpfException ex = new InvalidCpfException("Invalid CPF");
        when(webRequest.getDescription(false)).thenReturn("test-request");

        // When
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = exceptionHandler.handleInvalidCpfException(ex, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Invalid CPF", response.getBody().getMessage());
        assertEquals("test-request", response.getBody().getPath());
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequestStatus() {
        // Given
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "error message");
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

        // When
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationExceptions(ex, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("field"));
        assertEquals("error message", response.getBody().get("field"));
    }

    @Test
    void handleConstraintViolation_ShouldReturnBadRequestStatus() {
        // Given
        @SuppressWarnings("unchecked")
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        
        when(violation.getPropertyPath()).thenReturn(path);
        when(path.toString()).thenReturn("field");
        when(violation.getMessage()).thenReturn("error message");
        
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation);
        
        ConstraintViolationException ex = mock(ConstraintViolationException.class);
        when(ex.getConstraintViolations()).thenReturn(violations);

        // When
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleConstraintViolation(ex, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("field"));
        assertEquals("error message", response.getBody().get("field"));
    }

    @Test
    void handleGlobalException_ShouldReturnInternalServerErrorStatus() {
        // Given
        Exception ex = new Exception("Unexpected error");
        when(webRequest.getDescription(false)).thenReturn("test-request");
        when(messageService.getMessage(Constants.MessageCodes.UNEXPECTED_ERROR)).thenReturn("An unexpected error occurred. Please contact support.");

        // When
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = exceptionHandler.handleGlobalException(ex, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals("An unexpected error occurred. Please contact support.", response.getBody().getMessage());
        assertEquals("test-request", response.getBody().getPath());
    }
}