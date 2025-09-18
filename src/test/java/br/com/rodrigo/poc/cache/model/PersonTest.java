package br.com.rodrigo.poc.cache.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void builder_ShouldCreatePersonWithAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        
        // When
        Person person = Person.builder()
                .id(id)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        // Then
        assertEquals(id, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPerson() {
        // When
        Person person = new Person();

        // Then
        assertNull(person.getId());
        assertNull(person.getName());
        assertNull(person.getEmail());
        assertNull(person.getAddress());
        assertNull(person.getPhoneNumber());
    }

    @Test
    void allArgsConstructor_ShouldCreatePersonWithAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        
        // When
        Person person = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // Then
        assertEquals(id, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Given
        Person person = new Person();
        UUID id = UUID.randomUUID();

        // When
        person.setId(id);
        person.setName("John Doe");
        person.setEmail("john.doe@example.com");
        person.setAddress("123 Main St");
        person.setPhoneNumber("555-1234");

        // Then
        assertEquals(id, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
    }

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(person, person);
    }

    @Test
    void equals_WithEqualObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        Person person1 = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        Person person2 = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(person1, person2);
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void equals_WithDifferentObject_ShouldReturnFalse() {
        // Given
        Person person1 = new Person(UUID.randomUUID(), "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        Person person2 = new Person(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", "456 Oak St", "555-5678");

        // When & Then
        assertNotEquals(person1, person2);
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When
        String toString = person.toString();

        // Then
        assertTrue(toString.contains("id=" + id));
        assertTrue(toString.contains("name=John Doe"));
        assertTrue(toString.contains("email=john.doe@example.com"));
        assertTrue(toString.contains("address=123 Main St"));
        assertTrue(toString.contains("phoneNumber=555-1234"));
    }
}