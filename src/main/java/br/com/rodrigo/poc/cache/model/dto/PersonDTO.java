package br.com.rodrigo.poc.cache.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

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
    
    @NotBlank(message = "{validation.person.name.required}")
    @Size(min = 2, max = 100, message = "{validation.person.name.size}")
    private String name;
    
    @Email(message = "{validation.person.email.valid}")
    @Size(max = 100, message = "{validation.person.email.size}")
    private String email;
    
    @Size(max = 200, message = "{validation.person.address.size}")
    private String address;
    
    @Size(max = 20, message = "{validation.person.phoneNumber.size}")
    private String phoneNumber;
    
    @Size(min = 11, max = 11, message = "{validation.person.cpf.size}")
    @Pattern(regexp = "\\d{11}", message = "{validation.person.cpf.pattern}")
    private String cpf;
    
    @NotBlank(message = "{validation.person.nationality.required}")
    @Size(min = 3, max = 3, message = "{validation.person.nationality.size}")
    private String nationality;
    
    private String passport;
    
    @Pattern(regexp = "[MF]", message = "{validation.person.gender.pattern}")
    private String gender;
    
    // Campos formatados para o frontend
    private String formattedCpf;
    private String nationalityName;
    private String nationalityFlag;
    private String genderDisplay;
    
    // Serviço de mensagens para internacionalização
    private static MessageSource messageSource;
    
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        PersonDTO.messageSource = messageSource;
    }
    
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
        if (nationality == null || messageSource == null) {
            return nationality;
        }
        
        return messageSource.getMessage("country." + nationality, null, nationality, LocaleContextHolder.getLocale());
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
     * @return "Masculino" ou "Feminino" de acordo com o idioma
     */
    public String getGenderDisplay() {
        if (gender == null || messageSource == null) {
            return null;
        }
        
        String genderKey = "M".equals(gender) ? "gender.male" : "gender.female";
        return messageSource.getMessage(genderKey, null, gender, LocaleContextHolder.getLocale());
    }
}