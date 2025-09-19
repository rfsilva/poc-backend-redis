package br.com.rodrigo.poc.cache.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

/**
 * Configuração para internacionalização de mensagens
 */
@Configuration
public class MessageConfig {

    /**
     * Define o resolvedor de locale baseado no cabeçalho Accept-Language da requisição
     * 
     * @return LocaleResolver configurado
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("pt", "BR")); // Português do Brasil como padrão
        localeResolver.setSupportedLocales(Arrays.asList(
                new Locale("pt", "BR"),
                Locale.ENGLISH,
                new Locale("es"),
                Locale.ITALIAN
        ));
        return localeResolver;
    }

    /**
     * Define a fonte de mensagens para internacionalização
     * 
     * @return MessageSource configurado
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}