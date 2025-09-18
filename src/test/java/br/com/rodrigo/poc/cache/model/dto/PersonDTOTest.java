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
                .build();

        // Then
        assertEquals(id, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
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
    }

    @Test
    void allArgsConstructor_ShouldCreatePersonDTOWithAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        
        // When
        PersonDTO personDTO = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // Then
        assertEquals(id, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
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

        // Then
        assertEquals(id, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
    }

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(personDTO, personDTO);
    }

    @Test
    void equals_WithEqualObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDTO personDTO1 = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        PersonDTO personDTO2 = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(personDTO1, personDTO2);
        assertEquals(personDTO1.hashCode(), personDTO2.hashCode());
    }

    @Test
    void equals_WithDifferentObject_ShouldReturnFalse() {
        // Given
        PersonDTO personDTO1 = new PersonDTO(UUID.randomUUID(), "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        PersonDTO personDTO2 = new PersonDTO(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", "456 Oak St", "555-5678");

        // When & Then
        assertNotEquals(personDTO1, personDTO2);
        assertNotEquals(personDTO1.hashCode(), personDTO2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When
        String toString = personDTO.toString();

        // Then
        assertTrue(toString.contains("id=" + id));
        assertTrue(toString.contains("name=John Doe"));
        assertTrue(toString.contains("email=john.doe@example.com"));
        assertTrue(toString.contains("address=123 Main St"));
        assertTrue(toString.contains("phoneNumber=555-1234"));
    }
}