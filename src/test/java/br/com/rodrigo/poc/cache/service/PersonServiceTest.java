package br.com.rodrigo.poc.cache.service;

import br.com.rodrigo.poc.cache.exception.ResourceNotFoundException;
import br.com.rodrigo.poc.cache.model.Person;
import br.com.rodrigo.poc.cache.model.dto.PageResponse;
import br.com.rodrigo.poc.cache.model.dto.PersonDTO;
import br.com.rodrigo.poc.cache.repository.PersonRepository;
import br.com.rodrigo.poc.cache.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    
    @Mock
    private MessageService messageService;

    @InjectMocks
    private PersonService personService;

    private Person person;
    private PersonDTO personDTO;
    private final UUID personId = UUID.randomUUID();

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
                
        // Setup message service mock
        when(messageService.getMessage(eq(Constants.MessageCodes.PERSON_NOT_FOUND), any(Object[].class)))
            .thenAnswer(invocation -> {
                Object[] args = invocation.getArgument(1);
                return "Person not found with id: " + args[0];
            });
        
        when(messageService.getMessage(Constants.MessageCodes.INVALID_CPF))
            .thenReturn("Invalid CPF. Please provide a valid CPF number.");
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
    void findAllPaged_ShouldReturnPagedPersons() {
        // Given
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "ASC";
        
        List<Person> persons = Arrays.asList(person);
        Page<Person> pagedResponse = new PageImpl<>(persons, PageRequest.of(page, size, Sort.by(sortBy)), 1);
        
        when(personRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        // When
        PageResponse<PersonDTO> result = personService.findAllPaged(page, size, sortBy, direction);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(personDTO.getName(), result.getContent().get(0).getName());
        assertEquals(personDTO.getEmail(), result.getContent().get(0).getEmail());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isLast());
        
        verify(personRepository, times(1)).findAll(any(Pageable.class));
    }
    
    @Test
    void findAllPaged_WithDescendingOrder_ShouldReturnPagedPersons() {
        // Given
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "DESC";
        
        List<Person> persons = Arrays.asList(person);
        Page<Person> pagedResponse = new PageImpl<>(persons, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy)), 1);
        
        when(personRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        // When
        PageResponse<PersonDTO> result = personService.findAllPaged(page, size, sortBy, direction);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(personDTO.getName(), result.getContent().get(0).getName());
        assertEquals(personDTO.getEmail(), result.getContent().get(0).getEmail());
        
        verify(personRepository, times(1)).findAll(any(Pageable.class));
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
        UUID nonExistingId = UUID.randomUUID();
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personService.findById(nonExistingId));
        assertEquals("Person not found with id: " + nonExistingId, exception.getMessage());
        verify(personRepository, times(1)).findById(nonExistingId);
        verify(messageService, times(1)).getMessage(eq(Constants.MessageCodes.PERSON_NOT_FOUND), any(Object[].class));
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
    void findByNamePaged_ShouldReturnPagedMatchingPersons() {
        // Given
        String nameQuery = "John";
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "ASC";
        
        List<Person> persons = Arrays.asList(person);
        Page<Person> pagedResponse = new PageImpl<>(persons, PageRequest.of(page, size, Sort.by(sortBy)), 1);
        
        when(personRepository.findByNameContainingIgnoreCase(eq(nameQuery), any(Pageable.class))).thenReturn(pagedResponse);

        // When
        PageResponse<PersonDTO> result = personService.findByNamePaged(nameQuery, page, size, sortBy, direction);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(personDTO.getName(), result.getContent().get(0).getName());
        assertEquals(personDTO.getEmail(), result.getContent().get(0).getEmail());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isLast());
        
        verify(personRepository, times(1)).findByNameContainingIgnoreCase(eq(nameQuery), any(Pageable.class));
    }
    
    @Test
    void findByNamePaged_WithDescendingOrder_ShouldReturnPagedMatchingPersons() {
        // Given
        String nameQuery = "John";
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "DESC";
        
        List<Person> persons = Arrays.asList(person);
        Page<Person> pagedResponse = new PageImpl<>(persons, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy)), 1);
        
        when(personRepository.findByNameContainingIgnoreCase(eq(nameQuery), any(Pageable.class))).thenReturn(pagedResponse);

        // When
        PageResponse<PersonDTO> result = personService.findByNamePaged(nameQuery, page, size, sortBy, direction);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(personDTO.getName(), result.getContent().get(0).getName());
        assertEquals(personDTO.getEmail(), result.getContent().get(0).getEmail());
        
        verify(personRepository, times(1)).findByNameContainingIgnoreCase(eq(nameQuery), any(Pageable.class));
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
        UUID nonExistingId = UUID.randomUUID();
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personService.update(nonExistingId, personDTO));
        assertEquals("Person not found with id: " + nonExistingId, exception.getMessage());
        verify(personRepository, times(1)).findById(nonExistingId);
        verify(personRepository, never()).save(any(Person.class));
        verify(messageService, times(1)).getMessage(eq(Constants.MessageCodes.PERSON_NOT_FOUND), any(Object[].class));
    }

    @Test
    void update_WithNullDTO_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> personService.update(personId, null));
        verify(personRepository, never()).findById(any(UUID.class));
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
        UUID nonExistingId = UUID.randomUUID();
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personService.delete(nonExistingId));
        assertEquals("Person not found with id: " + nonExistingId, exception.getMessage());
        verify(personRepository, times(1)).findById(nonExistingId);
        verify(personRepository, never()).delete(any(Person.class));
        verify(messageService, times(1)).getMessage(eq(Constants.MessageCodes.PERSON_NOT_FOUND), any(Object[].class));
    }

    @Test
    void clearCache_ShouldNotThrowException() {
        // When & Then
        assertDoesNotThrow(() -> personService.clearCache());
    }
}