# Service API Module

## Descrição

O módulo `service-api` define as interfaces para os serviços de negócio no projeto SEA Tecnologia Hub. Este módulo não contém implementações concretas, apenas contratos que devem ser implementados pelos módulos de serviço específicos.

## Responsabilidades

Este módulo é responsável por:

1. **Definição de Interfaces**: Estabelecer contratos para operações de serviço
2. **Desacoplamento**: Permitir que a aplicação não dependa de uma implementação específica de serviço
3. **Abstração de Negócio**: Definir operações de alto nível que abstraem a lógica de negócio

## Componentes Principais

### Interfaces de Serviço

- **ClienteService**: Interface para operações de serviço relacionadas a clientes

## Como Utilizar

Para utilizar este módulo em outro projeto, adicione a seguinte dependência ao seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>br.com.seatecnologia</groupId>
    <artifactId>service-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Implementando a Interface

Para criar uma nova implementação de serviço, você deve implementar a interface `ClienteService`:

```java
public class MinhaImplementacaoService implements ClienteService {
    
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
}
```

## Implementações Existentes

Atualmente, existe uma implementação desta API:

1. **service-client**: Implementação padrão dos serviços de negócio

## Arquitetura

Este módulo segue os princípios de:

1. **Inversão de Dependência**: A aplicação depende de abstrações, não de implementações concretas
2. **Segregação de Interfaces**: Interfaces pequenas e específicas para cada responsabilidade
3. **Desacoplamento**: Separação clara entre a definição do contrato e sua implementação

## Integração com o Projeto

O módulo `service-api` é utilizado por:

- **hub**: Para acessar os serviços de negócio através das interfaces definidas
- **service-client**: Para implementar as interfaces definidas

Esta arquitetura permite que o módulo `hub` não precise conhecer os detalhes de implementação dos serviços, apenas a interface pública definida neste módulo.

## Benefícios

A separação entre interface e implementação traz diversos benefícios:

1. **Flexibilidade**: Facilidade para trocar implementações sem modificar o código da aplicação
2. **Testabilidade**: Facilidade para criar mocks para testes
3. **Manutenibilidade**: Cada implementação é isolada em seu próprio módulo
4. **Evolução**: Permite evoluir ou substituir a camada de serviço sem afetar as outras partes do sistema
5. **Desenvolvimento Paralelo**: Diferentes equipes podem trabalhar em diferentes implementações

## Diferença entre Service API e Persistence API

Enquanto a `persistence-api` define interfaces para acesso a dados (operações CRUD básicas), a `service-api` define interfaces para operações de negócio de mais alto nível. A camada de serviço pode:

1. **Orquestrar** múltiplas operações de persistência
2. **Aplicar** regras de negócio complexas
3. **Validar** dados de entrada
4. **Transformar** dados entre diferentes formatos
5. **Integrar** com serviços externos

## Criando uma Nova Implementação

Para criar uma nova implementação de serviço:

1. Crie um novo módulo (ex: `alternative-service-client`)
2. Adicione a dependência para `service-api`
3. Implemente a interface `ClienteService`
4. Configure a auto-configuração do Spring Boot para o novo módulo
5. Adicione o novo módulo como dependência no módulo `hub`

Esta abordagem permite "plugar" e "desplugar" diferentes implementações de serviço, assim como é feito com as implementações de persistência.