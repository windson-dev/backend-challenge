package com.itau.jwtvalidator.service;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtValidationService {
    boolean validate(Jwt jwt);
}
