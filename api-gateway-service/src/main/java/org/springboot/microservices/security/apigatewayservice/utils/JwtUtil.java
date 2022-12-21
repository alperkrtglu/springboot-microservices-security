package org.springboot.microservices.security.apigatewayservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${secret-key}")
    private String secretKey;

    public boolean validateToken(final String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}
