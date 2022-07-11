package com.example.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        final var factory = new JedisConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(6379);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory factory) {
        final var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        return template;
    }

    @Bean
    public RedisLockRegistry redisLockRegistry(final RedisConnectionFactory factory) {
        final var registry = new RedisLockRegistry(factory, "session-lock");
        registry.setRedisLockType(RedisLockRegistry.RedisLockType.SPIN_LOCK);
        return registry;
    }
}
