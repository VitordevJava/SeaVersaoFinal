# Hub Core Module

## Descrição

O módulo `hub-core` é o núcleo compartilhado do projeto SEA Tecnologia Hub. Ele contém componentes fundamentais que são utilizados por todos os outros módulos do projeto, garantindo consistência e reutilização de código.

## Responsabilidades

Este módulo é responsável por:

1. **DTOs (Data Transfer Objects)**: Objetos para transferência de dados entre camadas da aplicação
2. **Validadores**: Classes para validação de dados de entrada
3. **Utilitários**: Classes com funções auxiliares comuns

## Componentes Principais

### DTOs

- **ClienteDTO**: Representa os dados de um cliente
- **EnderecoDTO**: Representa os dados de um endereço
- **TelefoneDTO**: Representa os dados de um telefone
- **EmailDTO**: Representa os dados de um email
- **TipoTelefone**: Enum que representa os tipos de telefone (CELULAR, RESIDENCIAL, etc.)

### Validadores

- **CPFValidator**: Valida se um CPF é válido
- **NomeValidator**: Valida se um nome está no formato correto
- **CEPValidator**: Valida se um CEP está no formato correto
- **TelefoneValidator**: Valida se um telefone está no formato correto
- **EmailValidator**: Valida se um email está no formato correto

### Utilitários

- **ClientUtil**: Contém métodos para formatação e desformatação de CPF, CEP e telefone

## Como Utilizar

Para utilizar este módulo em outro projeto, adicione a seguinte dependência ao seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>br.com.seatecnologia</groupId>
    <artifactId>hub-core</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Exemplos de Uso

### Utilizando DTOs

```java
// Criando um novo cliente
ClienteDTO cliente = new ClienteDTO(
    null,  // ID será gerado pelo sistema
    "João da Silva",
    "123.456.789-00",
    new EnderecoDTO(
        "12345-678",
        "Rua Exemplo",
        "Bairro Teste",
        "Cidade Teste",
        "UF",
        "Complemento"
    ),
    List.of(new TelefoneDTO(TipoTelefone.CELULAR, "(11) 98765-4321")),
    List.of(new EmailDTO("joao@exemplo.com"))
);
```

### Utilizando Validadores

Os validadores são utilizados automaticamente através de anotações nos DTOs:

```java
@CPF
String cpf;

@Nome
String nome;

@CEP
String cep;

@Telefone
String telefone;

@Email
String email;
```

### Utilizando Utilitários

```java
// Formatando um CPF
String cpfFormatado = ClientUtil.maskCpf("12345678900");  // Retorna "123.456.789-00"

// Desformatando um CPF
String cpfDesformatado = ClientUtil.unmask("123.456.789-00");  // Retorna "12345678900"

// Formatando um CEP
String cepFormatado = ClientUtil.maskCep("12345678");  // Retorna "12345-678"

// Formatando um telefone
String telefoneFormatado = ClientUtil.maskTelefone("11987654321", "CELULAR");  // Retorna "(11) 98765-4321"
```

## Arquitetura

Este módulo segue os princípios de:

1. **Imutabilidade**: Os DTOs são implementados como records, garantindo imutabilidade
2. **Validação declarativa**: Utiliza anotações para validação de dados
3. **Separação de responsabilidades**: Cada classe tem uma única responsabilidade bem definida

## Integração com o Projeto

O módulo `hub-core` é a base para todos os outros módulos do projeto. Ele é utilizado por:

- **persistence-api**: Para definir os tipos de dados que serão persistidos
- **service-api**: Para definir os tipos de dados que serão manipulados pelos serviços
- **hub**: Para receber e enviar dados através das APIs REST
- **Clientes de persistência**: Para converter entre DTOs e entidades específicas de cada implementação

Por ser um módulo fundamental, qualquer alteração nele deve ser feita com cuidado, pois pode afetar todos os outros módulos do projeto.