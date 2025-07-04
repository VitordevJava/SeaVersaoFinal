# SEA Tecnologia Hub

Este é um projeto multi-módulo Spring Boot para o desafio da SEA Tecnologia. A arquitetura multi-módulo permite o desacoplamento de componentes e a flexibilidade para "plugar" e "desplugar" implementações livremente.

## Arquitetura Multi-Módulo

A arquitetura multi-módulo deste projeto segue os princípios de:

1. **Desacoplamento**: Cada módulo tem uma responsabilidade específica e bem definida.
2. **Substituibilidade**: Implementações podem ser trocadas sem modificar o código da aplicação principal.
3. **Auto-configuração**: Cada módulo anuncia sua própria configuração para o Spring Boot.
4. **Detecção automática**: O Spring Boot detecta e aplica a configuração do módulo presente no classpath.

### Benefícios da Arquitetura Multi-Módulo

- **Desacoplamento**: A aplicação não depende de implementações específicas.
- **Flexibilidade**: É possível trocar implementações sem modificar o código da aplicação.
- **Testabilidade**: Facilita a criação de testes usando implementações mais simples.
- **Evolução**: Permite evoluir ou substituir componentes sem afetar as outras partes do sistema.
- **Desenvolvimento Paralelo**: Diferentes equipes podem trabalhar em diferentes implementações.
- **Manutenibilidade**: Cada módulo é responsável apenas por suas próprias configurações.
- **Clareza**: A estrutura do projeto é mais clara, com responsabilidades bem definidas.

## Estrutura do Projeto

O projeto está organizado nos seguintes módulos:

- **hub-core**: Contém DTOs, validadores e utilitários compartilhados por todos os módulos.
- **persistence-api**: Define interfaces para acesso a dados, sem implementações concretas.
- **memory-client**: Implementação da persistência usando memória RAM (volátil).
- **json-client**: Implementação da persistência usando arquivos JSON.
- **postgres-client**: Implementação da persistência usando PostgreSQL.
- **service-api**: Define interfaces para serviços de negócio.
- **service-client**: Implementação dos serviços de negócio.
- **hub**: Aplicação principal que integra todos os módulos e expõe APIs REST.

## Como "Plugar" e "Desplugar" Implementações

Uma das principais vantagens desta arquitetura é a capacidade de trocar implementações facilmente. Vamos usar os clientes de persistência como exemplo:

### Exemplo: Trocar entre Diferentes Clientes de Persistência

O projeto inclui três implementações de persistência:
1. **postgres-client**: Armazena dados em um banco de dados PostgreSQL.
2. **json-client**: Armazena dados em arquivos JSON.
3. **memory-client**: Armazena dados apenas em memória RAM.

Para trocar entre elas:

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
cd hub
mvn spring-boot:run
```

Não é necessário modificar nenhum código da aplicação, pois o Spring Boot detectará automaticamente a implementação presente no classpath e a usará.

### Como Funciona?

Cada módulo de implementação (postgres-client, json-client, memory-client) inclui:

1. **Auto-configuração**: Uma classe anotada com `@Configuration` e `@AutoConfiguration`.
2. **Registro da auto-configuração**: Um arquivo `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` que registra a classe de auto-configuração.
3. **Implementação da interface**: Classes que implementam as interfaces definidas no módulo `persistence-api`.

Quando o Spring Boot inicia, ele:
1. Detecta as classes de auto-configuração registradas.
2. Aplica as configurações correspondentes.
3. Injeta as implementações nos componentes que dependem das interfaces.

## Adicionando Novas Implementações

Para adicionar uma nova implementação (por exemplo, um cliente para MongoDB):

1. Crie um novo módulo (ex: `mongodb-client`).
2. Implemente as interfaces definidas no módulo `persistence-api`.
3. Crie uma classe de auto-configuração.
4. Registre a auto-configuração no arquivo `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`.
5. Adicione o novo módulo como dependência no módulo `hub`.

## Requisitos

- Java 17
- Maven 3.8+
- PostgreSQL 14+ (se usar o postgres-client)

## Configuração do Banco de Dados (para postgres-client)

Antes de executar a aplicação com o postgres-client, você precisa criar um banco de dados PostgreSQL:

```sql
CREATE DATABASE seatecnologia;
```

As credenciais padrão configuradas são:
- **URL**: jdbc:postgresql://localhost:5432/seatecnologia
- **Usuário**: postgres
- **Senha**: postgres

Se você precisar alterar essas configurações, edite o arquivo `postgres-client/src/main/resources/postgres-client.properties`.

## Como Executar

1. Clone o repositório
2. Configure o banco de dados PostgreSQL (se usar o postgres-client)
3. Execute o seguinte comando na raiz do projeto:

```bash
mvn clean install
cd hub
mvn spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

## Documentação da API

A aplicação inclui documentação completa da API REST:

1. **Interface Web**: Acesse `http://localhost:8080/` no seu navegador para uma visão geral da API
2. **Documentação Detalhada**: Acesse `http://localhost:8080/api-docs.md` para informações completas sobre cada endpoint

A documentação inclui:
- Detalhes de autenticação
- Endpoints disponíveis
- Exemplos de requisições e respostas
- Estrutura de dados
- Validações
- Tratamento de erros

## Autenticação

A aplicação possui dois perfis de usuário:

1. **Usuário Admin**
   - Login: admin
   - Senha: 123qw@ADMIN
   - Permissões: Acesso total para criar, ler, atualizar e apagar dados (CRUD).

2. **Usuário Padrão**
   - Login: padrao
   - Senha: 123qw@USER
   - Permissões: Acesso somente para visualização dos dados.

## Solução de Problemas

Se você encontrar problemas ao executar o projeto:

1. Verifique se o PostgreSQL está em execução e acessível (se usar o postgres-client)
2. Verifique se as credenciais do banco de dados estão corretas
3. Certifique-se de que todas as dependências foram baixadas corretamente
4. Verifique os logs da aplicação para identificar erros específicos

## Estrutura de Pacotes

- `br.com.seatecnologia.hub`: Pacote principal da aplicação
- `br.com.seatecnologia.hub.core`: DTOs e utilitários
- `br.com.seatecnologia.hub.persistence.api`: Interfaces de repositório
- `br.com.seatecnologia.hub.memory.client`: Implementação da persistência em memória
- `br.com.seatecnologia.hub.json.client`: Implementação da persistência em JSON
- `br.com.seatecnologia.hub.postgres.client`: Implementação da persistência em PostgreSQL
- `br.com.seatecnologia.hub.service.api`: Interfaces de serviço
- `br.com.seatecnologia.hub.service.client`: Implementação dos serviços

Consulte os README.md individuais de cada módulo para mais detalhes sobre suas funcionalidades e configurações específicas.
