# PostgreSQL Client Module

## Descrição

O módulo `postgres-client` implementa a persistência de dados em um banco de dados PostgreSQL para o projeto SEA Tecnologia Hub. Esta implementação oferece todas as vantagens de um banco de dados relacional completo, como durabilidade, transações, consultas complexas e escalabilidade.

## Responsabilidades

Este módulo é responsável por:

1. **Implementação da Persistência**: Implementar as interfaces definidas no módulo `persistence-api`
2. **Mapeamento Objeto-Relacional**: Mapear objetos Java para tabelas do PostgreSQL usando JPA/Hibernate
3. **Configuração do Banco de Dados**: Configurar a conexão com o PostgreSQL
4. **Auto-configuração**: Configurar-se automaticamente quando presente no classpath

## Componentes Principais

### Entidades

- **Cliente**: Entidade JPA que representa um cliente no banco de dados
- **Endereco**: Entidade JPA que representa um endereço no banco de dados
- **Telefone**: Entidade JPA que representa um telefone no banco de dados
- **Email**: Entidade JPA que representa um email no banco de dados

### Repositórios

- **ClienteJpaRepository**: Interface Spring Data JPA para acesso ao banco de dados
- **ClienteRepositoryImpl**: Implementação da interface `ClienteRepository` que utiliza JPA

### Configuração

- **PostgresClientAutoConfiguration**: Classe de auto-configuração do Spring Boot
- **PostgresDatabaseConfig**: Configuração específica do banco de dados PostgreSQL

## Características

- **Durabilidade**: Os dados são persistidos de forma durável no banco de dados
- **Transações**: Suporte a transações ACID
- **Consultas Complexas**: Suporte a consultas SQL complexas
- **Escalabilidade**: Capacidade de lidar com grandes volumes de dados
- **Integridade Referencial**: Garantia de integridade dos dados através de chaves estrangeiras

## Como Utilizar

Para utilizar este módulo, adicione a seguinte dependência ao arquivo `pom.xml` do módulo `hub`:

```xml
<dependency>
    <groupId>br.com.seatecnologia</groupId>
    <artifactId>postgres-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Certifique-se de remover ou comentar outras implementações de persistência (como `json-client` ou `memory-client`) para evitar conflitos.

## Configuração

O módulo é configurado através do arquivo `postgres-client.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/seatecnologia
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Estas propriedades definem:
- **URL de conexão**: Endereço do servidor PostgreSQL
- **Credenciais**: Nome de usuário e senha para acesso ao banco
- **Configurações JPA**: Modo de atualização do esquema, exibição de SQL, etc.

## Pré-requisitos

Antes de executar a aplicação com este módulo, você precisa:

1. Ter o PostgreSQL instalado e em execução
2. Criar um banco de dados chamado `seatecnologia` (ou alterar a configuração para usar outro banco)
3. Garantir que as credenciais configuradas tenham permissão para criar/alterar tabelas

## Funcionamento Interno

Internamente, o módulo utiliza:

1. **Spring Data JPA**: Para acesso simplificado ao banco de dados
2. **Hibernate**: Como implementação JPA para mapeamento objeto-relacional
3. **HikariCP**: Para gerenciamento de pool de conexões
4. **Conversão**: As entidades JPA são convertidas para DTOs e vice-versa durante as operações

## Auto-configuração

A classe `PostgresClientAutoConfiguration` é responsável por configurar o Spring Data JPA:

```java
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
    // A configuração é feita através das anotações
}
```

A classe `PostgresDatabaseConfig` carrega as propriedades específicas do PostgreSQL:

```java
@Configuration
@PropertySource("classpath:postgres-client.properties")
public class PostgresDatabaseConfig {
    // Configuração do DataSource, EntityManagerFactory e TransactionManager
}
```

O arquivo `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` registra a classe de auto-configuração:

```
br.com.seatecnologia.hub.postgres.client.config.PostgresClientAutoConfiguration
```

## Casos de Uso

Esta implementação é ideal para:

1. **Ambiente de Produção**: Aplicações em produção que precisam de persistência robusta
2. **Dados Críticos**: Quando a integridade e durabilidade dos dados são essenciais
3. **Consultas Complexas**: Quando são necessárias consultas complexas sobre os dados
4. **Escalabilidade**: Quando a aplicação precisa lidar com grandes volumes de dados
5. **Múltiplas Instâncias**: Quando a aplicação é executada em múltiplas instâncias

## Limitações

- **Complexidade**: Requer configuração e manutenção de um banco de dados
- **Recursos**: Consome mais recursos de sistema que as alternativas mais simples
- **Dependência Externa**: Depende de um servidor PostgreSQL em execução

## Arquitetura

Este módulo segue os princípios de:

1. **Implementação de Interface**: Implementa as interfaces definidas no módulo `persistence-api`
2. **Auto-configuração**: Configura-se automaticamente quando presente no classpath
3. **Separação de Responsabilidades**: Separa entidades, repositórios e configuração
4. **Mapeamento Objeto-Relacional**: Utiliza JPA para mapear objetos para o banco de dados

## Integração com o Projeto

O módulo `postgres-client` se integra ao projeto através da interface `ClienteRepository` definida no módulo `persistence-api`. O módulo `service-client` utiliza esta interface sem conhecer os detalhes de implementação.

Esta arquitetura permite que o módulo `postgres-client` seja substituído por outras implementações (como `json-client` ou `memory-client`) sem modificar o código da aplicação.