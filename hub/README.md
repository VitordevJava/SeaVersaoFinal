# Hub Module

## Descrição

O módulo `hub` é a aplicação principal do projeto SEA Tecnologia Hub. Ele integra todos os outros módulos e expõe as APIs REST para interação com o sistema.

## Responsabilidades

Este módulo é responsável por:

1. **Configuração da Aplicação**: Configuração do Spring Boot, segurança, etc.
2. **Exposição de APIs**: Controladores REST para acesso às funcionalidades do sistema
3. **Integração de Módulos**: Orquestração dos diferentes módulos do projeto
4. **Segurança**: Autenticação e autorização de usuários

## Componentes Principais

### Controladores

- **ClienteController**: Expõe endpoints REST para operações CRUD de clientes

### Configuração

- **SecurityConfig**: Configura a segurança da aplicação, incluindo autenticação e autorização

### Tratamento de Exceções

- **GlobalExceptionHandler**: Trata exceções de forma centralizada e retorna respostas HTTP apropriadas
- **CPFUniqueException**: Exceção específica para violação de unicidade de CPF

## Segurança

A aplicação possui dois perfis de usuário:

1. **Usuário Admin**
   - Login: admin
   - Senha: 123qw@ADMIN
   - Permissões: Acesso total para criar, ler, atualizar e apagar dados (CRUD).

2. **Usuário Padrão**
   - Login: padrao
   - Senha: 123qw@USER
   - Permissões: Acesso somente para visualização dos dados.

## Endpoints da API

### Clientes

- **GET /api/clientes**: Lista todos os clientes
  - Requer: Autenticação (ROLE_USER ou ROLE_ADMIN)
  - Retorna: Lista de ClienteDTO

- **GET /api/clientes/{id}**: Busca um cliente pelo ID
  - Requer: Autenticação (ROLE_USER ou ROLE_ADMIN)
  - Retorna: ClienteDTO ou 404 Not Found

- **POST /api/clientes**: Cria um novo cliente
  - Requer: Autenticação (ROLE_ADMIN)
  - Body: ClienteDTO
  - Retorna: ClienteDTO criado com status 201 Created

- **PUT /api/clientes/{id}**: Atualiza um cliente existente
  - Requer: Autenticação (ROLE_ADMIN)
  - Body: ClienteDTO
  - Retorna: ClienteDTO atualizado ou 404 Not Found

- **DELETE /api/clientes/{id}**: Remove um cliente
  - Requer: Autenticação (ROLE_ADMIN)
  - Retorna: 204 No Content ou 404 Not Found

## Como Executar

Para executar este módulo:

```bash
cd hub
mvn spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

## Configuração

A configuração da aplicação é feita através do arquivo `application.properties`:

```properties
# Server Configuration
server.port=8080

# Logging Configuration
logging.level.org.springframework=INFO
logging.level.br.com.seatecnologia=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

## Dependências

Este módulo depende dos seguintes módulos:

- **hub-core**: Para utilizar os DTOs e utilitários compartilhados
- **service-api**: Para acessar as interfaces de serviço
- **service-client**: Para utilizar a implementação dos serviços
- **Um cliente de persistência**: Para armazenar os dados (postgres-client, json-client ou memory-client)

## Trocando a Implementação de Persistência

Uma das principais vantagens da arquitetura multi-módulo é a capacidade de trocar implementações facilmente. Para trocar o cliente de persistência:

1. Edite o arquivo `pom.xml` do módulo `hub`:

```xml
<!-- Remova ou comente a implementação atual -->
<dependency>
  <groupId>br.com.seatecnologia</groupId>
  <artifactId>postgres-client</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>

<!-- Adicione a nova implementação desejada -->
<dependency>
  <groupId>br.com.seatecnologia</groupId>
  <artifactId>json-client</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>

<!-- OU -->

<dependency>
  <groupId>br.com.seatecnologia</groupId>
  <artifactId>memory-client</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

2. Reconstrua e execute a aplicação:

```bash
mvn clean install
mvn spring-boot:run
```

Não é necessário modificar nenhum código da aplicação, pois o Spring Boot detectará automaticamente a implementação presente no classpath e a usará.

## Arquitetura

Este módulo segue os princípios de:

1. **Separação de responsabilidades**: Cada componente tem uma única responsabilidade bem definida
2. **Injeção de dependências**: Utiliza o Spring para injetar dependências
3. **Segurança declarativa**: Utiliza anotações para controle de acesso
4. **Tratamento centralizado de exceções**: Utiliza um handler global para tratar exceções

## Integração com o Projeto

O módulo `hub` é o ponto central do projeto, integrando todos os outros módulos:

- Utiliza os **DTOs** do módulo `hub-core` para transferência de dados
- Acessa os **serviços** definidos no módulo `service-api` e implementados no módulo `service-client`
- Indiretamente, utiliza a **persistência** implementada por um dos módulos de cliente (postgres-client, json-client ou memory-client)

Esta arquitetura permite que o módulo `hub` não precise conhecer os detalhes de implementação dos outros módulos, apenas suas interfaces públicas.