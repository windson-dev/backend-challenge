package com.itau.jwtvalidator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimeNumberUtilTest {

    @ParameterizedTest
    @ValueSource(longs = {2, 3, 5, 7, 11, 13, 17})
    @DisplayName("Should return true for prime numbers")
    void shouldReturnTrueForPrimeNumbers(long number) {
        assertTrue(PrimeNumberUtil.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 4, 6, 8, 9, 10, 12})
    @DisplayName("Should return false for non-prime numbers")
    void shouldReturnFalseForNonPrimeNumbers(long number) {
        assertFalse(PrimeNumberUtil.isPrime(number));
    }

    @Test
    @DisplayName("Should return false for negative numbers")
    void shouldReturnFalseForNegativeNumbers() {
        assertFalse(PrimeNumberUtil.isPrime(-1));
        assertFalse(PrimeNumberUtil.isPrime(-7));
    }

    @Test
    @DisplayName("Should handle large prime numbers")
    void shouldHandleLargePrimeNumbers() {
        assertTrue(PrimeNumberUtil.isPrime(7919));
        assertTrue(PrimeNumberUtil.isPrime(104729));
    }

    @Test
    @DisplayName("Should handle large non-prime numbers")
    void shouldHandleLargeNonPrimeNumbers() {
        assertFalse(PrimeNumberUtil.isPrime(7920));
        assertFalse(PrimeNumberUtil.isPrime(104730));
    }
}
