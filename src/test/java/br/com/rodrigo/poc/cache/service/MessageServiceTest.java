package br.com.rodrigo.poc.cache.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    void getMessage_WithoutArgs_ShouldReturnMessage() {
        // Given
        String code = "error.person.notFound";
        String expectedMessage = "Person not found";
        when(messageSource.getMessage(eq(code), eq(null), any(Locale.class))).thenReturn(expectedMessage);

        // When
        String result = messageService.getMessage(code);

        // Then
        assertEquals(expectedMessage, result);
    }

    @Test
    void getMessage_WithArgs_ShouldReturnFormattedMessage() {
        // Given
        String code = "error.person.notFound";
        Object[] args = new Object[]{"123"};
        String expectedMessage = "Person not found with id: 123";
        when(messageSource.getMessage(eq(code), eq(args), any(Locale.class))).thenReturn(expectedMessage);

        // When
        String result = messageService.getMessage(code, args);

        // Then
        assertEquals(expectedMessage, result);
    }
}