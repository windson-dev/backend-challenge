package com.itau.jwtvalidator.service.impl;

import com.itau.jwtvalidator.exception.InvalidJwtException;
import com.itau.jwtvalidator.service.JwtParserService;
import com.itau.jwtvalidator.service.JwtService;
import com.itau.jwtvalidator.service.JwtValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtParserService jwtParserService;
    private final JwtValidationService jwtValidationService;

    public boolean validate(String token) {

        try {
            var jwt = jwtParserService.parse(token);

            return jwtValidationService.validate(jwt);

        } catch (InvalidJwtException e) {

            log.warn("JWT validation failed: {}", e.getMessage());

            return false;

        }
    }
}