package br.com.rodrigo.poc.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Aplicação principal para demonstração de cache com Redis
 */
@SpringBootApplication
@EnableCaching
public class RedisDemoApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }
}