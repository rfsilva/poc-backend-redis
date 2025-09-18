package br.com.rodrigo.poc.cache.model.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PageResponseTest {

    @Test
    void testPageResponseBuilder() {
        // Given
        List<PersonDTO> content = Arrays.asList(
            PersonDTO.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .build()
        );
        int pageNumber = 0;
        int pageSize = 10;
        long totalElements = 1;
        int totalPages = 1;
        boolean last = true;

        // When
        PageResponse<PersonDTO> pageResponse = PageResponse.<PersonDTO>builder()
                .content(content)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(last)
                .build();

        // Then
        assertNotNull(pageResponse);
        assertEquals(content, pageResponse.getContent());
        assertEquals(pageNumber, pageResponse.getPageNumber());
        assertEquals(pageSize, pageResponse.getPageSize());
        assertEquals(totalElements, pageResponse.getTotalElements());
        assertEquals(totalPages, pageResponse.getTotalPages());
        assertEquals(last, pageResponse.isLast());
    }

    @Test
    void testPageResponseNoArgsConstructor() {
        // When
        PageResponse<PersonDTO> pageResponse = new PageResponse<>();

        // Then
        assertNotNull(pageResponse);
        assertNull(pageResponse.getContent());
        assertEquals(0, pageResponse.getPageNumber());
        assertEquals(0, pageResponse.getPageSize());
        assertEquals(0, pageResponse.getTotalElements());
        assertEquals(0, pageResponse.getTotalPages());
        assertFalse(pageResponse.isLast());
    }

    @Test
    void testPageResponseAllArgsConstructor() {
        // Given
        List<PersonDTO> content = Arrays.asList(
            PersonDTO.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .build()
        );
        int pageNumber = 0;
        int pageSize = 10;
        long totalElements = 1;
        int totalPages = 1;
        boolean last = true;

        // When
        PageResponse<PersonDTO> pageResponse = new PageResponse<>(content, pageNumber, pageSize, totalElements, totalPages, last);

        // Then
        assertNotNull(pageResponse);
        assertEquals(content, pageResponse.getContent());
        assertEquals(pageNumber, pageResponse.getPageNumber());
        assertEquals(pageSize, pageResponse.getPageSize());
        assertEquals(totalElements, pageResponse.getTotalElements());
        assertEquals(totalPages, pageResponse.getTotalPages());
        assertEquals(last, pageResponse.isLast());
    }

    @Test
    void testPageResponseSettersAndGetters() {
        // Given
        PageResponse<PersonDTO> pageResponse = new PageResponse<>();
        List<PersonDTO> content = Arrays.asList(
            PersonDTO.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .build()
        );
        int pageNumber = 0;
        int pageSize = 10;
        long totalElements = 1;
        int totalPages = 1;
        boolean last = true;

        // When
        pageResponse.setContent(content);
        pageResponse.setPageNumber(pageNumber);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalElements(totalElements);
        pageResponse.setTotalPages(totalPages);
        pageResponse.setLast(last);

        // Then
        assertEquals(content, pageResponse.getContent());
        assertEquals(pageNumber, pageResponse.getPageNumber());
        assertEquals(pageSize, pageResponse.getPageSize());
        assertEquals(totalElements, pageResponse.getTotalElements());
        assertEquals(totalPages, pageResponse.getTotalPages());
        assertEquals(last, pageResponse.isLast());
    }

    @Test
    void testPageResponseEqualsAndHashCode() {
        // Given
        List<PersonDTO> content = Arrays.asList(
            PersonDTO.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .build()
        );
        
        PageResponse<PersonDTO> pageResponse1 = PageResponse.<PersonDTO>builder()
                .content(content)
                .pageNumber(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();
                
        PageResponse<PersonDTO> pageResponse2 = PageResponse.<PersonDTO>builder()
                .content(content)
                .pageNumber(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();
                
        PageResponse<PersonDTO> pageResponse3 = PageResponse.<PersonDTO>builder()
                .content(content)
                .pageNumber(1) // Different page number
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();

        // Then
        assertEquals(pageResponse1, pageResponse2);
        assertEquals(pageResponse1.hashCode(), pageResponse2.hashCode());
        assertNotEquals(pageResponse1, pageResponse3);
        assertNotEquals(pageResponse1.hashCode(), pageResponse3.hashCode());
    }

    @Test
    void testPageResponseToString() {
        // Given
        List<PersonDTO> content = Arrays.asList(
            PersonDTO.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .build()
        );
        
        PageResponse<PersonDTO> pageResponse = PageResponse.<PersonDTO>builder()
                .content(content)
                .pageNumber(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();

        // When
        String toString = pageResponse.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("content"));
        assertTrue(toString.contains("pageNumber=0"));
        assertTrue(toString.contains("pageSize=10"));
        assertTrue(toString.contains("totalElements=1"));
        assertTrue(toString.contains("totalPages=1"));
        assertTrue(toString.contains("last=true"));
    }
}