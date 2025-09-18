package br.com.rodrigo.poc.cache.repository;

import br.com.rodrigo.poc.cache.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findByNameContainingIgnoreCase(String name);
    
    Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    List<Person> findByEmail(String email);
}