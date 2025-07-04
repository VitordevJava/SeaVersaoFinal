package br.com.seatecnologia.hub.json.client.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Autoconfiguração para o módulo json-client.
 * Esta classe é carregada automaticamente pelo Spring Boot quando o módulo json-client
 * está no classpath. Ela configura o escaneamento de componentes para a implementação
 * de persistência baseada em JSON.
 */
@Configuration
@AutoConfiguration
@ConditionalOnClass(ObjectMapper.class)
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub.json.client.repository",
    "br.com.seatecnologia.hub.json.client.config"
})
@PropertySource("classpath:json-client.properties")
public class JsonClientAutoConfiguration {
    // Não são necessários beans adicionais, as anotações acima fazem todo o trabalho
}