package br.com.rodrigo.poc.cache.service;

import br.com.rodrigo.poc.cache.exception.ResourceNotFoundException;
import br.com.rodrigo.poc.cache.model.Person;
import br.com.rodrigo.poc.cache.model.dto.PersonDTO;
import br.com.rodrigo.poc.cache.repository.PersonRepository;
import br.com.rodrigo.poc.cache.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person person;
    private PersonDTO personDTO;
    private final Long personId = 1L;

    @BeforeEach
    void setUp() {
        // Setup test data
        person = Person.builder()
                .id(personId)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();

        personDTO = PersonDTO.builder()
                .id(personId)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();
    }

    @Test
    void findAll_ShouldReturnAllPersons() {
        // Given
        List<Person> persons = Arrays.asList(person);
        when(personRepository.findAll()).thenReturn(persons);

        // When
        List<PersonDTO> result = personService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(personDTO.getName(), result.get(0).getName());
        assertEquals(personDTO.getEmail(), result.get(0).getEmail());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void findById_WithExistingId_ShouldReturnPerson() {
        // Given
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        // When
        PersonDTO result = personService.findById(personId);

        // Then
        assertNotNull(result);
        assertEquals(personDTO.getId(), result.getId());
        assertEquals(personDTO.getName(), result.getName());
        assertEquals(personDTO.getEmail(), result.getEmail());
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void findById_WithNonExistingId_ShouldThrowException() {
        // Given
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personService.findById(999L));
        assertEquals(Constants.ErrorMessages.PERSON_NOT_FOUND + "999", exception.getMessage());
        verify(personRepository, times(1)).findById(999L);
    }

    @Test
    void findByName_ShouldReturnMatchingPersons() {
        // Given
        String nameQuery = "John";
        List<Person> persons = Arrays.asList(person);
        when(personRepository.findByNameContainingIgnoreCase(nameQuery)).thenReturn(persons);

        // When
        List<PersonDTO> result = personService.findByName(nameQuery);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(personDTO.getName(), result.get(0).getName());
        verify(personRepository, times(1)).findByNameContainingIgnoreCase(nameQuery);
    }

    @Test
    void create_ShouldSaveAndReturnPerson() {
        // Given
        when(personRepository.save(any(Person.class))).thenReturn(person);

        // When
        PersonDTO result = personService.create(personDTO);

        // Then
        assertNotNull(result);
        assertEquals(personDTO.getName(), result.getName());
        assertEquals(personDTO.getEmail(), result.getEmail());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void create_WithNullDTO_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> personService.create(null));
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void update_WithExistingId_ShouldUpdateAndReturnPerson() {
        // Given
        PersonDTO updatedDTO = PersonDTO.builder()
                .id(personId)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .address("456 Oak St")
                .phoneNumber("555-5678")
                .build();

        Person updatedPerson = Person.builder()
                .id(personId)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .address("456 Oak St")
                .phoneNumber("555-5678")
                .build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);

        // When
        PersonDTO result = personService.update(personId, updatedDTO);

        // Then
        assertNotNull(result);
        assertEquals(updatedDTO.getName(), result.getName());
        assertEquals(updatedDTO.getEmail(), result.getEmail());
        assertEquals(updatedDTO.getAddress(), result.getAddress());
        assertEquals(updatedDTO.getPhoneNumber(), result.getPhoneNumber());
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void update_WithNonExistingId_ShouldThrowException() {
        // Given
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personService.update(999L, personDTO));
        assertEquals(Constants.ErrorMessages.PERSON_NOT_FOUND + "999", exception.getMessage());
        verify(personRepository, times(1)).findById(999L);
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void update_WithNullDTO_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> personService.update(personId, null));
        verify(personRepository, never()).findById(anyLong());
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void delete_WithExistingId_ShouldDeletePerson() {
        // Given
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        doNothing().when(personRepository).delete(any(Person.class));

        // When
        personService.delete(personId);

        // Then
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    void delete_WithNonExistingId_ShouldThrowException() {
        // Given
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personService.delete(999L));
        assertEquals(Constants.ErrorMessages.PERSON_NOT_FOUND + "999", exception.getMessage());
        verify(personRepository, times(1)).findById(999L);
        verify(personRepository, never()).delete(any(Person.class));
    }

    @Test
    void clearCache_ShouldNotThrowException() {
        // When & Then
        assertDoesNotThrow(() -> personService.clearCache());
    }
}