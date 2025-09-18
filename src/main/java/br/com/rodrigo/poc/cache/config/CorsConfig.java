package br.com.rodrigo.poc.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuração CORS para permitir requisições de origens diferentes
 */
@Configuration
public class CorsConfig {

    /**
     * Configura o filtro CORS para permitir requisições do frontend
     * 
     * @return CorsFilter configurado
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permite credenciais
        config.setAllowCredentials(true);
        
        // Origens permitidas - adicione seu frontend aqui
        config.addAllowedOrigin("http://localhost:4200");
        
        // Métodos HTTP permitidos
        config.addAllowedMethod("*");
        
        // Headers permitidos
        config.addAllowedHeader("*");
        
        // Expõe todos os headers na resposta
        config.addExposedHeader("*");
        
        // Aplica esta configuração para todos os endpoints
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}