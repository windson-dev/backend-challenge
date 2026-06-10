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

---

## Notas de Arquitetura

A solução segue uma abordagem em camadas:

- Camada de serviço responsável pela orquestração do fluxo
- Parser separado da validação para isolar responsabilidades
- Regras de negócio concentradas no serviço de validação

Todas as exceções são tratadas e convertidas em respostas booleanas na camada de serviço, garantindo que a API sempre retorne `true` ou `false` como resultado final.

---

## Decisões Técnicas

### Separação de responsabilidades (Parser x Validation x Service)

A solução foi estruturada em três camadas principais:

- **JwtService**: responsável por orquestrar o fluxo de validação e tratar exceções, garantindo que a API sempre retorne um resultado booleano (`true` ou `false`).
- **JwtParserService**: responsável exclusivamente por interpretar o token JWT, incluindo validação estrutural, decodificação Base64URL e conversão dos claims.
- **JwtValidationService**: responsável por aplicar as regras de negócio sobre os claims extraídos.

Essa separação garante baixo acoplamento e facilita a manutenção do fluxo.

---

### Tratamento de erros simplificado

Todas as exceções relacionadas à leitura ou parsing do token (`InvalidJwtException`, erros de decode ou JSON inválido) são capturadas na camada de serviço (`JwtService`).

Isso garante que:

- A API nunca expõe stack trace ou erro técnico
- O retorno da API é sempre um valor booleano
- O controle de erro fica centralizado na orquestração

---

### Regras de validação explícitas e determinísticas

As validações são realizadas de forma direta, sem frameworks adicionais de regras, seguindo um modelo explícito:

- Validação de estrutura do JWT (3 partes)
- Validação de claims obrigatórios (`Name`, `Role`, `Seed`)
- Validação de regras de negócio:
  - `Name`: não pode conter números e deve ter no máximo 256 caracteres
  - `Role`: deve ser `Admin`, `Member` ou `External`
  - `Seed`: deve ser um número válido e primo

---

### Adoção de interfaces (SOLID - DIP / ISP)

Foi adotada a segregação por interfaces em todos os serviços principais, por meio da criação de contratos explícitos para cada responsabilidade.

---

### Testabilidade

A aplicação foi construída visando fácil testabilidade:

- **Testes unitários** para serviços individuais (parser e validação)
- **Testes de integração** para validar o fluxo completo da API
- Cobertura dos principais cenários:
  - token válido
  - token inválido
  - erro de parsing
  - falhas de validação de claims
 
---

## Executando o Projeto

### Clonar repositório


```bash
git clone https://github.com/windson-dev/backend-challenge.git
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
  "token": "token"
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

## Fluxo de validação JWT

<img width="1293" height="2851" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/baa82c34-60c1-44c5-a5c7-1dfa951b69c8" />


## Postman Collection

> | Collection |
> |-------------------|
> | [<img src="https://assets.getpostman.com/common-share/postman-logo.png" alt="Postman Collection" width="50px">](https://github.com/user-attachments/files/28798167/Jwt.Validator.postman_collection.json)

## Autor

Windson Macedo

- GitHub: https://github.com/windson-dev
- LinkedIn: https://www.linkedin.com/in/windson-donizeti-macedo/
