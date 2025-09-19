package br.com.rodrigo.poc.cache.repository;

import br.com.rodrigo.poc.cache.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

    @Mock
    private PersonRepository personRepository;

    private Person person1;
    private Person person2;
    private final UUID personId1 = UUID.randomUUID();
    private final UUID personId2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        person1 = Person.builder()
                .id(personId1)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        person2 = Person.builder()
                .id(personId2)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .address("456 Oak St")
                .phoneNumber("555-5678")
                .build();
    }

    @Test
    void findByNameContainingIgnoreCase_ShouldReturnMatchingPersons() {
        // Given
        when(personRepository.findByNameContainingIgnoreCase("doe"))
                .thenReturn(Arrays.asList(person1, person2));
        when(personRepository.findByNameContainingIgnoreCase("john"))
                .thenReturn(Collections.singletonList(person1));

        // When
        List<Person> result1 = personRepository.findByNameContainingIgnoreCase("doe");
        List<Person> result2 = personRepository.findByNameContainingIgnoreCase("john");

        // Then
        assertEquals(2, result1.size());
        assertTrue(result1.stream().anyMatch(p -> p.getName().equals("John Doe")));
        assertTrue(result1.stream().anyMatch(p -> p.getName().equals("Jane Doe")));

        assertEquals(1, result2.size());
        assertEquals("John Doe", result2.get(0).getName());

        verify(personRepository, times(1)).findByNameContainingIgnoreCase("doe");
        verify(personRepository, times(1)).findByNameContainingIgnoreCase("john");
    }

    @Test
    void findByNameContainingIgnoreCase_WithPageable_ShouldReturnPagedResults() {
        // Given
        Page<Person> page = new PageImpl<>(Arrays.asList(person1, person2));
        when(personRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(page);

        // When
        Page<Person> result = personRepository.findByNameContainingIgnoreCase("doe", Pageable.unpaged());

        // Then
        assertEquals(2, result.getContent().size());
        verify(personRepository, times(1)).findByNameContainingIgnoreCase(anyString(), any(Pageable.class));
    }

    @Test
    void findByEmail_ShouldReturnMatchingPerson() {
        // Given
        when(personRepository.findByEmail("john.doe@example.com"))
                .thenReturn(Collections.singletonList(person1));

        // When
        List<Person> result = personRepository.findByEmail("john.doe@example.com");

        // Then
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());

        verify(personRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void findById_WithExistingId_ShouldReturnPerson() {
        // Given
        when(personRepository.findById(personId1)).thenReturn(Optional.of(person1));

        // When
        Optional<Person> result = personRepository.findById(personId1);

        // Then
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("john.doe@example.com", result.get().getEmail());

        verify(personRepository, times(1)).findById(personId1);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        when(personRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When
        Optional<Person> result = personRepository.findById(nonExistingId);

        // Then
        assertFalse(result.isPresent());
        verify(personRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void save_ShouldReturnSavedPerson() {
        // Given
        Person newPerson = Person.builder()
                .name("New Person")
                .email("new.person@example.com")
                .build();
        
        Person savedPerson = Person.builder()
                .id(UUID.randomUUID())
                .name("New Person")
                .email("new.person@example.com")
                .build();
        
        when(personRepository.save(newPerson)).thenReturn(savedPerson);

        // When
        Person result = personRepository.save(newPerson);

        // Then
        assertNotNull(result.getId());
        assertEquals("New Person", result.getName());
        assertEquals("new.person@example.com", result.getEmail());
        
        verify(personRepository, times(1)).save(newPerson);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        // Given
        doNothing().when(personRepository).delete(person1);

        // When
        personRepository.delete(person1);

        // Then
        verify(personRepository, times(1)).delete(person1);
    }
}