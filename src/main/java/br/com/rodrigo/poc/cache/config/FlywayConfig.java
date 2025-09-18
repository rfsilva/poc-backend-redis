package br.com.rodrigo.poc.cache.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

/**
 * Configuração do Flyway para gerenciamento de migrações de banco de dados
 */
@Configuration
public class FlywayConfig {

    @Value("${spring.flyway.locations}")
    private String flywayLocations;

    @Value("${spring.flyway.baseline-on-migrate}")
    private boolean baselineOnMigrate;

    @Value("${spring.flyway.validate-on-migrate}")
    private boolean validateOnMigrate;

    @Value("${spring.flyway.schemas:public}")
    private String schemas;

    /**
     * Configura o Flyway com as propriedades definidas no application.yml
     *
     * @param dataSource A fonte de dados configurada
     * @return A instância do Flyway configurada
     */
    @Bean(initMethod = "migrate")
    @DependsOn("dataSource")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(flywayLocations)
                .baselineOnMigrate(baselineOnMigrate)
                .validateOnMigrate(validateOnMigrate)
                .schemas(schemas)
                .load();
    }
}