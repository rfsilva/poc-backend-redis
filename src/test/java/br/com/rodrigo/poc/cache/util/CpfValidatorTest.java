package br.com.rodrigo.poc.cache.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CpfValidatorTest {

    @Test
    void shouldReturnFalseForNullCpf() {
        assertFalse(CpfValidator.isValid(null));
    }

    @Test
    void shouldReturnFalseForEmptyCpf() {
        assertFalse(CpfValidator.isValid(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1234567890", "123456789012"})
    void shouldReturnFalseForInvalidLength(String cpf) {
        assertFalse(CpfValidator.isValid(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567890a", "12345-67890", "123.456.789-01"})
    void shouldReturnFalseForNonNumericCpf(String cpf) {
        assertFalse(CpfValidator.isValid(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00000000000", "11111111111", "22222222222", "33333333333", 
                           "44444444444", "55555555555", "66666666666", 
                           "77777777777", "88888888888", "99999999999"})
    void shouldReturnFalseForAllSameDigits(String cpf) {
        assertFalse(CpfValidator.isValid(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678901", "98765432109", "11122233344"})
    void shouldReturnFalseForInvalidCheckDigits(String cpf) {
        assertFalse(CpfValidator.isValid(cpf));
    }

    @Test
    void shouldReturnTrueForValidCpf() {
        // Gerar CPFs válidos para teste
        String[] validCpfs = {
            generateValidCpf("529982247"),
            generateValidCpf("123456789"),
            generateValidCpf("987654321")
        };
        
        for (String cpf : validCpfs) {
            assertTrue(CpfValidator.isValid(cpf), "CPF " + cpf + " deveria ser válido");
        }
    }
    
    /**
     * Gera um CPF válido a partir dos 9 primeiros dígitos
     */
    private String generateValidCpf(String baseDigits) {
        if (baseDigits.length() != 9) {
            throw new IllegalArgumentException("Base digits must have 9 digits");
        }
        
        int digit1 = calculateFirstDigit(baseDigits);
        int digit2 = calculateSecondDigit(baseDigits, digit1);
        
        return baseDigits + digit1 + digit2;
    }
    
    private int calculateFirstDigit(String baseDigits) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(baseDigits.charAt(i));
            sum += digit * (10 - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }
    
    private int calculateSecondDigit(String baseDigits, int firstDigit) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(baseDigits.charAt(i));
            sum += digit * (11 - i);
        }
        sum += firstDigit * 2;
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }
}