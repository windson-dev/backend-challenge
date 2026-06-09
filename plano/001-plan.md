# Plano de Implementação - Desafio Técnico Itaú JWT Validator

## Objetivo

Desenvolver uma API REST utilizando Java 21 e Spring Boot que receba um JWT e retorne um boolean indicando se o token atende às regras de negócio definidas no desafio.

---

# Requisitos Funcionais

Validar:

1. JWT possui estrutura válida (3 partes separadas por ".")
2. JWT pode ser decodificado corretamente
3. Deve conter exatamente 3 claims
4. Claims obrigatórias:

   * Name
   * Role
   * Seed
5. Não pode existir nenhuma claim adicional
6. Name:

   * obrigatório
   * máximo de 256 caracteres
   * não pode conter números
7. Role:

   * obrigatório
   * valores permitidos:

     * Admin
     * Member
     * External
8. Seed:

   * obrigatório
   * deve ser numérico
   * deve ser número primo

Qualquer falha deve resultar em:

```json
{
  "valid": false
}
```

---

# Stack Tecnológica

* Java 21
* Spring Boot 3.x
* Maven
* Jackson
* JUnit 5
* Mockito
* Spring Boot Test
* MockMvc
* Spring Actuator
* Lombok (opcional)

Não utilizar banco de dados.

---

# Arquitetura

Estrutura simples em camadas.

```text
controller
service
dto
util
exception
```

Não utilizar Strategy Pattern, Factory Pattern ou Chain of Responsibility.

Objetivo é manter simplicidade e legibilidade.

---

# Estrutura de Pacotes

```text
com.itau.jwtvalidator

├── controller
│   └── JwtValidationController
│
├── service
│   ├── JwtParserService
│   └── JwtValidationService
│
├── dto
│   ├── JwtRequest
│   ├── JwtPayload
│   └── ValidationResponse
│
├── util
│   └── PrimeNumberUtil
│
├── exception
│   └── InvalidJwtException
│
└── config
```

---

# Endpoint

## POST

```http
/api/v1/jwt/validate
```

Request:

```json
{
  "token": "jwt_string"
}
```

Response:

```json
{
  "valid": true
}
```

ou

```json
{
  "valid": false
}
```

Sempre retornar HTTP 200.

Não lançar erro para regras inválidas.

---

# Responsabilidades

## JwtValidationController

Responsável apenas por:

* receber request
* chamar serviços
* retornar resposta

Não deve conter regra de negócio.

---

## JwtParserService

Responsável por:

* validar estrutura do JWT
* separar header, payload e signature
* decodificar payload Base64URL
* converter JSON para objeto Java
* montar JwtPayload

Não deve validar regras de negócio.

---

## JwtValidationService

Responsável por validar todas as regras.

Método principal:

```java
boolean validate(JwtPayload payload)
```

Implementar métodos privados:

```java
hasOnlyRequiredClaims()
isValidName()
isValidRole()
isValidSeed()
```

Método principal deve combinar todas as validações.

---

## PrimeNumberUtil

Responsável apenas por verificar se um número é primo.

Implementar algoritmo eficiente utilizando:

```java
sqrt(n)
```

para evitar complexidade desnecessária.

---

# DTOs

## JwtRequest

Campos:

```java
String token
```

---

## JwtPayload

Campos:

```java
String name
String role
String seed
Map<String, Object> claims
```

---

## ValidationResponse

Campos:

```java
boolean valid
```

---

# Regras de Validação

## Claims

Validar:

```java
claims.size() == 3
```

E garantir existência de:

```java
Name
Role
Seed
```

Nenhuma claim extra permitida.

---

## Name

Validar:

* não nulo
* não vazio
* tamanho <= 256
* não contém dígitos

Regex sugerida:

```java
.*\\d.*
```

---

## Role

Valores permitidos:

```java
Admin
Member
External
```

Utilizar Set para consulta.

---

## Seed

Validar:

* não nulo
* numérico
* número primo

---

# Tratamento de Erros

Criar InvalidJwtException.

Cenários:

* token nulo
* token vazio
* menos de 3 partes
* mais de 3 partes
* payload inválido
* Base64 inválido
* JSON inválido

Esses casos devem resultar em:

```json
{
  "valid": false
}
```

Não retornar stacktrace para o cliente.

---

# Logging

Adicionar logs utilizando SLF4J.

Logs mínimos:

Recebimento da requisição:

```text
Starting JWT validation
```

Após parse:

```text
JWT payload successfully extracted
```

Resultado final:

```text
JWT validation result: true/false
```

Não logar o JWT completo por segurança.

---

# Observabilidade

Adicionar:

```xml
spring-boot-starter-actuator
```

Configurar:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,metrics
```

Garantir endpoints:

```text
/actuator/health
/actuator/metrics
```

---

# Testes Unitários

Criar testes para:

## PrimeNumberUtil

Casos:

```text
2
3
5
7
11
13
17
```

retornam true.

Casos:

```text
0
1
4
6
8
9
10
12
```

retornam false.

---

## JwtValidationService

Testar:

* payload válido
* claims extras
* role inválida
* nome com números
* nome acima de 256 caracteres
* seed não primo
* seed não numérico

---

## JwtParserService

Testar:

* JWT válido
* Base64 inválido
* JSON inválido
* token mal formatado

---

# Testes de Integração

Utilizar MockMvc.

Cobrir todos os exemplos fornecidos pelo desafio.

Caso 1:

```text
true
```

Caso 2:

```text
false
```

Caso 3:

```text
false
```

Caso 4:

```text
false
```

Adicionar cenários extras:

* Role inválida
* Seed não primo
* Nome maior que 256 caracteres

---

# README

Documentar:

## Como executar

```bash
mvn spring-boot:run
```

## Como testar

```bash
mvn test
```

## Endpoint

Exemplo de request e response.

## Decisões arquiteturais

Explicar:

* arquitetura simples por conta do escopo reduzido
* separação Parser x Validator
* foco em legibilidade e testabilidade
* evitar abstrações prematuras

## Limitação do desafio

Explicar que não é possível validar a assinatura criptográfica do JWT sem a chave secreta utilizada para assinatura.
