package br.com.rodrigo.poc.cache.util;

/**
 * Classe utilitária para validação de CPF
 */
public class CpfValidator {

    /**
     * Valida se um CPF é válido verificando os dígitos verificadores
     * 
     * @param cpf CPF a ser validado (apenas números)
     * @return true se o CPF for válido, false caso contrário
     */
    public static boolean isValid(String cpf) {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }
        
        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os caracteres são dígitos
        if (!cpf.matches("\\d{11}")) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (CPF inválido, mas com regra de validação correta)
        if (isAllDigitsEqual(cpf)) {
            return false;
        }
        
        // Calcula o primeiro dígito verificador
        int digit1 = calculateFirstDigit(cpf);
        
        // Calcula o segundo dígito verificador
        int digit2 = calculateSecondDigit(cpf, digit1);
        
        // Verifica se os dígitos verificadores calculados são iguais aos do CPF
        String calculatedDigits = String.valueOf(digit1) + String.valueOf(digit2);
        String actualDigits = cpf.substring(9, 11);
        
        return calculatedDigits.equals(actualDigits);
    }
    
    /**
     * Verifica se todos os dígitos do CPF são iguais
     * 
     * @param cpf CPF a ser verificado
     * @return true se todos os dígitos forem iguais, false caso contrário
     */
    private static boolean isAllDigitsEqual(String cpf) {
        char firstDigit = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != firstDigit) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Calcula o primeiro dígito verificador do CPF
     * 
     * @param cpf CPF completo
     * @return Primeiro dígito verificador calculado
     */
    private static int calculateFirstDigit(String cpf) {
        int sum = 0;
        
        // Multiplica os 9 primeiros dígitos pelos pesos (10, 9, 8, ..., 2)
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (10 - i);
        }
        
        // Calcula o resto da divisão por 11
        int remainder = sum % 11;
        
        // Se o resto for menor que 2, o dígito é 0, caso contrário é 11 - resto
        return (remainder < 2) ? 0 : (11 - remainder);
    }
    
    /**
     * Calcula o segundo dígito verificador do CPF
     * 
     * @param cpf CPF completo
     * @param firstDigit Primeiro dígito verificador calculado
     * @return Segundo dígito verificador calculado
     */
    private static int calculateSecondDigit(String cpf, int firstDigit) {
        int sum = 0;
        
        // Multiplica os 9 primeiros dígitos pelos pesos (11, 10, 9, ..., 3)
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (11 - i);
        }
        
        // Adiciona o produto do primeiro dígito verificador pelo peso 2
        sum += firstDigit * 2;
        
        // Calcula o resto da divisão por 11
        int remainder = sum % 11;
        
        // Se o resto for menor que 2, o dígito é 0, caso contrário é 11 - resto
        return (remainder < 2) ? 0 : (11 - remainder);
    }
}