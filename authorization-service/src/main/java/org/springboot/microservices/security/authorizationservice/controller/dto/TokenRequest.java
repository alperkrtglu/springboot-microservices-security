package org.springboot.microservices.security.authorizationservice.controller.dto;

import lombok.Data;

@Data
public class TokenRequest {

    private String username;
    private String role;

}
