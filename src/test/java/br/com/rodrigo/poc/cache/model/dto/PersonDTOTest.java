package br.com.rodrigo.poc.cache.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    @Test
    void builder_ShouldCreatePersonDTOWithAllFields() {
        // When
        PersonDTO personDTO = PersonDTO.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        // Then
        assertEquals(1L, personDTO.getId());
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
        // When
        PersonDTO personDTO = new PersonDTO(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // Then
        assertEquals(1L, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Given
        PersonDTO personDTO = new PersonDTO();

        // When
        personDTO.setId(1L);
        personDTO.setName("John Doe");
        personDTO.setEmail("john.doe@example.com");
        personDTO.setAddress("123 Main St");
        personDTO.setPhoneNumber("555-1234");

        // Then
        assertEquals(1L, personDTO.getId());
        assertEquals("John Doe", personDTO.getName());
        assertEquals("john.doe@example.com", personDTO.getEmail());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("555-1234", personDTO.getPhoneNumber());
    }

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        // Given
        PersonDTO personDTO = new PersonDTO(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(personDTO, personDTO);
    }

    @Test
    void equals_WithEqualObject_ShouldReturnTrue() {
        // Given
        PersonDTO personDTO1 = new PersonDTO(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        PersonDTO personDTO2 = new PersonDTO(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(personDTO1, personDTO2);
        assertEquals(personDTO1.hashCode(), personDTO2.hashCode());
    }

    @Test
    void equals_WithDifferentObject_ShouldReturnFalse() {
        // Given
        PersonDTO personDTO1 = new PersonDTO(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        PersonDTO personDTO2 = new PersonDTO(2L, "Jane Doe", "jane.doe@example.com", "456 Oak St", "555-5678");

        // When & Then
        assertNotEquals(personDTO1, personDTO2);
        assertNotEquals(personDTO1.hashCode(), personDTO2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        PersonDTO personDTO = new PersonDTO(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When
        String toString = personDTO.toString();

        // Then
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=John Doe"));
        assertTrue(toString.contains("email=john.doe@example.com"));
        assertTrue(toString.contains("address=123 Main St"));
        assertTrue(toString.contains("phoneNumber=555-1234"));
    }
}