package br.com.rodrigo.poc.cache.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void customOpenAPI_ShouldReturnConfiguredOpenAPI() {
        // Given
        OpenApiConfig openApiConfig = new OpenApiConfig();

        // When
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Then
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        
        Info info = openAPI.getInfo();
        assertEquals("Person API with Redis Cache", info.getTitle());
        assertEquals("API REST para gerenciamento de pessoas com cache Redis", info.getDescription());
        assertEquals("1.0.0", info.getVersion());
        
        assertNotNull(info.getContact());
        assertEquals("Equipe de Desenvolvimento", info.getContact().getName());
        assertEquals("dev@example.com", info.getContact().getEmail());
        assertEquals("https://example.com", info.getContact().getUrl());
        
        assertNotNull(info.getLicense());
        assertEquals("Apache 2.0", info.getLicense().getName());
        assertEquals("https://www.apache.org/licenses/LICENSE-2.0.html", info.getLicense().getUrl());
    }
}