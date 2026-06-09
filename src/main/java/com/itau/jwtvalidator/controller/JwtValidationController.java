package com.itau.jwtvalidator.controller;

import com.itau.jwtvalidator.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/jwt")
@RequiredArgsConstructor
public class JwtValidationController {

    private final JwtService jwtService;

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody String token) {
        return ResponseEntity.ok(jwtService.validate(token));
    }
}
