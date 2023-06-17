package com.nhnacademy.team4.mindooray.global.repository;

import com.nhnacademy.team4.mindooray.global.exception.RedisSessionAccountIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, String hashKey, Object value, long expireTimeSecond) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, expireTimeSecond, TimeUnit.SECONDS);
    }

    public Object get(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Long getSessionAccountId(String key) {
        Object o = redisTemplate.opsForHash().get(key, "accountId");
        if(Objects.isNull(o)) {
            throw new RedisSessionAccountIdNotFoundException();
        }
        return Long.parseLong(String.valueOf(o));
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
