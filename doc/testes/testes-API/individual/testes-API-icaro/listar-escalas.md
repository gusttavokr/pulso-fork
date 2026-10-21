# Testes de API - Ícaro

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si

## CDU 01 - Gerenciar Escala

#### Objetivo
O objetivo dos testes de Gerenciamento de Escalas é validar todas as operações relacionadas ao ciclo de vida de uma escala, garantindo que usuários autorizados possam criar, consultar, atualizar, listar e excluir escalas conforme as regras de negócio estabelecidas pelo sistema. Além disso, os testes visam verificar a integridade dos dados, o tratamento adequado de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Principal - Listar Escalas

#### Teste API 01 - Listar Escalas do Líder

##### Objetivo do Teste

Validar que o sistema retorna a lista de escalas quando o usuário informado é líder de escala e nenhum filtro adicional é aplicado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http
liderEscala=true
```

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

##### Validações Realizadas

| Validação        | Descrição                            |
| ---------------- | ------------------------------------ |
| Status Code      | Retorno HTTP 200                     |
| Lista Retornada  | Deve retornar uma coleção de escalas |
| Estrutura JSON   | Resposta válida                      |
| Tipo da Resposta | Deve ser um array                    |
| Performance      | Menor que 2000 ms                    |

##### Script de Teste

```javascript
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta é um array", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData).to.be.an("array");
});

pm.test("Lista contém escalas", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.length).to.be.greaterThan(0);
});

pm.test("Primeiro item possui id", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData[0].id).to.exist;
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Resposta Array        | PASSED    |
| Lista Escalas         | PASSED    |
| Possue Id             | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/81e3de8b-73cf-434f-8708-eee33edca783" />

---

#### Teste API 02 - Tentar Listar Escalas com Mês de Referência Inválido

##### Objetivo do Teste

