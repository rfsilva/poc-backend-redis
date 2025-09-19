package br.com.rodrigo.poc.cache.controller;

import br.com.rodrigo.poc.cache.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Controlador para testar a internacionalização
 */
@RestController
@RequestMapping("/api/locale")
public class LocaleController {

    private final MessageService messageService;

    public LocaleController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Retorna mensagens internacionalizadas para teste
     * 
     * @param locale Locale a ser usado (opcional)
     * @return Mapa com mensagens internacionalizadas
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testLocale(@RequestParam(required = false) Locale locale) {
        Map<String, String> messages = new HashMap<>();
        
        // Adiciona algumas mensagens de teste
        messages.put("error.person.notFound", messageService.getMessage("error.person.notFound", new Object[]{"123"}));
        messages.put("error.validation", messageService.getMessage("error.validation"));
        messages.put("error.unexpected", messageService.getMessage("error.unexpected"));
        messages.put("error.cpf.invalid", messageService.getMessage("error.cpf.invalid"));
        
        // Adiciona mensagens de gênero
        messages.put("gender.male", messageService.getMessage("gender.male"));
        messages.put("gender.female", messageService.getMessage("gender.female"));
        
        // Adiciona alguns países
        messages.put("country.BRA", messageService.getMessage("country.BRA"));
        messages.put("country.USA", messageService.getMessage("country.USA"));
        messages.put("country.ESP", messageService.getMessage("country.ESP"));
        messages.put("country.ITA", messageService.getMessage("country.ITA"));
        
        return ResponseEntity.ok(messages);
    }
}