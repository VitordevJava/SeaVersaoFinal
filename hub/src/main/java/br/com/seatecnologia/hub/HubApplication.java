package br.com.seatecnologia.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Classe principal da aplicação Hub.
 * <p>
 * Esta classe utiliza o mecanismo de autoconfiguração do Spring Boot para detectar
 * e configurar automaticamente a implementação de persistência presente no classpath.
 * <p>
 * A aplicação não precisa conhecer a implementação específica de persistência
 * (PostgreSQL, Oracle, etc.), pois cada módulo de implementação é responsável por
 * anunciar sua própria configuração.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub",
    "br.com.seatecnologia.hub.core",
    "br.com.seatecnologia.hub.persistence.api",
    "br.com.seatecnologia.hub.service.api",
    "br.com.seatecnologia.hub.service.client"
})
public class HubApplication {
    public static void main(String[] args) {
        SpringApplication.run(HubApplication.class, args);
    }
}
