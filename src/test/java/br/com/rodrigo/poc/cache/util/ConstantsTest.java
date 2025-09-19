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
    void messageCodesConstants_ShouldHaveExpectedValues() {
        assertEquals("error.person.notFound", Constants.MessageCodes.PERSON_NOT_FOUND);
        assertEquals("error.validation", Constants.MessageCodes.VALIDATION_ERROR);
        assertEquals("error.unexpected", Constants.MessageCodes.UNEXPECTED_ERROR);
        assertEquals("error.cpf.invalid", Constants.MessageCodes.INVALID_CPF);
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
    void messageCodesConstructor_ShouldBePrivate() throws NoSuchMethodException {
        Constructor<Constants.MessageCodes> constructor = Constants.MessageCodes.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void endpointsConstructor_ShouldBePrivate() throws NoSuchMethodException {
        Constructor<Constants.Endpoints> constructor = Constants.Endpoints.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }
}