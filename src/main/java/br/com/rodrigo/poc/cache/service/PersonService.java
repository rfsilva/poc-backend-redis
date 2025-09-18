package br.com.rodrigo.poc.cache.service;

import br.com.rodrigo.poc.cache.exception.ResourceNotFoundException;
import br.com.rodrigo.poc.cache.model.Person;
import br.com.rodrigo.poc.cache.model.dto.PageResponse;
import br.com.rodrigo.poc.cache.model.dto.PersonDTO;
import br.com.rodrigo.poc.cache.repository.PersonRepository;
import br.com.rodrigo.poc.cache.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de pessoas com suporte a cache
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    
    /**
     * Busca todas as pessoas
     * Não utilizamos cache aqui para evitar problemas com listas grandes
     * e para garantir que sempre temos dados atualizados na listagem
     * 
     * @return Lista de DTOs de pessoas
     */
    @Transactional(readOnly = true)
    public List<PersonDTO> findAll() {
        log.info("Fetching all persons from database");
        return personRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca todas as pessoas com paginação
     * 
     * @param page Número da página (começando em 0)
     * @param size Tamanho da página
     * @param sortBy Campo para ordenação
     * @param direction Direção da ordenação (ASC ou DESC)
     * @return Resposta paginada com DTOs de pessoas
     */
    @Transactional(readOnly = true)
    public PageResponse<PersonDTO> findAllPaged(int page, int size, String sortBy, String direction) {
        log.info("Fetching persons page {} with size {}", page, size);
        
        Sort sort = direction.equalsIgnoreCase("DESC") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Person> personPage = personRepository.findAll(pageable);
        
        List<PersonDTO> personDTOs = personPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return PageResponse.<PersonDTO>builder()
                .content(personDTOs)
                .pageNumber(personPage.getNumber())
                .pageSize(personPage.getSize())
                .totalElements(personPage.getTotalElements())
                .totalPages(personPage.getTotalPages())
                .last(personPage.isLast())
                .build();
    }
    
    /**
     * Busca pessoa por ID
     * Utiliza cache para evitar consultas repetidas ao banco
     * 
     * @param id ID da pessoa
     * @return DTO da pessoa encontrada
     * @throws ResourceNotFoundException se a pessoa não for encontrada
     */
    @Transactional(readOnly = true)
    @Cacheable(value = Constants.Cache.PERSON_CACHE, key = "#id", unless = "#result == null")
    public PersonDTO findById(UUID id) {
        log.info("Fetching person with id {} from database", id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ErrorMessages.PERSON_NOT_FOUND + id));
        return convertToDTO(person);
    }
    
    /**
     * Busca pessoas por nome
     * Não utilizamos cache aqui pois é uma consulta dinâmica
     * 
     * @param name Nome ou parte do nome para busca
     * @return Lista de DTOs de pessoas que correspondem ao critério
     */
    @Transactional(readOnly = true)
    public List<PersonDTO> findByName(String name) {
        log.info("Searching persons with name containing: {}", name);
        return personRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca pessoas por nome com paginação
     * 
     * @param name Nome ou parte do nome para busca
     * @param page Número da página (começando em 0)
     * @param size Tamanho da página
     * @param sortBy Campo para ordenação
     * @param direction Direção da ordenação (ASC ou DESC)
     * @return Resposta paginada com DTOs de pessoas que correspondem ao critério
     */
    @Transactional(readOnly = true)
    public PageResponse<PersonDTO> findByNamePaged(String name, int page, int size, String sortBy, String direction) {
        log.info("Searching persons with name containing: {} (page: {}, size: {})", name, page, size);
        
        Sort sort = direction.equalsIgnoreCase("DESC") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Person> personPage = personRepository.findByNameContainingIgnoreCase(name, pageable);
        
        List<PersonDTO> personDTOs = personPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return PageResponse.<PersonDTO>builder()
                .content(personDTOs)
                .pageNumber(personPage.getNumber())
                .pageSize(personPage.getSize())
                .totalElements(personPage.getTotalElements())
                .totalPages(personPage.getTotalPages())
                .last(personPage.isLast())
                .build();
    }
    
    /**
     * Cria uma nova pessoa
     * 
     * @param personDTO DTO com os dados da pessoa a ser criada
     * @return DTO da pessoa criada com ID gerado
     */
    public PersonDTO create(PersonDTO personDTO) {
        Objects.requireNonNull(personDTO, "PersonDTO cannot be null");
        log.info("Creating new person: {}", personDTO.getName());
        Person person = convertToEntity(personDTO);
        Person savedPerson = personRepository.save(person);
        return convertToDTO(savedPerson);
    }
    
    /**
     * Atualiza uma pessoa existente
     * Atualiza o cache com o novo valor
     * 
     * @param id ID da pessoa a ser atualizada
     * @param personDTO DTO com os novos dados
     * @return DTO da pessoa atualizada
     * @throws ResourceNotFoundException se a pessoa não for encontrada
     */
    @CachePut(value = Constants.Cache.PERSON_CACHE, key = "#id")
    public PersonDTO update(UUID id, PersonDTO personDTO) {
        Objects.requireNonNull(personDTO, "PersonDTO cannot be null");
        log.info("Updating person with id: {}", id);
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ErrorMessages.PERSON_NOT_FOUND + id));
        
        updatePersonFields(existingPerson, personDTO);
        
        Person updatedPerson = personRepository.save(existingPerson);
        return convertToDTO(updatedPerson);
    }
    
    /**
     * Remove uma pessoa
     * Limpa o cache após a remoção
     * 
     * @param id ID da pessoa a ser removida
     * @throws ResourceNotFoundException se a pessoa não for encontrada
     */
    @Caching(evict = {
        @CacheEvict(value = Constants.Cache.PERSON_CACHE, key = "#id"),
        @CacheEvict(value = Constants.Cache.ALL_PERSONS_CACHE, allEntries = true)
    })
    public void delete(UUID id) {
        log.info("Deleting person with id: {}", id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ErrorMessages.PERSON_NOT_FOUND + id));
        personRepository.delete(person);
    }
    
    /**
     * Limpa todo o cache de pessoas
     */
    @CacheEvict(value = {Constants.Cache.PERSON_CACHE, Constants.Cache.ALL_PERSONS_CACHE}, allEntries = true)
    public void clearCache() {
        log.info("Clearing all person caches");
    }
    
    /**
     * Atualiza os campos de uma pessoa com os dados do DTO
     * 
     * @param person Entidade pessoa a ser atualizada
     * @param personDTO DTO com os novos dados
     */
    private void updatePersonFields(Person person, PersonDTO personDTO) {
        person.setName(personDTO.getName());
        person.setEmail(personDTO.getEmail());
        person.setAddress(personDTO.getAddress());
        person.setPhoneNumber(personDTO.getPhoneNumber());
    }
    
    /**
     * Converte uma entidade Person para um DTO
     * 
     * @param person Entidade a ser convertida
     * @return DTO correspondente
     */
    private PersonDTO convertToDTO(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .address(person.getAddress())
                .phoneNumber(person.getPhoneNumber())
                .build();
    }
    
    /**
     * Converte um DTO para uma entidade Person
     * 
     * @param dto DTO a ser convertido
     * @return Entidade correspondente
     */
    private Person convertToEntity(PersonDTO dto) {
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}