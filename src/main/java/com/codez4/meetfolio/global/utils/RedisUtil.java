package com.codez4.meetfolio.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, String> redisBlackListTemplate;
    private final RedisTemplate<String, String> redisEmailAuthTemplate;

    public void set(String key, String value, int minutes) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setBlackList(String key, String value, Long milliSeconds) {
        redisBlackListTemplate.setValueSerializer(new StringRedisSerializer());
        redisBlackListTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public Object getBlackList(String key) {
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return redisBlackListTemplate.delete(key);
    }

    public boolean hasKeyBlackList(String key) {
        return redisBlackListTemplate.hasKey(key);
    }

    public void setRedisEmailAuth(String key, String value, int minutes) {
        redisEmailAuthTemplate.setKeySerializer(new StringRedisSerializer());
        redisEmailAuthTemplate.setValueSerializer(new StringRedisSerializer());
        redisEmailAuthTemplate.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }

    public String getEmailAuth(String key) {
        return redisEmailAuthTemplate.opsForValue().get(key);
    }

    public boolean deleteEmailAuth(String key) {
        return Boolean.TRUE.equals(redisEmailAuthTemplate.delete(key));
    }

    public boolean hasEmailAuthKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
