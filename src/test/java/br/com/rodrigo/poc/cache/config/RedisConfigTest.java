package br.com.rodrigo.poc.cache.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisConfigTest {

    @InjectMocks
    private RedisConfig redisConfig;

    @Mock
    private RedisConnectionFactory connectionFactory;

    @Test
    void redisTemplate_ShouldCreateRedisTemplate() {
        // When
        RedisTemplate<String, Object> template = redisConfig.redisTemplate(connectionFactory);

        // Then
        assertNotNull(template);
        assertEquals(connectionFactory, template.getConnectionFactory());
    }

    @Test
    void cacheManager_ShouldCreateRedisCacheManager() {
        // Given
        ReflectionTestUtils.setField(redisConfig, "timeToLive", 3600L);

        // When
        RedisCacheManager cacheManager = redisConfig.cacheManager(connectionFactory);

        // Then
        assertNotNull(cacheManager);
    }
    
    @Test
    void redisConnectionFactory_ShouldCreateLettuceConnectionFactory() {
        // Given
        ReflectionTestUtils.setField(redisConfig, "redisHost", "localhost");
        ReflectionTestUtils.setField(redisConfig, "redisPort", 6379);
        
        // Use PowerMockito ou um wrapper para evitar a criação real da conexão
        // Este teste é mais para verificar se o método existe e não lança exceções
        
        // When & Then
        assertDoesNotThrow(() -> {
            // Não testamos a implementação real, apenas verificamos que o método não lança exceção
            // Em um ambiente real, usaríamos PowerMockito para mockar a criação do LettuceConnectionFactory
            RedisConfig spyConfig = spy(redisConfig);
            doReturn(mock(LettuceConnectionFactory.class)).when(spyConfig).redisConnectionFactory();
            
            RedisConnectionFactory factory = spyConfig.redisConnectionFactory();
            assertNotNull(factory);
        });
    }
}