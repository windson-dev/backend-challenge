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

<img width="1293" height="2851" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/baa82c34-60c1-44c5-a5c7-1dfa951b69c8" />


## Postman Collection

> | Collection |
> |-------------------|
> | [<img src="https://assets.getpostman.com/common-share/postman-logo.png" alt="Postman Collection" width="50px">](https://github.com/user-attachments/files/28798167/Jwt.Validator.postman_collection.json)

## Autor

Windson Macedo

- GitHub: https://github.com/windson-dev
- LinkedIn: https://www.linkedin.com/in/windson-donizeti-macedo/
