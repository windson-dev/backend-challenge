package com.itau.jwtvalidator.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtValidationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private String buildJwt(String payloadJson) {
        var encoder = Base64.getUrlEncoder().withoutPadding();
        var header = encoder.encodeToString("{\"alg\":\"none\"}".getBytes());
        var payload = encoder.encodeToString(payloadJson.getBytes());
        var signature = encoder.encodeToString("signature".getBytes());
        return "%s.%s.%s".formatted(header, payload, signature);
    }

    private void assertValidation(String token, boolean expected) throws Exception {
        mockMvc.perform(post("/api/v1/jwt/validate")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(token != null ? token : ""))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expected)));
    }

    @Test
    @DisplayName("Should return true for valid JWT")
    void caso1_validJwt() throws Exception {
        var token = buildJwt("""
                {"Name":"Toninho Araujo","Role":"Admin","Seed":"7841"}""");
        assertValidation(token, true);
    }

    @Test
    @DisplayName("Should return false when Name contains digits")
    void caso2_nameWithDigits() throws Exception {
        var token = buildJwt("""
                {"Name":"M4ria Silva","Role":"External","Seed":"7841"}""");
        assertValidation(token, false);
    }

    @Test
    @DisplayName("Should return false when JWT has extra claims")
    void caso3_extraClaims() throws Exception {
        var token = buildJwt("""
                {"Name":"Toninho Araujo","Role":"Admin","Seed":"7841","Extra":"value"}""");
        assertValidation(token, false);
    }

    @Test
    @DisplayName("Should return false for invalid Role")
    void invalidRole() throws Exception {
        var token = buildJwt("""
                {"Name":"Toninho Araujo","Role":"Manager","Seed":"7841"}""");
        assertValidation(token, false);
    }

    @Test
    @DisplayName("Should return false for non-prime Seed")
    void nonPrimeSeed() throws Exception {
        var token = buildJwt("""
                {"Name":"Toninho Araujo","Role":"Admin","Seed":"10"}""");
        assertValidation(token, false);
    }

    @Test
    @DisplayName("Should return false for Name longer than 256 characters")
    void nameTooLong() throws Exception {
        var longName = "A".repeat(257);
        var token = buildJwt("""
                {"Name":"%s","Role":"Admin","Seed":"7841"}""".formatted(longName));
        assertValidation(token, false);
    }

    @Test
    @DisplayName("Should return false for malformed JWT structure")
    void malformedJwt() throws Exception {
        assertValidation("not.a.valid-jwt", false);
    }

    @Test
    @DisplayName("Should return false for non-numeric Seed")
    void nonNumericSeed() throws Exception {
        var token = buildJwt("""
                {"Name":"Toninho Araujo","Role":"Admin","Seed":"abc"}""");
        assertValidation(token, false);
    }

    @Test
    @DisplayName("Should return true for valid JWT with Member role")
    void validMemberRole() throws Exception {
        var token = buildJwt("""
                {"Name":"Maria Silva","Role":"Member","Seed":"7"}""");
        assertValidation(token, true);
    }

    @Test
    @DisplayName("Should return true for valid JWT with External role")
    void validExternalRole() throws Exception {
        var token = buildJwt("""
                {"Name":"Joao Pedro","Role":"External","Seed":"11"}""");
        assertValidation(token, true);
    }

    @Test
    @DisplayName("Should return false for missing required claim")
    void missingClaim() throws Exception {
        var token = buildJwt("""
                {"Name":"Toninho Araujo","Role":"Admin"}""");
        assertValidation(token, false);
    }
}
