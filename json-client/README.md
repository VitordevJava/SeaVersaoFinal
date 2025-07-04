# JSON Client Module

## Descrição

O módulo `json-client` implementa a persistência de dados em arquivos JSON para o projeto SEA Tecnologia Hub. Esta implementação armazena os dados em disco, permitindo que eles sejam mantidos entre reinicializações da aplicação.

## Responsabilidades

Este módulo é responsável por:

1. **Implementação da Persistência**: Implementar as interfaces definidas no módulo `persistence-api`
2. **Armazenamento em JSON**: Salvar e carregar dados de arquivos JSON em disco
3. **Auto-configuração**: Configurar-se automaticamente quando presente no classpath

## Componentes Principais

### Entidades

- **Cliente**: Representa um cliente no formato de armazenamento JSON
- **Endereco**: Representa um endereço no formato de armazenamento JSON
- **Telefone**: Representa um telefone no formato de armazenamento JSON
- **Email**: Representa um email no formato de armazenamento JSON

### Repositórios

- **ClienteRepositoryImpl**: Implementação da interface `ClienteRepository` que armazena dados em arquivos JSON

### Configuração

- **JsonClientAutoConfiguration**: Classe de auto-configuração do Spring Boot

## Características

- **Persistência**: Os dados são mantidos entre reinicializações da aplicação
- **Simplicidade**: Não requer configuração de banco de dados
- **Portabilidade**: Os arquivos JSON podem ser facilmente transferidos entre ambientes
- **Legibilidade**: Os dados armazenados são legíveis por humanos
- **Geração de IDs**: Gera IDs sequenciais para novos clientes

## Como Utilizar

Para utilizar este módulo, adicione a seguinte dependência ao arquivo `pom.xml` do módulo `hub`:

```xml
<dependency>
    <groupId>br.com.seatecnologia</groupId>
    <artifactId>json-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Certifique-se de remover ou comentar outras implementações de persistência (como `postgres-client` ou `memory-client`) para evitar conflitos.

## Configuração

O módulo pode ser configurado através das seguintes propriedades no arquivo `json-client.properties`:

```properties
json.client.data.folder=./data/json
json.client.data.file=clientes.json
```

Estas propriedades definem:
- **json.client.data.folder**: O diretório onde o arquivo JSON será armazenado
- **json.client.data.file**: O nome do arquivo JSON

## Funcionamento Interno

Internamente, o módulo utiliza a biblioteca Jackson para serialização e deserialização de objetos Java para JSON:

1. **Inicialização**: Ao iniciar, o repositório carrega os dados do arquivo JSON (se existir)
2. **Operações CRUD**: Cada operação CRUD manipula a estrutura de dados em memória e salva as alterações no arquivo JSON
3. **Conversão**: As entidades são convertidas para DTOs e vice-versa durante as operações

## Auto-configuração

A classe `JsonClientAutoConfiguration` é responsável por registrar a implementação do repositório como um bean do Spring:

```java
@Configuration
@AutoConfiguration
@ComponentScan(basePackages = {
    "br.com.seatecnologia.hub.json.client.repository"
})
public class JsonClientAutoConfiguration {
    // A configuração é feita através das anotações
}
```

O arquivo `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` registra esta classe para que o Spring Boot a detecte automaticamente:

```
br.com.seatecnologia.hub.json.client.config.JsonClientAutoConfiguration
```

## Casos de Uso

Esta implementação é ideal para:

1. **Aplicações Simples**: Aplicações que não necessitam de um banco de dados completo
2. **Desenvolvimento**: Ambiente de desenvolvimento onde a configuração de um banco de dados é desnecessária
3. **Testes**: Testes que precisam de persistência real, mas não querem depender de um banco de dados
4. **Prototipagem**: Desenvolvimento rápido de protótipos com persistência real
5. **Aplicações Desktop**: Aplicações desktop que precisam armazenar dados localmente

## Limitações

- **Escalabilidade**: Não é adequado para grandes volumes de dados
- **Concorrência**: Pode ter problemas de concorrência em ambientes com muitos usuários simultâneos
- **Consultas Complexas**: Não suporta consultas complexas como um banco de dados relacional
- **Transações**: Não suporta transações atômicas
- **Backup**: Requer backup manual dos arquivos JSON

## Arquitetura

Este módulo segue os princípios de:

1. **Implementação de Interface**: Implementa as interfaces definidas no módulo `persistence-api`
2. **Auto-configuração**: Configura-se automaticamente quando presente no classpath
3. **Separação de Responsabilidades**: Separa entidades de repositórios
4. **Conversão de Dados**: Converte entre entidades internas e DTOs da API

## Integração com o Projeto

O módulo `json-client` se integra ao projeto através da interface `ClienteRepository` definida no módulo `persistence-api`. O módulo `service-client` utiliza esta interface sem conhecer os detalhes de implementação.

Esta arquitetura permite que o módulo `json-client` seja substituído por outras implementações (como `postgres-client` ou `memory-client`) sem modificar o código da aplicação.