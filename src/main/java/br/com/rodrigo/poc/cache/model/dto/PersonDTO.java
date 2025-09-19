package br.com.rodrigo.poc.cache.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO para transferência de dados de pessoa entre camadas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private UUID id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;
    
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;
    
    @Size(min = 11, max = 11, message = "CPF must have 11 digits")
    @Pattern(regexp = "\\d{11}", message = "CPF must contain only numbers")
    private String cpf;
    
    @NotBlank(message = "Nationality is required")
    @Size(min = 3, max = 3, message = "Nationality code must have 3 characters")
    private String nationality;
    
    private String passport;
    
    @Pattern(regexp = "[MF]", message = "Gender must be 'M' or 'F'")
    private String gender;
    
    // Campos formatados para o frontend
    private String formattedCpf;
    private String nationalityName;
    private String nationalityFlag;
    private String genderDisplay;
    
    /**
     * Formata o CPF para exibição (XXX.XXX.XXX-XX)
     * @return CPF formatado
     */
    public String getFormattedCpf() {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.substring(0, 3) + "." + 
               cpf.substring(3, 6) + "." + 
               cpf.substring(6, 9) + "-" + 
               cpf.substring(9);
    }
    
    /**
     * Retorna o nome do país com base na nacionalidade
     * @return Nome do país
     */
    public String getNationalityName() {
        if (nationality == null) {
            return null;
        }
        
        switch (nationality) {
            case "BRA": return "Brasil";
            case "USA": return "Estados Unidos";
            case "GBR": return "Reino Unido";
            case "FRA": return "França";
            case "DEU": return "Alemanha";
            case "ESP": return "Espanha";
            case "JPN": return "Japão";
            case "CHN": return "China";
            case "IND": return "Índia";
            case "CAN": return "Canadá";
            case "AUS": return "Austrália";
            case "RUS": return "Rússia";
            case "ARG": return "Argentina";
            default: return nationality;
        }
    }
    
    /**
     * Retorna o emoji da bandeira do país
     * @return Emoji da bandeira
     */
    public String getNationalityFlag() {
        if (nationality == null) {
            return null;
        }
        
        // Convertendo código do país para emoji de bandeira (Unicode)
        // Cada letra maiúscula do código ISO é convertida para um regional indicator symbol
        // A = U+1F1E6, B = U+1F1E7, etc.
        StringBuilder flag = new StringBuilder();
        for (char c : nationality.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                // Converte A-Z para regional indicator symbols
                flag.append(Character.toChars(0x1F1E6 + (c - 'A')));
            }
        }
        return flag.toString();
    }
    
    /**
     * Retorna o gênero formatado para exibição
     * @return "Masculino" ou "Feminino"
     */
    public String getGenderDisplay() {
        if (gender == null) {
            return null;
        }
        return "M".equals(gender) ? "Masculino" : "Feminino";
    }
}