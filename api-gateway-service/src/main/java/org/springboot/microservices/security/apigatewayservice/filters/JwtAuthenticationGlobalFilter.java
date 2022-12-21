package org.springboot.microservices.security.apigatewayservice.filters;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springboot.microservices.security.apigatewayservice.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
@Component
public class JwtAuthenticationGlobalFilter implements GlobalFilter {

    private static final List<String> AUTH_ENDPOINTS = List.of("/login", "/register");

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (isApiSecured(request)) {

            if (!request.getHeaders().containsKey("Authorization")) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            final String token = request
                    .getHeaders().getOrEmpty("Authorization").get(0);

            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getClaims(token);
                request.mutate()
                        .header("username", (String) claims.get("username"))
                        .build();
            } else {
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }
        }

        return chain.filter(exchange);
    }

    private boolean isApiSecured(ServerHttpRequest request) {
        Predicate<ServerHttpRequest> isApiSecured = serverHttpRequest -> AUTH_ENDPOINTS.stream()
                .noneMatch(authEndpoint -> request.getURI().getPath().contains(authEndpoint));

        return isApiSecured.test(request);
    }

}
