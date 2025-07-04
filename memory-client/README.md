# Memory Client Module

## Descrição

O módulo `memory-client` implementa a persistência de dados em memória RAM para o projeto SEA Tecnologia Hub. Esta implementação é volátil, ou seja, os dados são perdidos quando a aplicação é reiniciada.

## Responsabilidades

Este módulo é responsável por:

1. **Implementação da Persistência**: Implementar as interfaces definidas no módulo `persistence-api`
2. **Armazenamento em Memória**: Manter os dados em estruturas de dados na memória RAM
3. **Auto-configuração**: Configurar-se automaticamente quando presente no classpath

## Componentes Principais

### Repositórios

- **ClienteRepositoryImpl**: Implementação da interface `ClienteRepository` que armazena dados em memória

### Configuração

- **MemoryClientAutoConfiguration**: Classe de auto-configuração do Spring Boot

## Características

- **Volatilidade**: Os dados são perdidos quando a aplicação é reiniciada
- **Velocidade**: Extremamente rápido por não envolver I/O de disco ou rede
- **Simplicidade**: Implementação simples e direta
- **Geração de IDs**: Gera IDs sequenciais para novos clientes

## Como Utilizar

Para utilizar este módulo, adicione a seguinte dependência ao arquivo `pom.xml` do módulo `hub`:

```xml
<dependency>
    <groupId>br.com.seatecnologia</groupId>
    <artifactId>memory-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Certifique-se de remover ou comentar outras implementações de persistência (como `postgres-client` ou `json-client`) para evitar conflitos.

## Funcionamento Interno

Internamente, o módulo utiliza um `Map` para armazenar os clientes, usando o ID como chave:

```java
private final Map<Long, ClienteDTO> clientes = new ConcurrentHashMap<>();
private final AtomicLong sequence = new AtomicLong(1);
```

As operações CRUD são implementadas manipulando este mapa:

- **criar**: Gera um novo ID e adiciona o cliente ao mapa
- **buscarPorId**: Retorna o cliente com o ID especificado
- **listarTodos**: Retorna todos os clientes do mapa
- **atualizar**: Substitui o cliente existente no mapa
- **deletar**: Remove o cliente do mapa
- **existePorCpf**: Verifica se existe um cliente com o CPF informado

## Auto-configuração

A classe `MemoryClientAutoConfiguration` é responsável por registrar a implementação do repositório como um bean do Spring:

```java
@Configuration
@AutoConfiguration
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub.memory.client.repository"
})
public class MemoryClientAutoConfiguration {
    // A configuração é feita através das anotações
}
```

O arquivo `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` registra esta classe para que o Spring Boot a detecte automaticamente:

```
br.com.seatecnologia.hub.memory.client.config.MemoryClientAutoConfiguration
```

## Casos de Uso

Esta implementação é ideal para:

1. **Desenvolvimento**: Ambiente de desenvolvimento onde a persistência dos dados não é crítica
2. **Testes**: Testes automatizados que precisam de um repositório rápido e isolado
3. **Demonstrações**: Demonstrações rápidas da aplicação sem necessidade de configurar um banco de dados
4. **Prototipagem**: Desenvolvimento de protótipos e provas de conceito

## Limitações

- **Volatilidade**: Os dados são perdidos quando a aplicação é reiniciada
- **Escalabilidade**: Não é adequado para grandes volumes de dados
- **Concorrência**: Pode ter problemas de concorrência em ambientes com muitos usuários simultâneos
- **Distribuição**: Não funciona em ambientes distribuídos (múltiplas instâncias da aplicação)

## Arquitetura

Este módulo segue os princípios de:

1. **Implementação de Interface**: Implementa as interfaces definidas no módulo `persistence-api`
2. **Auto-configuração**: Configura-se automaticamente quando presente no classpath
3. **Simplicidade**: Implementação simples e direta, sem dependências externas

## Integração com o Projeto

O módulo `memory-client` se integra ao projeto através da interface `ClienteRepository` definida no módulo `persistence-api`. O módulo `service-client` utiliza esta interface sem conhecer os detalhes de implementação.

Esta arquitetura permite que o módulo `memory-client` seja substituído por outras implementações (como `postgres-client` ou `json-client`) sem modificar o código da aplicação.