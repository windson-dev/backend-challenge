package com.itau.jwtvalidator.service;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtParserService {
    Jwt parse(String token);
}
