# Testes de API - Ícaro

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si

## CDU 01 - Gerenciar Escala

#### Objetivo
O objetivo dos testes de Gerenciamento de Escalas é validar todas as operações relacionadas ao ciclo de vida de uma escala, garantindo que usuários autorizados possam criar, consultar, atualizar, listar e excluir escalas conforme as regras de negócio estabelecidas pelo sistema. Além disso, os testes visam verificar a integridade dos dados, o tratamento adequado de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Principal - Excluir Escala

#### Teste API 01 - Excluir Escala Existente

##### Objetivo do Teste

Validar que uma escala pode ser excluída com sucesso quando um identificador válido é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | DELETE                    |
| Endpoint     | `/api/v1/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
    "mensagem": "escala excluída com sucesso"
}
```

##### Validações Realizadas

| Validação      | Descrição                   |
| -------------- | --------------------------- |
| Status Code    | Retorno HTTP 200            |
| Exclusão       | Escala removida com sucesso |
| Estrutura JSON | Resposta válida             |
| Performance    | Menor que 2000 ms           |

##### Script de Teste

```javascript
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Escala excluída com sucesso", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("sucesso");
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
| Escala Excluida       | FAILED    |
| JSON Válido           | FAILED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/da4d2862-5dec-46aa-9816-9ac1d73b2225" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/c23c22ee-d613-4806-9aa8-f3aad2cc9ce5" />

---

#### Teste API 02 - Tentar Excluir Escala sem Informar o Identificador

##### Objetivo do Teste

Validar que o sistema impede a exclusão de uma escala quando o identificador não é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | DELETE                    |
| Endpoint     | `/api/VI/schedules/`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-18T19:43:08.711545171",
    "status": 400,
    "message": "escalaId não foi informado",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação      | Descrição                   |
| -------------- | --------------------------- |
| Status Code    | Retorno HTTP 400            |
| Identificador  | EscalaId deve ser informado |
| Estrutura JSON | Resposta válida             |
| Performance    | Menor que 2000 ms           |

##### Script de Teste

```javascript
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
| Tratamento de Erro    | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/b18a1924-3a9e-4b29-b20d-4c487bd2adc8" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/e7375ce9-a5ba-4247-b763-bdebf79567ed" />

---

#### Teste API 03 - Tentar Excluir Escala com Identificador Não Numérico

##### Objetivo do Teste

Validar que o sistema impede a exclusão de uma escala quando o identificador informado não é um número válido.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | DELETE                    |
| Endpoint     | `/api/v1/schedules/"3"`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-18T19:43:08.711545171",
    "status": 400,
    "message": "escalaId informado não é um número",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação      | Descrição                  |
| -------------- | -------------------------- |
| Status Code    | Retorno HTTP 400           |
| Identificador  | EscalaId deve ser numérico |
| Estrutura JSON | Resposta válida            |
| Performance    | Menor que 2000 ms          |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de escalaId inválido", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("número");
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
| Tratamento de Erro    | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/0fa62840-4b44-4b5c-a190-a7e96a3936e2" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/3b8ac7a8-9b82-4f73-a4b1-5ac4e8cc2ad2" />

---

#### Teste API 04 - Tentar Excluir Escala com Identificador Menor que 1

##### Objetivo do Teste

Validar que o sistema impede a exclusão de uma escala quando o identificador informado é menor que 1.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | DELETE                    |
| Endpoint     | `/api/v1/schedules/0`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-18T19:43:08.711545171",
    "status": 400,
    "message": "escalaId informado é menor que 1",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação      | Descrição                        |
| -------------- | -------------------------------- |
| Status Code    | Retorno HTTP 400                 |
| Identificador  | EscalaId deve ser maior que zero |
| Estrutura JSON | Resposta válida                  |
| Performance    | Menor que 2000 ms                |

##### Script de Teste

```javascript
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
| Tratamento de Erro    | FAILED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/e5cb0f4b-4a2d-4c0d-87ed-ab67f11783dc" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/78199828-cf2b-4fd7-8a47-66d3cebc6359" />

---

#### Teste API 05 - Tentar Excluir Escala Inexistente

##### Objetivo do Teste

Validar que o sistema impede a exclusão de uma escala quando o identificador informado não corresponde a uma escala cadastrada.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | DELETE                    |
| Endpoint     | `/api/v1/schedules/321`      |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `404 Not Found`

```json
{
    "timestamp": "2026-06-18T19:43:08.711545171",
    "status": 404,
    "message": "escala não encontrada com o escalaId informado",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação      | Descrição                             |
| -------------- | ------------------------------------- |
| Status Code    | Retorno HTTP 404                      |
| Existência     | Escala deve existir para ser excluída |
| Estrutura JSON | Resposta válida                       |
| Performance    | Menor que 2000 ms                     |

##### Script de Teste

```javascript
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
| Tratamento de Erro    | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/f9f3089a-da3d-452e-a131-22f27456856a" />

---

#### Teste API 06 - Tentar Excluir Escala Já Publicada

##### Objetivo do Teste

Validar que o sistema impede a exclusão de uma escala que já foi publicada.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | DELETE                    |
| Endpoint     | `/api/v1/schedules/2`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-18T19:43:08.711545171",
    "status": 400,
    "message": "não é possivel excluir escala já publicada",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação        | Descrição                                  |
| ---------------- | ------------------------------------------ |
| Status Code      | Retorno HTTP 400                           |
| Regra de Negócio | Escalas publicadas não podem ser excluídas |
| Estrutura JSON   | Resposta válida                            |
| Performance      | Menor que 2000 ms                          |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Erro de escala publicada", function () {
    const jsonData = pm.response.json();

    pm.expect(
        JSON.stringify(jsonData).toLowerCase()
    ).to.include("publicada");
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
| Tratamento de Erro    | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/82d28319-4774-43f6-bf58-ed55692d8c9d" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/75967c97-26f9-445f-bb43-fecd23c8e997" />

