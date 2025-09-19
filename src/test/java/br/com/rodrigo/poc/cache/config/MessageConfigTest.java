package br.com.rodrigo.poc.cache.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import static org.junit.jupiter.api.Assertions.*;

class MessageConfigTest {

    private final MessageConfig messageConfig = new MessageConfig();

    @Test
    void localeResolver_ShouldReturnAcceptHeaderLocaleResolver() {
        // When
        LocaleResolver resolver = messageConfig.localeResolver();

        // Then
        assertNotNull(resolver);
        assertTrue(resolver instanceof AcceptHeaderLocaleResolver);
    }

    @Test
    void messageSource_ShouldBeConfiguredCorrectly() {
        // When
        MessageSource messageSource = messageConfig.messageSource();

        // Then
        assertNotNull(messageSource);
        assertTrue(messageSource.toString().contains("basename=messages"));
    }
}