package br.com.seatecnologia.hub.service.client.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/*
  Autoconfiguração para o módulo service-client.
  Esta classe é carregada automaticamente pelo Spring Boot quando o módulo service-client
  está no classpath. Ela configura o escaneamento de componentes para as implementações de serviço.
 */
@Configuration
@AutoConfiguration
@ConditionalOnClass(Service.class)
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub.service.client.service"
})
public class ServiceClientAutoConfiguration {
}
