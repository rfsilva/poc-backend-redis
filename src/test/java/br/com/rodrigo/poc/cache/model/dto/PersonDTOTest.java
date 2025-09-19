package br.com.rodrigo.poc.cache.model.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    @Test
    void builder_ShouldCreatePersonDTOWithAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        
        // When
        PersonDTO personDTO = PersonDTO.builder()
                .id(id)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .cpf("12345678901")
                .nationality("BRA")
                .passport("AB1234567")
                .gender("M")
                .build();

        // Then
        assertEquals(id, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
        assertEquals("12345678901", personDTO.getCpf());
        assertEquals("BRA", personDTO.getNationality());
        assertEquals("AB1234567", personDTO.getPassport());
        assertEquals("M", personDTO.getGender());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPersonDTO() {
        // When
        PersonDTO personDTO = new PersonDTO();

        // Then
        assertNull(personDTO.getId());
        assertNull(personDTO.getName());
        assertNull(personDTO.getEmail());
        assertNull(personDTO.getAddress());
        assertNull(personDTO.getPhoneNumber());
        assertNull(personDTO.getCpf());
        assertNull(personDTO.getNationality());
        assertNull(personDTO.getPassport());
        assertNull(personDTO.getGender());
    }

    @Test
    void allArgsConstructor_ShouldCreatePersonDTOWithAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        
        // When
        PersonDTO personDTO = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M", null, null, null, null);

        // Then
        assertEquals(id, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
        assertEquals("12345678901", personDTO.getCpf());
        assertEquals("BRA", personDTO.getNationality());
        assertEquals("AB1234567", personDTO.getPassport());
        assertEquals("M", personDTO.getGender());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        UUID id = UUID.randomUUID();

        // When
        personDTO.setId(id);
        personDTO.setName("John Doe");
        personDTO.setEmail("john.doe@example.com");
        personDTO.setAddress("123 Main St");
        personDTO.setPhoneNumber("555-1234");
        personDTO.setCpf("12345678901");
        personDTO.setNationality("BRA");
        personDTO.setPassport("AB1234567");
        personDTO.setGender("M");

        // Then
        assertEquals(id, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
        assertEquals("12345678901", personDTO.getCpf());
        assertEquals("BRA", personDTO.getNationality());
        assertEquals("AB1234567", personDTO.getPassport());
        assertEquals("M", personDTO.getGender());
    }

    @Test
    void getFormattedCpf_ShouldFormatCpfCorrectly() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        personDTO.setCpf("12345678901");
        
        // When
        String formattedCpf = personDTO.getFormattedCpf();
        
        // Then
        assertEquals("123.456.789-01", formattedCpf);
    }
    
    @Test
    void getFormattedCpf_WithInvalidCpf_ShouldReturnOriginal() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        personDTO.setCpf("123456");
        
        // When
        String formattedCpf = personDTO.getFormattedCpf();
        
        // Then
        assertEquals("123456", formattedCpf);
    }
    
    @Test
    void getFormattedCpf_WithNullCpf_ShouldReturnNull() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        
        // When
        String formattedCpf = personDTO.getFormattedCpf();
        
        // Then
        assertNull(formattedCpf);
    }
    
    @Test
    void getNationalityName_ShouldReturnCorrectCountryName() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        personDTO.setNationality("BRA");
        
        // When
        String countryName = personDTO.getNationalityName();
        
        // Then
        assertEquals("Brasil", countryName);
    }
    
    @Test
    void getNationalityName_WithUnknownCode_ShouldReturnOriginalCode() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        personDTO.setNationality("XYZ");
        
        // When
        String countryName = personDTO.getNationalityName();
        
        // Then
        assertEquals("XYZ", countryName);
    }
    
    @Test
    void getNationalityName_WithNullNationality_ShouldReturnNull() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        
        // When
        String countryName = personDTO.getNationalityName();
        
        // Then
        assertNull(countryName);
    }
    
    @Test
    void getNationalityFlag_ShouldReturnFlagEmoji() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        personDTO.setNationality("BRA");
        
        // When
        String flag = personDTO.getNationalityFlag();
        
        // Then
        assertNotNull(flag);
        assertTrue(flag.length() > 0);
    }
    
    @Test
    void getNationalityFlag_WithNullNationality_ShouldReturnNull() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        
        // When
        String flag = personDTO.getNationalityFlag();
        
        // Then
        assertNull(flag);
    }
    
    @Test
    void getGenderDisplay_ShouldReturnCorrectDisplay() {
        // Given
        PersonDTO personDTO1 = new PersonDTO();
        personDTO1.setGender("M");
        
        PersonDTO personDTO2 = new PersonDTO();
        personDTO2.setGender("F");
        
        // When
        String display1 = personDTO1.getGenderDisplay();
        String display2 = personDTO2.getGenderDisplay();
        
        // Then
        assertEquals("Masculino", display1);
        assertEquals("Feminino", display2);
    }
    
    @Test
    void getGenderDisplay_WithNullGender_ShouldReturnNull() {
        // Given
        PersonDTO personDTO = new PersonDTO();
        
        // When
        String display = personDTO.getGenderDisplay();
        
        // Then
        assertNull(display);
    }

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M", null, null, null, null);

        // When & Then
        assertEquals(personDTO, personDTO);
    }

    @Test
    void equals_WithEqualObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDTO personDTO1 = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M", null, null, null, null);
        PersonDTO personDTO2 = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M", null, null, null, null);

        // When & Then
        assertEquals(personDTO1, personDTO2);
        assertEquals(personDTO1.hashCode(), personDTO2.hashCode());
    }

    @Test
    void equals_WithDifferentObject_ShouldReturnFalse() {
        // Given
        PersonDTO personDTO1 = new PersonDTO(UUID.randomUUID(), "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M", null, null, null, null);
        PersonDTO personDTO2 = new PersonDTO(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", "456 Oak St", "555-5678",
                "98765432109", "USA", "CD7654321", "F", null, null, null, null);

        // When & Then
        assertNotEquals(personDTO1, personDTO2);
        assertNotEquals(personDTO1.hashCode(), personDTO2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M", null, null, null, null);

        // When
        String toString = personDTO.toString();

        // Then
        assertTrue(toString.contains("id=" + id));
        assertTrue(toString.contains("name=John Doe"));
        assertTrue(toString.contains("email=john.doe@example.com"));
        assertTrue(toString.contains("address=123 Main St"));
        assertTrue(toString.contains("phoneNumber=555-1234"));
        assertTrue(toString.contains("cpf=12345678901"));
        assertTrue(toString.contains("nationality=BRA"));
        assertTrue(toString.contains("passport=AB1234567"));
        assertTrue(toString.contains("gender=M"));
    }
}