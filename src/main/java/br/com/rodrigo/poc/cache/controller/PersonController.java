package br.com.rodrigo.poc.cache.controller;

import br.com.rodrigo.poc.cache.model.dto.PersonDTO;
import br.com.rodrigo.poc.cache.service.PersonService;
import br.com.rodrigo.poc.cache.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações com pessoas
 */
@RestController
@RequestMapping(Constants.Endpoints.API_BASE + Constants.Endpoints.PERSONS)
@Validated
@Tag(name = "Person API", description = "API para gerenciamento de pessoas")
public class PersonController {

    private final PersonService personService;
    
    /**
     * Construtor para injeção de dependências
     * 
     * @param personService Serviço de pessoas
     */
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    /**
     * Retorna todas as pessoas cadastradas
     * 
     * @return Lista de pessoas
     */
    @GetMapping
    @Operation(summary = "Listar todas as pessoas", description = "Retorna uma lista com todas as pessoas cadastradas")
    @ApiResponse(responseCode = "200", description = "Pessoas encontradas com sucesso")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return ResponseEntity.ok(personService.findAll());
    }
    
    /**
     * Busca uma pessoa pelo ID
     * 
     * @param id ID da pessoa
     * @return Pessoa encontrada
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa por ID", description = "Retorna uma pessoa específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PersonDTO> getPersonById(
            @Parameter(description = "ID da pessoa", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }
    
    /**
     * Busca pessoas pelo nome
     * 
     * @param name Nome ou parte do nome para busca
     * @return Lista de pessoas que correspondem ao critério
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar pessoas por nome", description = "Retorna uma lista de pessoas que contêm o nome informado")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public ResponseEntity<List<PersonDTO>> searchPersonsByName(
            @Parameter(description = "Nome ou parte do nome para busca", required = true)
            @RequestParam @NotBlank String name) {
        return ResponseEntity.ok(personService.findByName(name));
    }
    
    /**
     * Cria uma nova pessoa
     * 
     * @param personDTO Dados da pessoa a ser criada
     * @return Pessoa criada com ID gerado
     */
    @PostMapping
    @Operation(summary = "Criar pessoa", description = "Cria uma nova pessoa com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PersonDTO> createPerson(
            @Parameter(description = "Dados da pessoa", required = true)
            @Valid @RequestBody PersonDTO personDTO) {
        return new ResponseEntity<>(personService.create(personDTO), HttpStatus.CREATED);
    }
    
    /**
     * Atualiza uma pessoa existente
     * 
     * @param id ID da pessoa a ser atualizada
     * @param personDTO Novos dados da pessoa
     * @return Pessoa atualizada
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa", description = "Atualiza os dados de uma pessoa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PersonDTO> updatePerson(
            @Parameter(description = "ID da pessoa", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados da pessoa", required = true)
            @Valid @RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(personService.update(id, personDTO));
    }
    
    /**
     * Remove uma pessoa
     * 
     * @param id ID da pessoa a ser removida
     * @return Resposta sem conteúdo
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pessoa", description = "Remove uma pessoa pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pessoa removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "ID da pessoa", required = true)
            @PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Limpa o cache de pessoas
     * 
     * @return Resposta sem conteúdo
     */
    @PostMapping(Constants.Endpoints.CACHE_CLEAR)
    @Operation(summary = "Limpar cache", description = "Limpa todo o cache de pessoas")
    @ApiResponse(responseCode = "200", description = "Cache limpo com sucesso")
    public ResponseEntity<Void> clearCache() {
        personService.clearCache();
        return ResponseEntity.ok().build();
    }
}