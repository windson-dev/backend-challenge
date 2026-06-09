# JWT Validator API

API REST desenvolvida em Java 21 e Spring Boot para validação de JWTs com regras de negócio específicas.

## Tecnologias

- Java 21
- Spring Boot 3.5.0
- Spring Boot Actuator
- JUnit 5
- Mockito
- Maven

## Regras de Validação

Um token é considerado válido quando atende a todas as regras abaixo:

| Regra | Descrição |
|---------|---------|
| Estrutura JWT | Deve possuir exatamente 3 partes separadas por "." |
| Claims permitidas | Apenas `Name`, `Role` e `Seed` |
| Name | Obrigatória, máximo de 256 caracteres e sem números |
| Role | Obrigatória e deve ser `Admin`, `Member` ou `External` |
| Seed | Obrigatória e deve ser um número primo |

## Executando o Projeto

### Clonar repositório

```bash
git clone https://github.com/windson-dev/backend-challenge.git
```

### Entrar na pasta

```bash
cd backend-challenge
```

### Executar aplicação

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

## Endpoint

### Validar JWT

```http
POST /api/v1/jwt/validate
```

### Request

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJOYW1lIjoiSm9obiIsIlJvbGUiOiJBZG1pbiIsIlNlZWQiOjE3fQ.signature"
}
```

### Response

#### Token válido

```json
true
```

#### Token inválido

```json
false
```

A API sempre retorna HTTP 200, conforme especificação do desafio.

---

## Observabilidade

Endpoints disponibilizados pelo Spring Boot Actuator:

### Health Check

```http
GET /actuator/health
```

---

## Arquitetura

O projeto foi desenvolvido com foco em simplicidade, legibilidade e facilidade de manutenção.

### Responsabilidades

#### JwtParserService

Responsável por:

- Decodificar o token
- Extrair as claims
- Converter os dados para DTOs

#### JwtValidationService

Responsável por:

- Validar estrutura do JWT
- Validar claims obrigatórias
- Validar Name
- Validar Role
- Validar Seed

---

## Decisões Técnicas

### Separação Parser x Validation

Foi adotada a separação entre parsing e validação para manter cada componente com uma única responsabilidade.

### Sem padrões desnecessários

Não foram utilizados Strategy, Factory ou Chain of Responsibility por se tratar de um conjunto pequeno e estável de regras de negócio.

Essa abordagem reduz complexidade e facilita a leitura do código.

### Testabilidade

A aplicação possui:

- Testes unitários
- Testes de integração
- Cobertura dos principais cenários de validação

---

## Limitações

Não é possível validar a assinatura criptográfica do JWT sem acesso à chave utilizada na geração do token.

Por esse motivo, a aplicação valida:

- Estrutura do token
- Claims presentes
- Regras de negócio definidas pelo desafio

---

## Autor

Windson Macedo

- GitHub: https://github.com/windson-dev
- LinkedIn: https://www.linkedin.com/in/windson-dev
