package br.com.seatecnologia.hub.postgres.client.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.JpaRepository;

/*
  Autoconfiguração para o módulo postgres-client.
  Esta classe é carregada automaticamente pelo Spring Boot quando o módulo postgres-client
  está no classpath. Ela configura o escaneamento de entidades e repositórios
  para a implementação PostgreSQL, bem como a configuração do banco de dados.
 */
@Configuration
@AutoConfiguration
@ConditionalOnClass(JpaRepository.class)
@EntityScan(basePackages = "br.com.seatecnologia.hub.postgres.client.entity")
@EnableJpaRepositories(basePackages = "br.com.seatecnologia.hub.postgres.client.repository")
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub.postgres.client.repository"
})
@Import(PostgresDatabaseConfig.class)
public class PostgresClientAutoConfiguration {
}
