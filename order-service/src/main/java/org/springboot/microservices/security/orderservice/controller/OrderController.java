package org.springboot.microservices.security.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-order")
    public void addOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(
                "Username : " + authentication.getPrincipal() + " - " +
                        "Role : " + authentication.getAuthorities() + " added!"
        );
    }

}
