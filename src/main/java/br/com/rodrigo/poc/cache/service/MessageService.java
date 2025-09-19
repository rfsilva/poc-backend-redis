package br.com.rodrigo.poc.cache.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Serviço para obtenção de mensagens internacionalizadas
 */
@Service
public class MessageService {

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Obtém uma mensagem internacionalizada pelo código
     * 
     * @param code Código da mensagem
     * @return Mensagem internacionalizada
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * Obtém uma mensagem internacionalizada pelo código com parâmetros
     * 
     * @param code Código da mensagem
     * @param args Argumentos para substituição na mensagem
     * @return Mensagem internacionalizada com parâmetros substituídos
     */
    public String getMessage(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }
}