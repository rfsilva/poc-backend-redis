package br.com.rodrigo.poc.cache.util;

/**
 * Constantes utilizadas na aplicação
 */
public final class Constants {
    
    private Constants() {
        // Construtor privado para evitar instanciação
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Constantes relacionadas ao cache
     */
    public static final class Cache {
        private Cache() {
            // Construtor privado para evitar instanciação
        }
        
        public static final String PERSON_CACHE = "personCache";
        public static final String ALL_PERSONS_CACHE = "allPersonsCache";
    }
    
    /**
     * Constantes relacionadas a mensagens de erro
     */
    public static final class ErrorMessages {
        private ErrorMessages() {
            // Construtor privado para evitar instanciação
        }
        
        public static final String PERSON_NOT_FOUND = "Person not found with id: ";
        public static final String VALIDATION_ERROR = "Validation error";
        public static final String UNEXPECTED_ERROR = "An unexpected error occurred. Please contact support.";
        public static final String INVALID_CPF = "Invalid CPF. Please provide a valid CPF number.";
    }
    
    /**
     * Constantes relacionadas a endpoints da API
     */
    public static final class Endpoints {
        private Endpoints() {
            // Construtor privado para evitar instanciação
        }
        
        public static final String API_BASE = "/api";
        public static final String PERSONS = "/persons";
        public static final String PERSONS_ID = "/persons/{id}";
        public static final String PERSONS_SEARCH = "/persons/search";
        public static final String CACHE_CLEAR = "/cache/clear";
    }
}