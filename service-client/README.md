# Service Client Module

## Descrição

O módulo `service-client` implementa os serviços de negócio para o projeto SEA Tecnologia Hub. Este módulo contém a lógica de negócio da aplicação, implementando as interfaces definidas no módulo `service-api`.

## Responsabilidades

Este módulo é responsável por:

1. **Implementação de Serviços**: Implementar as interfaces definidas no módulo `service-api`
2. **Lógica de Negócio**: Conter as regras de negócio da aplicação
3. **Orquestração**: Coordenar operações entre diferentes repositórios
4. **Validação**: Validar dados de entrada conforme regras de negócio
5. **Auto-configuração**: Configurar-se automaticamente quando presente no classpath

## Componentes Principais

### Serviços

- **ClienteServiceImpl**: Implementação da interface `ClienteService` que contém a lógica de negócio para operações com clientes

### Configuração

- **ServiceClientAutoConfiguration**: Classe de auto-configuração do Spring Boot

## Como Utilizar

Para utilizar este módulo, adicione a seguinte dependência ao arquivo `pom.xml` do módulo `hub`:

```xml
<dependency>
    <groupId>br.com.seatecnologia</groupId>
    <artifactId>service-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Dependências

Este módulo depende dos seguintes módulos:

- **hub-core**: Para utilizar os DTOs e utilitários compartilhados
- **service-api**: Para implementar as interfaces de serviço
- **persistence-api**: Para acessar os repositórios através de suas interfaces

Note que este módulo não depende diretamente de nenhuma implementação específica de persistência (como `postgres-client`, `json-client` ou `memory-client`), apenas da interface definida em `persistence-api`.

## Funcionamento Interno

A implementação do serviço `ClienteServiceImpl` utiliza a interface `ClienteRepository` para acessar os dados:

```java
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    
    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @Override
    public ClienteDTO criar(ClienteDTO cliente) {
        // Validação e lógica de negócio
        if (clienteRepository.existePorCpf(cliente.cpf())) {
            throw new CPFUniqueException("CPF já cadastrado");
        }
        
        return clienteRepository.criar(cliente);
    }
    
    // Outras implementações de métodos...
}
```

## Auto-configuração

A classe `ServiceClientAutoConfiguration` é responsável por registrar a implementação do serviço como um bean do Spring:

```java
@Configuration
@AutoConfiguration
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub.service.client.service"
})
public class ServiceClientAutoConfiguration {
    // A configuração é feita através das anotações
}
```

O arquivo `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` registra esta classe para que o Spring Boot a detecte automaticamente:

```
br.com.seatecnologia.hub.service.client.config.ServiceClientAutoConfiguration
```

## Regras de Negócio

Este módulo implementa as seguintes regras de negócio:

1. **Validação de CPF único**: Verifica se já existe um cliente com o mesmo CPF antes de criar um novo
2. **Validação de dados**: Utiliza as validações definidas nos DTOs (através de anotações como `@CPF`, `@Nome`, etc.)
3. **Tratamento de erros**: Lança exceções apropriadas quando ocorrem erros de negócio

## Arquitetura

Este módulo segue os princípios de:

1. **Implementação de Interface**: Implementa as interfaces definidas no módulo `service-api`
2. **Injeção de Dependência**: Utiliza injeção de dependência para acessar os repositórios
3. **Separação de Responsabilidades**: Separa a lógica de negócio da lógica de acesso a dados
4. **Auto-configuração**: Configura-se automaticamente quando presente no classpath

## Integração com o Projeto

O módulo `service-client` se integra ao projeto através da interface `ClienteService` definida no módulo `service-api`. O módulo `hub` utiliza esta interface sem conhecer os detalhes de implementação.

Esta arquitetura permite que o módulo `service-client` seja substituído por outras implementações sem modificar o código da aplicação.

## Benefícios

A separação da lógica de negócio em um módulo dedicado traz diversos benefícios:

1. **Desacoplamento**: A lógica de negócio é independente da interface do usuário e da persistência
2. **Testabilidade**: Facilidade para testar a lógica de negócio isoladamente
3. **Reutilização**: A lógica de negócio pode ser reutilizada em diferentes interfaces (web, mobile, etc.)
4. **Manutenibilidade**: Mudanças na lógica de negócio não afetam outras partes do sistema
5. **Clareza**: A estrutura do projeto é mais clara, com responsabilidades bem definidas

## Criando uma Implementação Alternativa

Para criar uma implementação alternativa de serviço:

1. Crie um novo módulo (ex: `alternative-service-client`)
2. Adicione as dependências para `service-api`, `persistence-api` e `hub-core`
3. Implemente a interface `ClienteService`
4. Configure a auto-configuração do Spring Boot para o novo módulo
5. Adicione o novo módulo como dependência no módulo `hub` (substituindo `service-client`)

Esta abordagem permite "plugar" e "desplugar" diferentes implementações de serviço, assim como é feito com as implementações de persistência.