package br.com.rodrigo.poc.cache.repository;

import br.com.rodrigo.poc.cache.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByNameContainingIgnoreCase(String name);
    
    List<Person> findByEmail(String email);
}