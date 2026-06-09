package com.itau.jwtvalidator.service;

import com.itau.jwtvalidator.exception.InvalidJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtParserService jwtParserService;
    private final JwtValidationService validationService;

    public boolean validate(String token) {

        try {
            var jwt = jwtParserService.parse(token);

            return validationService.validate(jwt);

        } catch (InvalidJwtException e) {

            log.warn("JWT validation failed: {}", e.getMessage());

            return false;

        }
    }
}