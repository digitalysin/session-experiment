package com.example.demo.services;

import java.time.Duration;
import java.util.Optional;

public interface SessionPool {
    void lock(final String key);
    Optional<Session> get(final String key);
    boolean set(final String key, final Session session, final Duration expired);
}
