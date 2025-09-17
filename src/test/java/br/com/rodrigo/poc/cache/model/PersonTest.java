package br.com.rodrigo.poc.cache.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void builder_ShouldCreatePersonWithAllFields() {
        // When
        Person person = Person.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        // Then
        assertEquals(1L, person.getId());
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
        // When
        Person person = new Person(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // Then
        assertEquals(1L, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Given
        Person person = new Person();

        // When
        person.setId(1L);
        person.setName("John Doe");
        person.setEmail("john.doe@example.com");
        person.setAddress("123 Main St");
        person.setPhoneNumber("555-1234");

        // Then
        assertEquals(1L, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
    }

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        // Given
        Person person = new Person(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(person, person);
    }

    @Test
    void equals_WithEqualObject_ShouldReturnTrue() {
        // Given
        Person person1 = new Person(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        Person person2 = new Person(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When & Then
        assertEquals(person1, person2);
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void equals_WithDifferentObject_ShouldReturnFalse() {
        // Given
        Person person1 = new Person(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");
        Person person2 = new Person(2L, "Jane Doe", "jane.doe@example.com", "456 Oak St", "555-5678");

        // When & Then
        assertNotEquals(person1, person2);
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        Person person = new Person(1L, "John Doe", "john.doe@example.com", "123 Main St", "555-1234");

        // When
        String toString = person.toString();

        // Then
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=John Doe"));
        assertTrue(toString.contains("email=john.doe@example.com"));
        assertTrue(toString.contains("address=123 Main St"));
        assertTrue(toString.contains("phoneNumber=555-1234"));
    }
}