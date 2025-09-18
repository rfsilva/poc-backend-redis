package br.com.rodrigo.poc.cache.repository;

import br.com.rodrigo.poc.cache.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByNameContainingIgnoreCase_ShouldReturnMatchingPersons() {
        // Given
        Person person1 = Person.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        Person person2 = Person.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .address("456 Oak St")
                .phoneNumber("555-5678")
                .build();

        Person person3 = Person.builder()
                .name("Bob Smith")
                .email("bob.smith@example.com")
                .address("789 Pine St")
                .phoneNumber("555-9012")
                .build();

        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        entityManager.flush();

        // When
        List<Person> result1 = personRepository.findByNameContainingIgnoreCase("doe");
        List<Person> result2 = personRepository.findByNameContainingIgnoreCase("JOHN");
        List<Person> result3 = personRepository.findByNameContainingIgnoreCase("smith");

        // Then
        assertEquals(2, result1.size());
        assertTrue(result1.stream().anyMatch(p -> p.getName().equals("John Doe")));
        assertTrue(result1.stream().anyMatch(p -> p.getName().equals("Jane Doe")));

        assertEquals(1, result2.size());
        assertEquals("John Doe", result2.get(0).getName());

        assertEquals(1, result3.size());
        assertEquals("Bob Smith", result3.get(0).getName());
    }

    @Test
    void findByEmail_ShouldReturnMatchingPerson() {
        // Given
        Person person = Person.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        entityManager.persist(person);
        entityManager.flush();

        // When
        List<Person> result = personRepository.findByEmail("john.doe@example.com");

        // Then
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
    }

    @Test
    void findById_WithExistingId_ShouldReturnPerson() {
        // Given
        Person person = Person.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        person = entityManager.persist(person);
        entityManager.flush();

        // When
        Optional<Person> result = personRepository.findById(person.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("john.doe@example.com", result.get().getEmail());
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        // When
        Optional<Person> result = personRepository.findById(UUID.randomUUID());

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistPerson() {
        // Given
        Person person = Person.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        // When
        Person savedPerson = personRepository.save(person);

        // Then
        assertNotNull(savedPerson.getId());
        
        Person foundPerson = entityManager.find(Person.class, savedPerson.getId());
        assertNotNull(foundPerson);
        assertEquals("John Doe", foundPerson.getName());
        assertEquals("john.doe@example.com", foundPerson.getEmail());
    }

    @Test
    void delete_ShouldRemovePerson() {
        // Given
        Person person = Person.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        person = entityManager.persist(person);
        entityManager.flush();
        
        UUID personId = person.getId();

        // When
        personRepository.delete(person);
        entityManager.flush();

        // Then
        Person foundPerson = entityManager.find(Person.class, personId);
        assertNull(foundPerson);
    }
}