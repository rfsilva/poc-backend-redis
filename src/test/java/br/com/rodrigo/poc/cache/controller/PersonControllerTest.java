package br.com.rodrigo.poc.cache.controller;

import br.com.rodrigo.poc.cache.exception.ResourceNotFoundException;
import br.com.rodrigo.poc.cache.model.dto.PersonDTO;
import br.com.rodrigo.poc.cache.service.PersonService;
import br.com.rodrigo.poc.cache.util.Constants;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private PersonDTO personDTO;
    private final Long personId = 1L;

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
        when(personService.findById(anyLong())).thenThrow(
                new ResourceNotFoundException(Constants.ErrorMessages.PERSON_NOT_FOUND + "999"));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personController.getPersonById(999L));
        assertEquals(Constants.ErrorMessages.PERSON_NOT_FOUND + "999", exception.getMessage());
        verify(personService, times(1)).findById(999L);
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

        when(personService.update(anyLong(), any(PersonDTO.class))).thenReturn(updatedDTO);

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
        when(personService.update(anyLong(), any(PersonDTO.class))).thenThrow(
                new ResourceNotFoundException(Constants.ErrorMessages.PERSON_NOT_FOUND + "999"));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personController.updatePerson(999L, personDTO));
        assertEquals(Constants.ErrorMessages.PERSON_NOT_FOUND + "999", exception.getMessage());
        verify(personService, times(1)).update(999L, personDTO);
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
        doThrow(new ResourceNotFoundException(Constants.ErrorMessages.PERSON_NOT_FOUND + "999"))
                .when(personService).delete(999L);

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> personController.deletePerson(999L));
        assertEquals(Constants.ErrorMessages.PERSON_NOT_FOUND + "999", exception.getMessage());
        verify(personService, times(1)).delete(999L);
    }

    @Test
    void clearCache_ShouldReturnOk() {
        // Given
        doNothing().when(personService).clearCache();

        // When
        ResponseEntity<Void> response = personController.clearCache();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(personService, times(1)).clearCache();
    }
}