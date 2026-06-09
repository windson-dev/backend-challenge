package com.itau.jwtvalidator.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.jwtvalidator.exception.InvalidJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtParserServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JwtParserService jwtParserService;

    private static final String VALID_HEADER = "eyJhbGciOiJub25lIn0";
    private static final String VALID_PAYLOAD = "eyJOYW1lIjoiSm9obiIsIlJvbGUiOiJBZG1pbiIsIlNlZWQiOiI3In0";
    private static final String VALID_SIGNATURE = "signature";
    private static final String VALID_TOKEN = VALID_HEADER + "." + VALID_PAYLOAD + "." + VALID_SIGNATURE;

    @Test
    @DisplayName("Should throw InvalidJwtException when token is null")
    void shouldThrowExceptionWhenTokenIsNull() {
        assertThrows(InvalidJwtException.class, () -> jwtParserService.parse(null));
    }

    @Test
    @DisplayName("Should throw InvalidJwtException when token is blank")
    void shouldThrowExceptionWhenTokenIsBlank() {
        assertThrows(InvalidJwtException.class, () -> jwtParserService.parse("   "));
    }

    @Test
    @DisplayName("Should throw InvalidJwtException when token has less than 3 parts")
    void shouldThrowExceptionWhenTokenHasLessThan3Parts() {
        assertThrows(InvalidJwtException.class, () -> jwtParserService.parse("header.payload"));
    }

    @Test
    @DisplayName("Should throw InvalidJwtException when token has more than 3 parts")
    void shouldThrowExceptionWhenTokenHasMoreThan3Parts() {
        assertThrows(InvalidJwtException.class, () -> jwtParserService.parse("header.payload.signature.extra"));
    }

    @Test
    @DisplayName("Should parse valid JWT token successfully")
    void shouldParseValidToken() throws Exception {
        Map<String, Object> expectedClaims = Map.of(
                "Name", "John",
                "Role", "Admin",
                "Seed", "7"
        );

        when(objectMapper.readValue(
                anyString(),
                ArgumentMatchers.<TypeReference<Map<String, Object>>>any()
        )).thenReturn(expectedClaims);

        var jwt = jwtParserService.parse(VALID_TOKEN);

        assertNotNull(jwt);
        assertEquals(VALID_TOKEN, jwt.getTokenValue());
        assertEquals("John", jwt.getClaimAsString("Name"));
        assertEquals("Admin", jwt.getClaimAsString("Role"));
        assertEquals("7", jwt.getClaimAsString("Seed"));
    }
}
