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
     * Constantes relacionadas a códigos de mensagens
     */
    public static final class MessageCodes {
        private MessageCodes() {
            // Construtor privado para evitar instanciação
        }
        
        public static final String PERSON_NOT_FOUND = "error.person.notFound";
        public static final String VALIDATION_ERROR = "error.validation";
        public static final String UNEXPECTED_ERROR = "error.unexpected";
        public static final String INVALID_CPF = "error.cpf.invalid";
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