Validar que o sistema impede a consulta de escalas quando o mês de referência informado não está no formato ISO esperado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="v4hmpq"
mesReferencia=""
liderEscala=true
```

##### Cabeçalhos da Requisição

```http id="u8kjqn"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="hy7nca"
{
    "timestamp": "2026-06-18T20:08:28.594902192",
    "status": 400,
    "message": "Mês de referência informado não está no formato ISO",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação      | Descrição                                          |
| -------------- | -------------------------------------------------- |
| Status Code    | Retorno HTTP 400                                   |
| Formato        | Mês de referência deve estar em formato ISO válido |
| Estrutura JSON | Resposta válida                                    |
| Performance    | Menor que 2000 ms                                  |

##### Script de Teste

```javascript id="3zz7nd"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de mês de referência inválido", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("formato iso");
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Tratamento Erro       | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/18155351-c6c9-447b-9101-922b5051406a" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/d20574de-c003-4e9b-a6c2-b84936abece6" />

---

#### Teste API 03 - Tentar Listar Escalas com Mês de Referência Não Textual

##### Objetivo do Teste

Validar que o sistema impede a consulta de escalas quando o mês de referência informado não é um texto.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="cw8r5a"
mesReferencia=true
liderEscala=true
```

##### Cabeçalhos da Requisição

```http id="e5x8pk"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="jv4gmr"
{
    "timestamp": "2026-06-18T20:08:28.594902192",
    "status": 400,
    "message": "Mês de referência informado não é um texto",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação      | Descrição                                       |
| -------------- | ----------------------------------------------- |
| Status Code    | Retorno HTTP 400                                |
| Tipo de Dado   | Mês de referência deve ser informado como texto |
| Estrutura JSON | Resposta válida                                 |
| Performance    | Menor que 2000 ms                               |

##### Script de Teste

```javascript id="f1nz6e"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de mês de referência não textual", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("não é um texto");
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Tratamento Erro       | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/542aeb1c-928d-4b9a-abe6-7269df8e0f2a" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/72ba9931-7018-4330-99f8-92d9896ec9b5" />

---

#### Teste API 04 - Tentar Listar Escalas com Especialidade Inválida

##### Objetivo do Teste

Validar que o sistema impede a consulta de escalas quando a especialidade informada não existe.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="o4z7dr"
especialidade=true
liderEscala=true
```

##### Cabeçalhos da Requisição

```http id="d7kq2w"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `404 Not Found`

```json id="p9jv6f"
{
    "timestamp": "2026-06-18T20:08:28.594902192",
    "status": 404,
    "message": "especialidade não encontrada",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação      | Descrição                            |
| -------------- | ------------------------------------ |
| Status Code    | Retorno HTTP 404                     |
| Existência     | Especialidade informada deve existir |
| Estrutura JSON | Resposta válida                      |
| Performance    | Menor que 2000 ms                    |

##### Script de Teste

```javascript id="m3xk8t"
pm.test("Status code é 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Erro de especialidade não encontrada", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("especialidade não encontrada");
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| Tratamento Erro       | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/c49c4090-061e-431d-96f8-2166d0a24429" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/fc9b3930-5f01-40a3-99f9-04cd67600220" />

---

#### Teste API 05 - Tentar Listar Escalas sem Informar Perfil de Consulta

##### Objetivo do Teste

Validar que o sistema impede a consulta de escalas quando nenhum dos parâmetros de contexto (`liderEscala` ou `instituicao`) é informado como verdadeiro.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="v8m4pk"
usuarioId=1
```

##### Cabeçalhos da Requisição

```http id="k2j7qy"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="r5x9na"
{
    "timestamp": "2026-06-18T20:08:28.594902192",
    "status": 400,
    "message": "Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação        | Descrição                                                                       |
| ---------------- | ------------------------------------------------------------------------------- |
| Status Code      | Retorno HTTP 400                                                                |
| Regra de Negócio | Pelo menos um dos parâmetros `liderEscala` ou `instituicao` deve ser verdadeiro |
| Estrutura JSON   | Resposta válida                                                                 |
| Performance      | Menor que 2000 ms                                                               |

##### Script de Teste

```javascript id="n6c8zf"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de perfil de consulta não informado", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData)
    ).to.include("liderEscala");
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Tratamento Erro       | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" height="658" alt="image" src="https://github.com/user-attachments/assets/9b8ac2d8-c851-48ea-a129-5418ab1be2b7" />

---

#### Teste API 06 - Tentar Listar Escalas com `liderEscala` Igual a False e `instituicao` Não Informada

##### Objetivo do Teste

Validar que o sistema impede a consulta de escalas quando o parâmetro `liderEscala` é informado como `false` e o parâmetro `instituicao` não é informado, violando a regra de que pelo menos um dos dois deve ser `true`.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="f9w3nk"
liderEscala=false
```

##### Cabeçalhos da Requisição

```http id="c7m2qx"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="x4p8jd"
{
    "timestamp": "2026-06-18T20:08:28.594902192",
    "status": 400,
    "message": "Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação        | Descrição                                                                       |
| ---------------- | ------------------------------------------------------------------------------- |
| Status Code      | Retorno HTTP 400                                                                |
| Regra de Negócio | Pelo menos um dos parâmetros `liderEscala` ou `instituicao` deve ser verdadeiro |
| Estrutura JSON   | Resposta válida                                                                 |
| Performance      | Menor que 2000 ms                                                               |

##### Script de Teste

```javascript id="p2r6vt"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de perfil de consulta inválido", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData)
    ).to.include("liderEscala");
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Tratamento Erro       | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/4952d738-1fb0-4337-856c-54b8412b6032" />

---

#### Teste API 07 - Tentar Listar Escalas com `instituicao` Igual a False e `liderEscala` Não Informado

##### Objetivo do Teste

Validar que o sistema impede a consulta de escalas quando o parâmetro `instituicao` é informado como `false` e o parâmetro `liderEscala` não é informado, violando a regra de que pelo menos um dos dois deve ser `true`.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="g8t5qy"
instituicao=false
```

##### Cabeçalhos da Requisição

```http id="m4v9kb"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="h2n7px"
{
    "timestamp": "2026-06-18T20:08:28.594902192",
    "status": 400,
    "message": "Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação        | Descrição                                                                       |
| ---------------- | ------------------------------------------------------------------------------- |
| Status Code      | Retorno HTTP 400                                                                |
| Regra de Negócio | Pelo menos um dos parâmetros `liderEscala` ou `instituicao` deve ser verdadeiro |
| Estrutura JSON   | Resposta válida                                                                 |
| Performance      | Menor que 2000 ms                                                               |

##### Script de Teste

```javascript id="z6k3mr"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de perfil de consulta inválido", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData)
    ).to.include("instituicao");
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Tratamento Erro       | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/2dd4cf94-241e-4045-8096-83ed56579121" />

---

### Fluxo Alternativo - Listar Escalas

#### Teste API 07 - Tentar Listar Escalas com `liderEscala` e `instituicao` Iguais a False

##### Objetivo do Teste

Validar que o sistema impede a consulta de escalas quando os parâmetros `liderEscala` e `instituicao` são informados como `false`, violando a regra de que pelo menos um deles deve ser `true`.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="r4n8ky"
liderEscala=false
instituicao=false
```

##### Cabeçalhos da Requisição

```http id="t7p2mv"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="w5c9jq"
{
    "timestamp": "2026-06-18T20:08:28.594902192",
    "status": 400,
    "message": "Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação        | Descrição                                                                       |
| ---------------- | ------------------------------------------------------------------------------- |
| Status Code      | Retorno HTTP 400                                                                |
| Regra de Negócio | Pelo menos um dos parâmetros `liderEscala` ou `instituicao` deve ser verdadeiro |
| Estrutura JSON   | Resposta válida                                                                 |
| Performance      | Menor que 2000 ms                                                               |

##### Script de Teste

```javascript id="y3m7pd"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de regra de negócio", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData)
    ).to.include("liderEscala");
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Tratamento Erro       | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/b571a706-a215-479c-b9a2-7f253a0aefc9" />

---

#### Teste API 08 - Listar Escalas como Instituição

##### Objetivo do Teste

Validar que o sistema retorna corretamente a lista de escalas quando a consulta é realizada pelo contexto de instituição.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="k8v2pm"
instituicao=true
```

##### Cabeçalhos da Requisição

```http id="x5n7qd"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

##### Validações Realizadas

| Validação           | Descrição                            |
| ------------------- | ------------------------------------ |
| Status Code         | Retorno HTTP 200                     |
| Retorno da Lista    | Deve retornar uma coleção de escalas |
| Estrutura JSON      | Resposta válida                      |
| Quantidade de Itens | Lista contém zero ou mais registros  |
| Performance         | Menor que 2000 ms                    |

##### Script de Teste

```javascript id="n7p4qw"
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta é um array", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData).to.be.an("array");
});

pm.test("Array retornado corretamente", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData.length).to.be.at.least(0);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| Resposta Array        | FAILED    |
| Lista Escalas         | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/616cfe7e-b66b-4ae0-a365-2583b837b0d9" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/edfc60bf-aa9d-435a-9c68-bc711921822b" />

---

#### Teste API 09 - Listar Escalas com `instituicao=true` e `liderEscala=false`

##### Objetivo do Teste

Validar que o sistema retorna corretamente a lista de escalas quando a consulta é realizada pelo contexto de instituição, mesmo com o parâmetro `liderEscala` explicitamente definido como `false`.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="p8m4kr"
liderEscala=false
instituicao=true
```

##### Cabeçalhos da Requisição

```http id="w2q9cx"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

##### Validações Realizadas

| Validação        | Descrição                                            |
| ---------------- | ---------------------------------------------------- |
| Status Code      | Retorno HTTP 200                                     |
| Retorno da Lista | Deve retornar uma coleção de escalas                 |
| Regra de Negócio | `instituicao=true` satisfaz os critérios da consulta |
| Estrutura JSON   | Resposta válida                                      |
| Performance      | Menor que 2000 ms                                    |

##### Script de Teste

```javascript id="j6x3fv"
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta é um array", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData).to.be.an("array");
});

