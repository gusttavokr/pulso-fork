# Testes de API - Ícaro

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si

## CDU 01 - Gerenciar Escala

#### Objetivo
O objetivo dos testes de Gerenciamento de Escalas é validar todas as operações relacionadas ao ciclo de vida de uma escala, garantindo que usuários autorizados possam criar, consultar, atualizar, listar e excluir escalas conforme as regras de negócio estabelecidas pelo sistema. Além disso, os testes visam verificar a integridade dos dados, o tratamento adequado de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Principal - Detalhar Escala

#### Teste API 01 - Detalhar Escala Existente

##### Objetivo do Teste

Validar que o sistema retorna corretamente os dados de uma escala existente.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas/1`       |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="p7w3kx"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

##### Validações Realizadas

| Validação       | Descrição                      |
| --------------- | ------------------------------ |
| Status Code     | Retorno HTTP 200               |
| Escala          | Escala encontrada              |
| Estrutura JSON  | Resposta válida                |
| Dados da Escala | Campos retornados corretamente |
| Performance     | Menor que 2000 ms              |

##### Script de Teste

```javascript id="v2x8qm"
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta contém os dados da escala", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData).to.have.property("id");
    pm.expect(jsonData.id).to.eql(1);
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
| Escala Encontrada     | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/4142e368-ac96-4530-8c57-af51a9fb926d" />

---

#### Teste API 02 - Tentar Detalhar Escala sem Informar o Identificador

##### Objetivo do Teste

Validar que o sistema impede a consulta dos dados de uma escala quando o identificador da escala não é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas`    |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="v7p2nx"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="x5r9jb"
{
    "timestamp": "2026-06-18T20:52:31.949684353",
    "status": 400,
    "message": "escalaId não foi informado",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação       | Descrição                   |
| --------------- | --------------------------- |
| Status Code     | Retorno HTTP 400            |
| Obrigatoriedade | escalaId deve ser informado |
| Estrutura JSON  | Resposta válida             |
| Performance     | Menor que 2000 ms           |

##### Script de Teste

```javascript id="m9w4qt"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de escalaId não informado", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("escalaid");
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
| Escala Encontrada     | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/88e9a651-1fd9-4e56-a744-d7b324fc9db0" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/ce2b72a2-588c-4093-b898-aa8a32492e2a" />

---

#### Teste API 03 - Tentar Detalhar Escala com Identificador Não Numérico

##### Objetivo do Teste

Validar que o sistema impede a consulta dos dados de uma escala quando o identificador informado não é um número válido.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas/"1"`    |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="r4m8pz"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="t8k5wy"
{
    "timestamp": "2026-06-18T20:52:31.949684353",
    "status": 400,
    "message": "escalaId informado não é um número",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação      | Descrição                  |
| -------------- | -------------------------- |
| Status Code    | Retorno HTTP 400           |
| Tipo de Dado   | escalaId deve ser numérico |
| Estrutura JSON | Resposta válida            |
| Performance    | Menor que 2000 ms          |

##### Script de Teste

```javascript id="u2v9ka"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de escalaId não numérico", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("não é um número");
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
| Escala Encontrada     | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/9caefd7c-0fa5-437f-804d-2b4e6eeffd23" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/388778e3-234b-4ccf-8579-54cbdc89f998" />

---

#### Teste API 04 - Tentar Detalhar Escala com Identificador Menor que 1

##### Objetivo do Teste

Validar que o sistema impede a consulta dos dados de uma escala quando o identificador informado é menor que 1.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas/0`    |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="n3q7tk"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="v9r4jy"
{
    "timestamp": "2026-06-18T20:52:31.949684353",
    "status": 400,
    "message": "escalaId informado é menor que 1",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação        | Descrição                        |
| ---------------- | -------------------------------- |
| Status Code      | Retorno HTTP 400                 |
| Regra de Negócio | escalaId deve ser maior que zero |
| Estrutura JSON   | Resposta válida                  |
| Performance      | Menor que 2000 ms                |

##### Script de Teste

```javascript id="w6m2qb"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de escalaId menor que 1", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("menor que 1");
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
| Escala Encontrada     | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/5fa1b643-1e96-427c-8d62-9671c02593d3" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/d2b41e3a-4511-4d84-b697-1154f5ab8052" />

---

#### Teste API 05 - Tentar Detalhar Escala Inexistente

##### Objetivo do Teste

Validar que o sistema impede a consulta dos dados de uma escala quando o identificador informado não corresponde a uma escala cadastrada.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | GET                       |
| Endpoint     | `/api/v1/escalas/321`    |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="t5n9mv"
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `404 Not Found`

```json id="u3p7wr"
{
    "timestamp": "2026-06-18T20:52:31.949684353",
    "status": 404,
    "message": "escala não encontrada com o escalaId informado",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação      | Descrição                               |
| -------------- | --------------------------------------- |
| Status Code    | Retorno HTTP 404                        |
| Existência     | Escala deve existir para ser consultada |
| Estrutura JSON | Resposta válida                         |
| Performance    | Menor que 2000 ms                       |

##### Script de Teste

```javascript id="v7m2ky"
pm.test("Status code é 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Erro de escala não encontrada", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("nenhuma escala encontrada");
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
| Escala Encontrada     | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/50f47494-a6ee-4f20-a609-92907413abed" />
