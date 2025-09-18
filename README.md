# Redis Cache Demo with PostgreSQL and Flyway

Este projeto demonstra a implementação de cache Redis com Spring Boot, utilizando PostgreSQL como banco de dados principal e Flyway para gerenciamento de migrações.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Data Redis
- PostgreSQL
- Flyway
- Redis
- Docker e Docker Compose
- JUnit 5
- Testcontainers
- Swagger/OpenAPI

## Pré-requisitos

- Java 21
- Maven
- Docker e Docker Compose

## Configuração e Execução

### Iniciar os Serviços com Docker Compose

```bash
docker-compose up -d
```

Este comando iniciará:
- PostgreSQL na porta 5432
- Redis na porta 6379
- Redis Commander na porta 8081
- PgAdmin na porta 5050

### Compilar e Executar a Aplicação

```bash
mvn clean install
mvn spring-boot:run
```

### Acessar a Documentação da API

A documentação da API está disponível em:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI: http://localhost:8080/api-docs

### Acessar o PgAdmin

- URL: http://localhost:5050
- Email: admin@admin.com
- Senha: admin

### Acessar o Redis Commander

- URL: http://localhost:8081

## Estrutura do Projeto

- `src/main/java/br/com/rodrigo/poc/cache`: Código fonte da aplicação
- `src/main/resources/db/migration`: Scripts de migração do Flyway
- `src/test`: Testes unitários e de integração

## Migrações de Banco de Dados

O projeto utiliza Flyway para gerenciar as migrações de banco de dados. Os scripts de migração estão localizados em `src/main/resources/db/migration` e são executados automaticamente na inicialização da aplicação.

## Testes

Para executar os testes:

```bash
mvn test
```

## Cobertura de Código

Para gerar o relatório de cobertura de código:

```bash
mvn clean verify
```

O relatório será gerado em `target/site/jacoco/index.html`.

## Análise de Qualidade de Código

Para executar a análise do SonarQube:

```bash
mvn clean verify sonar:sonar -Psonar
```