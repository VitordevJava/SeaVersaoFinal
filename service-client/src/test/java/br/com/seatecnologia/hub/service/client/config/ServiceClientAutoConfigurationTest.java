package br.com.seatecnologia.hub.service.client.config;

import br.com.seatecnologia.hub.persistence.api.repository.ClienteRepository;
import br.com.seatecnologia.hub.service.api.service.ClienteService;
import br.com.seatecnologia.hub.service.client.service.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Testes unitários para a autoconfiguração do módulo service-client.
 */
public class ServiceClientAutoConfigurationTest {

    // ApplicationContextRunner para testar a autoconfiguração
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ServiceClientAutoConfiguration.class))
            .withUserConfiguration(TestConfiguration.class);

    /**
     * Configuração de teste que fornece um mock do ClienteRepository
     * necessário para a criação do ClienteServiceImpl.
     */
    @Configuration
    static class TestConfiguration {
        @Bean
        public ClienteRepository clienteRepository() {
            return mock(ClienteRepository.class);
        }
    }

    @Test
    public void shouldRegisterClienteServiceImpl() {
        // Verificar se o ClienteServiceImpl é registrado como um bean
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(ClienteServiceImpl.class);
            assertThat(context).hasSingleBean(ClienteService.class);
            
            // Verificar se o bean é do tipo correto
            assertThat(context.getBean(ClienteService.class)).isInstanceOf(ClienteServiceImpl.class);
            
            // Verificar se o bean foi injetado com o ClienteRepository
            ClienteServiceImpl service = context.getBean(ClienteServiceImpl.class);
            assertThat(service).isNotNull();
        });
    }

    @Test
    public void shouldScanCorrectPackage() {
        // Verificar se o pacote correto está sendo escaneado
        contextRunner.run(context -> {
            assertThat(context).hasBean("clienteServiceImpl");
        });
    }
}