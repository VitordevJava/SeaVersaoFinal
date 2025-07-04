package br.com.seatecnologia.hub.memory.client.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguração para o módulo memory-client.
 * Esta classe é carregada automaticamente pelo Spring Boot quando o módulo memory-client
 * está no classpath. Ela configura o escaneamento de componentes para a implementação
 * de persistência baseada em memória.
 */
@Configuration
@AutoConfiguration
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub.memory.client.repository"
})
public class MemoryClientAutoConfiguration {
    // Não são necessários beans adicionais, as anotações acima fazem todo o trabalho
}