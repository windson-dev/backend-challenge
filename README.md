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

### Clonar repositório[Jwt Validator.postman_collection.json](https://github.com/user-attachments/files/28797991/Jwt.Validator.postman_collection.json)


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

## Postman Collection

> | Collection |
> |-------------------|
> | [<img src="https://assets.getpostman.com/common-share/postman-logo.png" alt="Postman Collection" width="50px">]([Uploading Jwt Validator.postman_collection.json…]()
) |{
  "info": {
    "_postman_id": "eccf25b4-5de2-4ad4-8624-23cf6b2e22e8",
    "name": "Jwt Validator",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
    "_exporter_id": "55619135",
    "_collection_link": "https://go.postman.co/collection/55619135-eccf25b4-5de2-4ad4-8624-23cf6b2e22e8?source=collection_link"
  },
  "item": [
    {
      "name": "Case 1",
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\r\n    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg\"\r\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "https://backend-challenge-tp2c.onrender.com/api/v1/jwt/validate",
          "protocol": "https",
          "host": [
            "backend-challenge-tp2c",
            "onrender",
            "com"
          ],
          "path": [
            "api",
            "v1",
            "jwt",
            "validate"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Case 2",
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\r\n    \"token\": \"eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg\"\r\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "https://backend-challenge-tp2c.onrender.com/api/v1/jwt/validate",
          "protocol": "https",
          "host": [
            "backend-challenge-tp2c",
            "onrender",
            "com"
          ],
          "path": [
            "api",
            "v1",
            "jwt",
            "validate"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Case 3",
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\r\n    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs\"\r\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "https://backend-challenge-tp2c.onrender.com/api/v1/jwt/validate",
          "protocol": "https",
          "host": [
            "backend-challenge-tp2c",
            "onrender",
            "com"
          ],
          "path": [
            "api",
            "v1",
            "jwt",
            "validate"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Case 4",
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\r\n    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY\"\r\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "https://backend-challenge-tp2c.onrender.com/api/v1/jwt/validate",
          "protocol": "https",
          "host": [
            "backend-challenge-tp2c",
            "onrender",
            "com"
          ],
          "path": [
            "api",
            "v1",
            "jwt",
            "validate"
          ]
        }
      },
      "response": []
    },
    {
      "name": "health check",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "https://backend-challenge-tp2c.onrender.com/actuator/health",
          "protocol": "https",
          "host": [
            "backend-challenge-tp2c",
            "onrender",
            "com"
          ],
          "path": [
            "actuator",
            "health"
          ]
        }
      },
      "response": []
    }
  ]
}


## Autor

Windson Macedo

- GitHub: https://github.com/windson-dev
- LinkedIn: https://www.linkedin.com/in/windson-donizeti-macedo/