pm.test("Lista retornada corretamente", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData.length).to.be.at.least(0);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| Resposta Array        | FAILED    |
| Lista Escalas         | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/c15f4ed7-1c05-4962-9611-edd0dfcd6d46" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/76734fe1-fc5e-4df6-8201-ac4a926bc210" />

---

#### Teste API 15 - Listar Escalas com `liderEscala=true` e `instituicao=false`

##### Objetivo do Teste

Validar que o sistema retorna corretamente a lista de escalas quando a consulta é realizada pelo contexto de líder de escala, mesmo com o parâmetro `instituicao` explicitamente definido como `false`.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Parâmetros da Requisição

```http id="f4m8qt"
liderEscala=true
instituicao=false
```

##### Cabeçalhos da Requisição

```http id="k7p2wv"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

##### Validações Realizadas

| Validação        | Descrição                                            |
| ---------------- | ---------------------------------------------------- |
| Status Code      | Retorno HTTP 200                                     |
| Retorno da Lista | Deve retornar uma coleção de escalas                 |
| Regra de Negócio | `liderEscala=true` satisfaz os critérios da consulta |
| Estrutura JSON   | Resposta válida                                      |
| Performance      | Menor que 2000 ms                                    |

##### Script de Teste

```javascript id="m5v8zc"
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta é um array", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData).to.be.an("array");
});

pm.test("Lista retornada corretamente", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData.length).to.be.at.least(0);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Resposta Array        | PASSED    |
| Lista Escalas         | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/80d85734-28ef-4d85-9b5c-ce3b9a1e9db1" />
