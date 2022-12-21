package org.springboot.microservices.security.orderservice.filters;

import lombok.AllArgsConstructor;
import org.springboot.microservices.security.orderservice.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = request.getHeader("username");
        String role = redisService.retrieve(username);

        if (username != null && role != null) {

            var simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            var preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(username, null, List.of(simpleGrantedAuthority));

            SecurityContextHolder.getContext().setAuthentication(preAuthenticatedAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}
