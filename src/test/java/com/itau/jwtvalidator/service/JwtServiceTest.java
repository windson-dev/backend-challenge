package com.itau.jwtvalidator.service;

import com.itau.jwtvalidator.exception.InvalidJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtParserService jwtParserService;

    @Mock
    private JwtValidationService validationService;

    @InjectMocks
    private JwtService jwtService;

    private Jwt buildJwt() {
        return Jwt.withTokenValue("token")
                .header("alg", "none")
                .claims(c -> c.putAll(Map.of("Name", "John", "Role", "Admin", "Seed", "7")))
                .build();
    }

    @Test
    @DisplayName("Should return true when token is valid")
    void shouldReturnTrueWhenTokenIsValid() {
        var jwt = buildJwt();

        when(jwtParserService.parse(anyString())).thenReturn(jwt);
        when(validationService.validate(jwt)).thenReturn(true);

        assertTrue(jwtService.validate("valid.token.here"));
    }

    @Test
    @DisplayName("Should return false when parser throws InvalidJwtException")
    void shouldReturnFalseWhenParserThrowsException() {
        when(jwtParserService.parse(anyString())).thenThrow(new InvalidJwtException("JWT inválido"));

        assertFalse(jwtService.validate("invalid-token"));
    }

    @Test
    @DisplayName("Should return false when validation fails")
    void shouldReturnFalseWhenValidationFails() {
        var jwt = buildJwt();

        when(jwtParserService.parse(anyString())).thenReturn(jwt);
        when(validationService.validate(jwt)).thenReturn(false);

        assertFalse(jwtService.validate("valid.token.here"));
    }

    @Test
    @DisplayName("Should return false when token is null")
    void shouldReturnFalseWhenTokenIsNull() {
        when(jwtParserService.parse(null)).thenThrow(new InvalidJwtException("JWT inválido"));

        assertFalse(jwtService.validate(null));
    }
}
