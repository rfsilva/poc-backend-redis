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
                .cpf("12345678901")
                .nationality("BRA")
                .passport("AB1234567")
                .gender("M")
                .build();

        // Then
        assertEquals(id, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
        assertEquals("12345678901", person.getCpf());
        assertEquals("BRA", person.getNationality());
        assertEquals("AB1234567", person.getPassport());
        assertEquals("M", person.getGender());
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
        assertNull(person.getCpf());
        assertNull(person.getNationality());
        assertNull(person.getPassport());
        assertNull(person.getGender());
    }

    @Test
    void allArgsConstructor_ShouldCreatePersonWithAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        
        // When
        Person person = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234", 
                "12345678901", "BRA", "AB1234567", "M");

        // Then
        assertEquals(id, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
        assertEquals("12345678901", person.getCpf());
        assertEquals("BRA", person.getNationality());
        assertEquals("AB1234567", person.getPassport());
        assertEquals("M", person.getGender());
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
        person.setCpf("12345678901");
        person.setNationality("BRA");
        person.setPassport("AB1234567");
        person.setGender("M");

        // Then
        assertEquals(id, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhoneNumber());
        assertEquals("12345678901", person.getCpf());
        assertEquals("BRA", person.getNationality());
        assertEquals("AB1234567", person.getPassport());
        assertEquals("M", person.getGender());
    }

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M");

        // When & Then
        assertEquals(person, person);
    }

    @Test
    void equals_WithEqualObject_ShouldReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        Person person1 = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M");
        Person person2 = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M");

        // When & Then
        assertEquals(person1, person2);
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void equals_WithDifferentObject_ShouldReturnFalse() {
        // Given
        Person person1 = new Person(UUID.randomUUID(), "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M");
        Person person2 = new Person(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", "456 Oak St", "555-5678",
                "98765432109", "USA", "CD7654321", "F");

        // When & Then
        assertNotEquals(person1, person2);
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "John Doe", "john.doe@example.com", "123 Main St", "555-1234",
                "12345678901", "BRA", "AB1234567", "M");

        // When
        String toString = person.toString();

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