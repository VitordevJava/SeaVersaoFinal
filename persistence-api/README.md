# Persistence API Module

## Descrição

O módulo `persistence-api` define as interfaces para acesso a dados no projeto SEA Tecnologia Hub. Este módulo não contém implementações concretas, apenas contratos que devem ser implementados pelos módulos de persistência específicos.

## Responsabilidades

Este módulo é responsável por:

1. **Definição de Interfaces**: Estabelecer contratos para operações de persistência
2. **Desacoplamento**: Permitir que a aplicação não dependa de uma implementação específica de persistência

## Componentes Principais

### Interfaces de Repositório

- **ClienteRepository**: Interface para operações CRUD de clientes

## Como Utilizar

Para utilizar este módulo em outro projeto, adicione a seguinte dependência ao seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>br.com.seatecnologia</groupId>
    <artifactId>persistence-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Implementando a Interface

Para criar uma nova implementação de persistência, você deve implementar a interface `ClienteRepository`:

```java
public class MinhaImplementacaoRepository implements ClienteRepository {
    
    @Override
    public ClienteDTO criar(ClienteDTO cliente) {
        // Implementação específica para criar um cliente
    }
    
    @Override
    public Optional<ClienteDTO> buscarPorId(Long id) {
        // Implementação específica para buscar um cliente por ID
    }
    
    @Override
    public List<ClienteDTO> listarTodos() {
        // Implementação específica para listar todos os clientes
    }
    
    @Override
    public ClienteDTO atualizar(Long id, ClienteDTO cliente) {
        // Implementação específica para atualizar um cliente
    }
    
    @Override
    public void deletar(Long id) {
        // Implementação específica para deletar um cliente
    }
    
    @Override
    public boolean existePorCpf(String cpf) {
        // Implementação específica para verificar se existe um cliente com o CPF informado
    }
}
```

## Implementações Existentes

Atualmente, existem três implementações desta API:

1. **postgres-client**: Implementação usando PostgreSQL
2. **json-client**: Implementação usando arquivos JSON
3. **memory-client**: Implementação usando memória RAM

## Arquitetura

Este módulo segue os princípios de:

1. **Inversão de Dependência**: A aplicação depende de abstrações, não de implementações concretas
2. **Segregação de Interfaces**: Interfaces pequenas e específicas para cada responsabilidade
3. **Desacoplamento**: Separação clara entre a definição do contrato e sua implementação

## Integração com o Projeto

O módulo `persistence-api` é utilizado por:

- **service-client**: Para acessar os dados através das interfaces definidas
- **Clientes de persistência**: Para implementar as interfaces definidas

Esta arquitetura permite que o módulo `service-client` não precise conhecer os detalhes de implementação da persistência, apenas a interface pública definida neste módulo.

## Benefícios

A separação entre interface e implementação traz diversos benefícios:

1. **Flexibilidade**: Facilidade para trocar implementações sem modificar o código da aplicação
2. **Testabilidade**: Facilidade para criar mocks para testes
3. **Manutenibilidade**: Cada implementação é isolada em seu próprio módulo
4. **Evolução**: Permite evoluir ou substituir a camada de persistência sem afetar as outras partes do sistema
5. **Desenvolvimento Paralelo**: Diferentes equipes podem trabalhar em diferentes implementações

## Criando uma Nova Implementação

Para criar uma nova implementação (por exemplo, para MongoDB):

1. Crie um novo módulo (ex: `mongodb-client`)
2. Adicione a dependência para `persistence-api`
3. Implemente a interface `ClienteRepository`
4. Configure a auto-configuração do Spring Boot para o novo módulo
5. Adicione o novo módulo como dependência no módulo `hub`

Consulte o README.md principal do projeto para instruções detalhadas sobre como adicionar novas implementações.