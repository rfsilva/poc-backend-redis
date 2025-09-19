package br.com.rodrigo.poc.cache.controller;

import br.com.rodrigo.poc.cache.exception.ResourceNotFoundException;
import br.com.rodrigo.poc.cache.model.dto.PageResponse;
import br.com.rodrigo.poc.cache.model.dto.PersonDTO;
import br.com.rodrigo.poc.cache.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private PersonDTO personDTO;
    private final UUID personId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        // Setup test data
        personDTO = PersonDTO.builder()
                .id(personId)
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .build();
    }

    @Test
    void getAllPersons_ShouldReturnAllPersons() {
        // Given
        List<PersonDTO> persons = Arrays.asList(personDTO);
        when(personService.findAll()).thenReturn(persons);

        // When
        ResponseEntity<List<PersonDTO>> response = personController.getAllPersons();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(personDTO.getName(), response.getBody().get(0).getName());
        verify(personService, times(1)).findAll();
    }
    
    @Test
    void getAllPersonsPaged_ShouldReturnPagedPersons() {
        // Given
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "ASC";
        
        List<PersonDTO> persons = Arrays.asList(personDTO);
        PageResponse<PersonDTO> pageResponse = PageResponse.<PersonDTO>builder()
                .content(persons)
                .pageNumber(page)
                .pageSize(size)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();
        
        when(personService.findAllPaged(page, size, sortBy, direction)).thenReturn(pageResponse);

        // When
        ResponseEntity<PageResponse<PersonDTO>> response = personController.getAllPersonsPaged(page, size, sortBy, direction);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(personDTO.getName(), response.getBody().getContent().get(0).getName());
        assertEquals(0, response.getBody().getPageNumber());
        assertEquals(10, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertTrue(response.getBody().isLast());
        
        verify(personService, times(1)).findAllPaged(page, size, sortBy, direction);
    }

    @Test
    void getPersonById_WithExistingId_ShouldReturnPerson() {
        // Given
        when(personService.findById(personId)).thenReturn(personDTO);

        // When
        ResponseEntity<PersonDTO> response = personController.getPersonById(personId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(personDTO.getId(), response.getBody().getId());
        assertEquals(personDTO.getName(), response.getBody().getName());
        verify(personService, times(1)).findById(personId);
    }

    @Test
    void getPersonById_WithNonExistingId_ShouldThrowException() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        String errorMessage = "Person not found with id: " + nonExistingId;
        when(personService.findById(any(UUID.class))).thenThrow(
                new ResourceNotFoundException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personController.getPersonById(nonExistingId));
        assertEquals(errorMessage, exception.getMessage());
        verify(personService, times(1)).findById(nonExistingId);
    }

    @Test
    void searchPersonsByName_ShouldReturnMatchingPersons() {
        // Given
        String nameQuery = "John";
        List<PersonDTO> persons = Arrays.asList(personDTO);
        when(personService.findByName(nameQuery)).thenReturn(persons);

        // When
        ResponseEntity<List<PersonDTO>> response = personController.searchPersonsByName(nameQuery);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(personDTO.getName(), response.getBody().get(0).getName());
        verify(personService, times(1)).findByName(nameQuery);
    }
    
    @Test
    void searchPersonsByNamePaged_ShouldReturnPagedMatchingPersons() {
        // Given
        String nameQuery = "John";
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "ASC";
        
        List<PersonDTO> persons = Arrays.asList(personDTO);
        PageResponse<PersonDTO> pageResponse = PageResponse.<PersonDTO>builder()
                .content(persons)
                .pageNumber(page)
                .pageSize(size)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();
        
        when(personService.findByNamePaged(nameQuery, page, size, sortBy, direction)).thenReturn(pageResponse);

        // When
        ResponseEntity<PageResponse<PersonDTO>> response = personController.searchPersonsByNamePaged(nameQuery, page, size, sortBy, direction);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(personDTO.getName(), response.getBody().getContent().get(0).getName());
        assertEquals(0, response.getBody().getPageNumber());
        assertEquals(10, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertTrue(response.getBody().isLast());
        
        verify(personService, times(1)).findByNamePaged(nameQuery, page, size, sortBy, direction);
    }

    @Test
    void createPerson_ShouldCreateAndReturnPerson() {
        // Given
        when(personService.create(any(PersonDTO.class))).thenReturn(personDTO);

        // When
        ResponseEntity<PersonDTO> response = personController.createPerson(personDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(personDTO.getName(), response.getBody().getName());
        assertEquals(personDTO.getEmail(), response.getBody().getEmail());
        verify(personService, times(1)).create(any(PersonDTO.class));
    }

    @Test
    void updatePerson_WithExistingId_ShouldUpdateAndReturnPerson() {
        // Given
        PersonDTO updatedDTO = PersonDTO.builder()
                .id(personId)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .address("456 Oak St")
                .phoneNumber("555-5678")
                .build();

        when(personService.update(any(UUID.class), any(PersonDTO.class))).thenReturn(updatedDTO);

        // When
        ResponseEntity<PersonDTO> response = personController.updatePerson(personId, updatedDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedDTO.getName(), response.getBody().getName());
        assertEquals(updatedDTO.getEmail(), response.getBody().getEmail());
        verify(personService, times(1)).update(personId, updatedDTO);
    }

    @Test
    void updatePerson_WithNonExistingId_ShouldThrowException() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        String errorMessage = "Person not found with id: " + nonExistingId;
        when(personService.update(any(UUID.class), any(PersonDTO.class))).thenThrow(
                new ResourceNotFoundException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personController.updatePerson(nonExistingId, personDTO));
        assertEquals(errorMessage, exception.getMessage());
        verify(personService, times(1)).update(nonExistingId, personDTO);
    }

    @Test
    void deletePerson_WithExistingId_ShouldReturnNoContent() {
        // Given
        doNothing().when(personService).delete(personId);

        // When
        ResponseEntity<Void> response = personController.deletePerson(personId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(personService, times(1)).delete(personId);
    }

    @Test
    void deletePerson_WithNonExistingId_ShouldThrowException() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        String errorMessage = "Person not found with id: " + nonExistingId;
        doThrow(new ResourceNotFoundException(errorMessage))
                .when(personService).delete(nonExistingId);

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personController.deletePerson(nonExistingId));
        assertEquals(errorMessage, exception.getMessage());
        verify(personService, times(1)).delete(nonExistingId);
    }

    @Test
    void clearCache_ShouldReturnNoContent() {
        // Given
        doNothing().when(personService).clearCache();

        // When
        ResponseEntity<Void> response = personController.clearCache();

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(personService, times(1)).clearCache();
    }
}