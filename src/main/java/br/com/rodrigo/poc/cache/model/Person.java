package br.com.rodrigo.poc.cache.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entidade que representa uma pessoa no sistema
 */
@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;
    
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    
    @Column
    private String address;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Size(min = 11, max = 11, message = "CPF must have 11 digits")
    @Pattern(regexp = "\\d{11}", message = "CPF must contain only numbers")
    @Column(length = 11)
    private String cpf;
    
    @NotBlank(message = "Nationality is required")
    @Size(min = 3, max = 3, message = "Nationality code must have 3 characters")
    @Column(length = 3, nullable = false)
    private String nationality;
    
    @Column(length = 20)
    private String passport;
    
    @Pattern(regexp = "[MF]", message = "Gender must be 'M' or 'F'")
    @Column(length = 1)
    private String gender;
}