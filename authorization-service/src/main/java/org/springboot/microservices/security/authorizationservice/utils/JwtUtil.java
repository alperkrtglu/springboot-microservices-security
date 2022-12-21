package org.springboot.microservices.security.authorizationservice.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${secret-key}")
    private String secretKey;

    @Value("${token-expire-time}")
    private long tokenExpireTime;

    public String generateToken(String username, String role) {

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenExpireTime;

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

}
