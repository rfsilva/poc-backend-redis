package br.com.rodrigo.poc.cache.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso não é encontrado
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Construtor com mensagem de erro
     * 
     * @param message Mensagem descritiva do erro
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Construtor com mensagem e causa
     * 
     * @param message Mensagem descritiva do erro
     * @param cause Causa raiz da exceção
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}