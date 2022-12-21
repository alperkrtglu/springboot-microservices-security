package org.springboot.microservices.security.authorizationservice.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    @Value("${token-expire-time}")
    private long tokenExpireTime;

    @Async
    public void setIfAbsent(String key, String value) {
        redisTemplate.opsForValue()
                .setIfAbsent(key, value, tokenExpireTime, TimeUnit.MILLISECONDS);
    }

}
