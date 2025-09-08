package com.bookstore.config.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveAccessToken(String token, Long accessDurationMs, String userEmail) {
        saveToken("access", userEmail, token, accessDurationMs);
    }

    public void saveRefreshToken(String token, Long refreshDurationMs, String userEmail) {
        saveToken("refresh", userEmail, token, refreshDurationMs);
    }

    private void saveToken(String tokenType, String userEmail, String token, Long durationMs) {
        String key = tokenType + ":" + userEmail;
        redisTemplate.opsForValue().set(key, token, durationMs, TimeUnit.MILLISECONDS);
    }

    public String getAccessToken(String userEmail) {
        return getToken("access", userEmail);
    }

    public String getRefreshToken(String userEmail) {
        return getToken("refresh", userEmail);
    }

    private String getToken(String tokenType, String userEmail) {
        return redisTemplate.opsForValue().get(tokenType + ":" + userEmail);
    }

    public boolean isAccessTokenValid(String userEmail, String expectedValue) {
        String accessToken = redisTemplate.opsForValue().get("access:" + userEmail);

        return Objects.equals(accessToken, expectedValue);
    }

    public boolean isRefreshTokenValid(String userEmail, String expectedValue) {
        String refreshToken = redisTemplate.opsForValue().get("refresh:" + userEmail);

        return Objects.equals(refreshToken, expectedValue);
    }

    public void invalidateTokens(String userEmail) {
        invalidateToken("access", userEmail);
        invalidateToken("refresh", userEmail);
    }

    public void invalidateToken(String tokenType, String userEmail) {
        redisTemplate.delete(tokenType + ":" + userEmail);
    }

}
