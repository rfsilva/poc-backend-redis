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
    
    @NotBlank(message = "{validation.person.name.required}")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "{validation.person.email.valid}")
    @Column(unique = true)
    private String email;
    
    @Column
    private String address;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Size(min = 11, max = 11, message = "{validation.person.cpf.size}")
    @Pattern(regexp = "\\d{11}", message = "{validation.person.cpf.pattern}")
    @Column(length = 11)
    private String cpf;
    
    @NotBlank(message = "{validation.person.nationality.required}")
    @Size(min = 3, max = 3, message = "{validation.person.nationality.size}")
    @Column(length = 3, nullable = false)
    private String nationality;
    
    @Column(length = 20)
    private String passport;
    
    @Pattern(regexp = "[MF]", message = "{validation.person.gender.pattern}")
    @Column(length = 1)
    private String gender;
}