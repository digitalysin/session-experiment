package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class SessionPoolImpl implements SessionPool {

    private RedisTemplate<String, Session> redisTemplate;


    private RedisLockRegistry locker;

    public SessionPoolImpl(RedisTemplate<String, Session> redisTemplate, RedisLockRegistry locker) {
        this.redisTemplate = redisTemplate;
        this.locker = locker;
    }

    @Override
    public void lock(String key) {
        final var lock = locker.obtain(key);
        lock.unlock();
    }

    @Override
    public Optional<Session> get(String key) {
        final var ops = redisTemplate.opsForValue();
        return Optional.ofNullable(ops.get(key));
    }

    @Override
    public boolean set(String key, Session session, final Duration expired) {
        redisTemplate
                .opsForValue()
                .set(key, session, expired);
        return true;
    }
}
