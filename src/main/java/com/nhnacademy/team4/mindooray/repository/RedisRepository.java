package com.nhnacademy.team4.mindooray.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, String hashKey, Object value, long expireTimeSecond) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, expireTimeSecond, TimeUnit.SECONDS);
    }

    public Long getSessionAccountId(String key, String hashKey) {
        return (Long) redisTemplate.opsForHash().get(key, hashKey);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
