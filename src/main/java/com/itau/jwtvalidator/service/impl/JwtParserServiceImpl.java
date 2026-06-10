package com.itau.jwtvalidator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.jwtvalidator.exception.InvalidJwtException;
import com.itau.jwtvalidator.service.JwtParserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtParserServiceImpl implements JwtParserService {

    private static final TypeReference<Map<String, Object>> MAP_TYPE =
            new TypeReference<>() {};

    private final ObjectMapper objectMapper;

    public Jwt parse(String token) {

        if (token == null || token.isBlank()) {
            throw new InvalidJwtException("JWT inválido");
        }

        var parts = token.split("\\.");

        if (parts.length != 3) {
            throw new InvalidJwtException("JWT inválido");
        }

        try {
            var claims = parseJson(decode(parts[1]));

            return Jwt.withTokenValue(token)
                    .header("alg", "none")
                    .claims(c -> c.putAll(claims))
                    .build();

        } catch (IllegalArgumentException e) {
            throw new InvalidJwtException("JWT inválido", e);
        } catch (Exception e) {
            throw new InvalidJwtException("JWT inválido", e);
        }
    }

    private String decode(String value) {
        return new String(
                Base64.getUrlDecoder().decode(value),
                StandardCharsets.UTF_8
        );
    }

    private Map<String, Object> parseJson(String json) throws Exception {
        return objectMapper.readValue(json, MAP_TYPE);
    }
}