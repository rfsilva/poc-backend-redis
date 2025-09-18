package br.com.rodrigo.poc.cache.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Configuração de cache para testes que usa um cache em memória
 * em vez de depender do Redis
 */
@TestConfiguration
public class TestCacheConfiguration {

    /**
     * Cria um gerenciador de cache em memória para testes
     * 
     * @return Gerenciador de cache em memória
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}