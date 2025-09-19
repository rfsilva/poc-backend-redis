package br.com.rodrigo.poc.cache.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um CPF inválido é fornecido
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCpfException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public InvalidCpfException(String message) {
        super(message);
    }
}