package org.springboot.microservices.security.authorizationservice.controller;

import lombok.AllArgsConstructor;
import org.springboot.microservices.security.authorizationservice.controller.dto.TokenRequest;
import org.springboot.microservices.security.authorizationservice.redis.RedisService;
import org.springboot.microservices.security.authorizationservice.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final TokenRequest dto) {
        String token = jwtUtil.generateToken(dto.getUsername(), dto.getRole());
        redisService.setIfAbsent(dto.getUsername(), dto.getRole());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
