package org.springboot.microservices.security.orderservice.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public String retrieve(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
