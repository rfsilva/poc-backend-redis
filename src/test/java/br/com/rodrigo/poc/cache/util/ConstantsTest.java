package br.com.rodrigo.poc.cache.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void constructor_ShouldBePrivate() throws NoSuchMethodException {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        
        constructor.setAccessible(true);
        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Utility class", exception.getCause().getMessage());
    }

    @Test
    void cacheConstants_ShouldHaveExpectedValues() {
        assertEquals("personCache", Constants.Cache.PERSON_CACHE);
        assertEquals("allPersonsCache", Constants.Cache.ALL_PERSONS_CACHE);
    }

    @Test
    void errorMessagesConstants_ShouldHaveExpectedValues() {
        assertEquals("Person not found with id: ", Constants.ErrorMessages.PERSON_NOT_FOUND);
        assertEquals("Validation error", Constants.ErrorMessages.VALIDATION_ERROR);
        assertEquals("An unexpected error occurred. Please contact support.", Constants.ErrorMessages.UNEXPECTED_ERROR);
    }

    @Test
    void endpointsConstants_ShouldHaveExpectedValues() {
        assertEquals("/api", Constants.Endpoints.API_BASE);
        assertEquals("/persons", Constants.Endpoints.PERSONS);
        assertEquals("/persons/{id}", Constants.Endpoints.PERSONS_ID);
        assertEquals("/persons/search", Constants.Endpoints.PERSONS_SEARCH);
        assertEquals("/cache/clear", Constants.Endpoints.CACHE_CLEAR);
    }

    @Test
    void cacheConstructor_ShouldBePrivate() throws NoSuchMethodException {
        Constructor<Constants.Cache> constructor = Constants.Cache.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void errorMessagesConstructor_ShouldBePrivate() throws NoSuchMethodException {
        Constructor<Constants.ErrorMessages> constructor = Constants.ErrorMessages.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void endpointsConstructor_ShouldBePrivate() throws NoSuchMethodException {
        Constructor<Constants.Endpoints> constructor = Constants.Endpoints.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }
}