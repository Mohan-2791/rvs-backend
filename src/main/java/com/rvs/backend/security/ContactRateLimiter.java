package com.rvs.backend.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ContactRateLimiter {

    private static final int MAX_REQUESTS_PER_WINDOW = 5;
    private static final long MAX_TRACKED_KEYS = 10_000;

    private final Cache<String, AtomicInteger> requestCounts = Caffeine.newBuilder()
            .maximumSize(MAX_TRACKED_KEYS)
            .expireAfterWrite(Duration.ofHours(1))
            .build();

    public boolean tryConsume(String clientKey) {
        AtomicInteger count = requestCounts.get(clientKey, key -> new AtomicInteger(0));
        if (count == null) {
            return false;
        }
        return count.incrementAndGet() <= MAX_REQUESTS_PER_WINDOW;
    }
}
