package com.itau.jwtvalidator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class JwtValidationService {

    private static final Set<String> VALID_ROLES =
            Set.of("Admin", "Member", "External");

    private static final Set<String> REQUIRED_CLAIMS =
            Set.of("Name", "Role", "Seed");

    private static final int MAX_NAME_LENGTH = 256;

    public boolean validate(Jwt jwt) {

        if (jwt == null) {
            return false;
        }

        var claims = jwt.getClaims();

        return hasOnlyRequiredClaims(claims)
                && isValidName(jwt.getClaimAsString("Name"))
                && isValidRole(jwt.getClaimAsString("Role"))
                && isValidSeed(jwt.getClaimAsString("Seed"));
    }

    private boolean hasOnlyRequiredClaims(Map<String, Object> claims) {
        return claims != null
                && claims.keySet().equals(REQUIRED_CLAIMS);
    }
    private boolean isValidName(String name) {

        if (name == null || name.isBlank()) {
            return false;
        }

        if (name.length() > MAX_NAME_LENGTH) {
            return false;
        }

        return !name.matches(".*\\d.*");
    }

    private boolean isValidRole(String role) {

        if (role == null || role.isBlank()) {
            return false;
        }

        return VALID_ROLES.contains(role);
    }

    private boolean isValidSeed(String seed) {

        if (seed == null || seed.isBlank()) {
            return false;
        }

        try {

            long n = Long.parseLong(seed);

            if (n <= 1) {
                return false;
            }

            for (long i = 2; i * i <= n; i++) {
                if (n % i == 0) {
                    return false;
                }
            }

            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}