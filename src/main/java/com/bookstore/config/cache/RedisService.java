package com.bookstore.config.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveAccessToken(String token, Long accessDurationMs){
        saveToken("access", token, accessDurationMs, TimeUnit.MILLISECONDS);
    }

    public void saveRefreshToken(String token, Long refreshDurationMs){
        saveToken("refresh", token,refreshDurationMs, TimeUnit.MILLISECONDS);
    }

    public void saveToken(String tokenType, String token, Long durationMs, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(tokenType+"Token", token, durationMs, timeUnit);
    }

    public String getAccessToken(){
        return getToken("access");
    }

    public String getRefreshToken(){
        return getToken("refresh");
    }

    public String getToken(String tokenType){
       return String.valueOf(redisTemplate.opsForValue().get(tokenType+"Token"));
    }

    public void invalidateTokens(){
        invalidateToken("access");
        invalidateToken("refresh");
    }

    public void invalidateToken(String tokenType){
        redisTemplate.delete(tokenType+"Token");
    }

}
