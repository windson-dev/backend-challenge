package com.itau.jwtvalidator.service;

import com.itau.jwtvalidator.service.impl.JwtValidationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtValidationServiceImplTest {

    @InjectMocks
    private JwtValidationServiceImpl validationService;

    private Jwt buildJwt(Map<String, Object> claims) {
        return Jwt.withTokenValue("token")
                .header("alg", "none")
                .claims(c -> c.putAll(claims))
                .build();
    }

    @Test
    @DisplayName("Should return false when jwt is null")
    void shouldReturnFalseWhenJwtIsNull() {
        assertFalse(validationService.validate(null));
    }

    @Test
    @DisplayName("Should return true for valid JWT")
    void shouldReturnTrueForValidJwt() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "Admin", "Seed", "7"));
        assertTrue(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when claims are missing")
    void shouldReturnFalseWhenClaimsAreMissing() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "Admin"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when extra claims exist")
    void shouldReturnFalseWhenExtraClaimsExist() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "Admin", "Seed", "7", "Extra", "value"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when name is blank")
    void shouldReturnFalseWhenNameIsBlank() {
        var jwt = buildJwt(Map.of("Name", "", "Role", "Admin", "Seed", "7"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when name contains digits")
    void shouldReturnFalseWhenNameContainsDigits() {
        var jwt = buildJwt(Map.of("Name", "John123", "Role", "Admin", "Seed", "7"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when name exceeds max length")
    void shouldReturnFalseWhenNameExceedsMaxLength() {
        String longName = "A".repeat(257);
        var jwt = buildJwt(Map.of("Name", longName, "Role", "Admin", "Seed", "7"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when role is invalid")
    void shouldReturnFalseWhenRoleIsInvalid() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "InvalidRole", "Seed", "7"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when role is blank")
    void shouldReturnFalseWhenRoleIsBlank() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "", "Seed", "7"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when seed is not a number")
    void shouldReturnFalseWhenSeedIsNotNumber() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "Admin", "Seed", "abc"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when seed is not prime")
    void shouldReturnFalseWhenSeedIsNotPrime() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "Admin", "Seed", "4"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when seed is less than 2")
    void shouldReturnFalseWhenSeedIsLessThan2() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "Admin", "Seed", "1"));
        assertFalse(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return true with valid Member role")
    void shouldReturnTrueWithMemberRole() {
        var jwt = buildJwt(Map.of("Name", "Jane", "Role", "Member", "Seed", "11"));
        assertTrue(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return true with valid External role")
    void shouldReturnTrueWithExternalRole() {
        var jwt = buildJwt(Map.of("Name", "Bob", "Role", "External", "Seed", "13"));
        assertTrue(validationService.validate(jwt));
    }

    @Test
    @DisplayName("Should return false when seed is blank")
    void shouldReturnFalseWhenSeedIsBlank() {
        var jwt = buildJwt(Map.of("Name", "John", "Role", "Admin", "Seed", ""));
        assertFalse(validationService.validate(jwt));
    }
}
