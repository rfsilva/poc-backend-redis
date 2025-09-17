package br.com.rodrigo.poc.cache.config;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RedisConfigTest {

    @Test
    void redisConnectionFactory_ShouldCreateLettuceConnectionFactory() {
        // Given
        RedisConfig redisConfig = new RedisConfig();
        ReflectionTestUtils.setField(redisConfig, "redisHost", "localhost");
        ReflectionTestUtils.setField(redisConfig, "redisPort", 6379);

        // When
        LettuceConnectionFactory factory = redisConfig.redisConnectionFactory();

        // Then
        assertNotNull(factory);
        assertEquals("localhost", factory.getHostName());
        assertEquals(6379, factory.getPort());
    }

    @Test
    void redisTemplate_ShouldCreateRedisTemplate() {
        // Given
        RedisConfig redisConfig = new RedisConfig();
        RedisConnectionFactory connectionFactory = mock(RedisConnectionFactory.class);

        // When
        RedisTemplate<String, Object> template = redisConfig.redisTemplate(connectionFactory);

        // Then
        assertNotNull(template);
        assertEquals(connectionFactory, template.getConnectionFactory());
    }

    @Test
    void cacheManager_ShouldCreateRedisCacheManager() {
        // Given
        RedisConfig redisConfig = new RedisConfig();
        ReflectionTestUtils.setField(redisConfig, "timeToLive", 3600L);
        RedisConnectionFactory connectionFactory = mock(RedisConnectionFactory.class);

        // When
        RedisCacheManager cacheManager = redisConfig.cacheManager(connectionFactory);

        // Then
        assertNotNull(cacheManager);
    }
}