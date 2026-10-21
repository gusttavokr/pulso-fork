# Testes de API - Flávio Matias

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si.

## CDU 02 - Gerenciar Plantão

#### Objetivo

O objetivo dos testes de Gerenciamento de Plantão é validar as operações relacionadas ao ciclo de vida de um plantão dentro de uma escala, garantindo que o Líder de Escala possa criar, atualizar, excluir, listar e detalhar plantões conforme as regras de negócio estabelecidas. Os testes verificam a integridade dos dados, o tratamento de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Principal - Criar Plantão

#### Teste API 01 - Criação com Sucesso

##### Objetivo do Teste

Validar que o Líder de Escala consegue criar um plantão com dados válidos dentro de uma escala existente e não publicada.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-08-10T07:00:00",
  "horaSaida": "2026-08-10T19:00:00",
  "estimativaValor": 1200,
  "formaPagamento": "PIX",
  "estimativaPagamento": "2026-08-15",
  "comentario": "Plantão de teste",
  "quantidadeProfissionais": 2,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `201 Created`

```json
{
  "id": 184,
  "especialidade": "CLINICA_GERAL",
  "horaEntrada": "2026-08-10T07:00:00",
  "horaSaida": "2026-08-10T19:00:00",
  "formaPagamento": "PIX",
  "estimativaValor": 1200,
  "idEscala": 1,
  "statusEscala": "RASCUNHO",
  "quantidadeProfissionais": 2,
  "estimativaPagamento": "2026-08-15"
}
```

##### Validações Realizadas

| Validação         | Descrição                          |
| ----------------- | ---------------------------------- |
| Status Code       | Retorno HTTP 201                   |
| Content-Type      | Retorno JSON                       |
| Estrutura JSON    | Campos do plantão retornados       |
| Performance       | Menor que 2000 ms                  |

##### Script de Teste

```javascript
pm.test("Status 201 Created", function () {
    pm.response.to.have.status(201);
});
pm.test("Content-Type JSON", function () {
    pm.response.to.have.header("Content-Type", "application/json");
});
pm.test("Tempo de resposta < 2000ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
pm.test("Dados do plantão criado", function () {
    const j = pm.response.json();
    pm.expect(j.id, "id ausente").to.be.a("number");
    pm.expect(j.quantidadeProfissionais).to.eql(2);
    pm.expect(j.formaPagamento).to.eql("PIX");
    pm.collectionVariables.set("plantaoId", j.id);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Content-Type          | PASSED    |
| Estrutura da Resposta | PASSED    |
| Performance           | PASSED    |

##### Evidências
<img width="1486" height="890" alt="criarsucesso" src="https://github.com/user-attachments/assets/6a1f9d1b-e027-40ea-8528-60363d73a23c" />


#### Teste API 02 - Criação sem Informar o escalaId

##### Objetivo do Teste

Validar que o sistema rejeita a criação de um plantão quando o campo obrigatório `escalaId` não é informado.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-08-10T07:00:00",
  "horaSaida": "2026-08-10T19:00:00",
  "quantidadeProfissionais": 2,
  "escalaId": null
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "timestamp": "2026-06-21T19:44:50.677",
  "status": 400,
  "message": "escalaId não foi informado",
  "path": "/api/v1/plantoes"
}
```

##### Validações Realizadas

| Validação                 | Descrição                       |
| ------------------------- | ------------------------------- |
| Status Code               | Retorno HTTP 400                |
| Corpo de Erro Padronizado | Presença de `message` e `path`  |
| Mensagem de Erro          | Indica `escalaId` ausente       |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Corpo de erro padronizado", function () {
    const j = pm.response.json();
    pm.expect(j).to.have.property("message");
    pm.expect(j).to.have.property("path");
});
pm.test("Mensagem indica escalaId ausente", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("escalaid");
});
```

##### Resultado Obtido

| Item                      | Resultado |
| ------------------------- | --------- |
| Status Code               | FAILED    |
| Corpo de Erro Padronizado | PASSED    |
| Mensagem de Erro          | FAILED    |

##### Evidências

<img width="1486" height="890" alt="idescalaausente" src="https://github.com/user-attachments/assets/56a95eda-3f62-4a37-b937-e1a4286f968b" />


#### Teste API 03 - Antecedência Mínima Não Respeitada

##### Objetivo do Teste

Validar que o sistema rejeita a criação de um plantão cuja `horaEntrada` esteja a menos de 7 dias da data atual.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-06-22T07:00:00",
  "horaSaida": "2026-06-22T19:00:00",
  "quantidadeProfissionais": 2,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "antecedência mínima de 7 dias não respeitada",
  "path": "/api/v1/plantoes"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 400                   |
| Mensagem de Erro | Indica antecedência mínima         |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Mensagem indica antecedência", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("antecedência");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | FAILED    |
| Mensagem de Erro | PASSED    |

##### Evidências
<img width="1486" height="890" alt="Antecedênciamínimanãorespeitada" src="https://github.com/user-attachments/assets/01828df5-789f-43bc-898f-76312b3b3d1d" />


#### Teste API 04 - Quantidade de Profissionais Inválida

##### Objetivo do Teste

Validar que o sistema rejeita a criação de um plantão quando `quantidadeProfissionais` é igual a 0.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-08-10T07:00:00",
  "horaSaida": "2026-08-10T19:00:00",
  "quantidadeProfissionais": 0,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "quantidade de profissionais deve ser maior que 0",
  "path": "/api/v1/plantoes"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 400                   |
| Mensagem de Erro | Indica quantidade inválida         |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Mensagem indica quantidade inválida", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("profissionais");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | FAILED    |
| Mensagem de Erro | FAILED    |

##### Evidências

<img width="1486" height="890" alt="Quantidadedeprofissionaisinválida" src="https://github.com/user-attachments/assets/86cfcc1b-ebf2-4026-8987-5c4fc1b77386" />


#### Teste API 05 - Limite Inferior do Mês de Referência

##### Objetivo do Teste

Validar que o sistema aceita a criação de um plantão no primeiro instante do mês de referência da escala.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-08-01T00:00:00",
  "horaSaida": "2026-08-01T12:00:00",
  "quantidadeProfissionais": 1,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `201 Created`

##### Validações Realizadas

| Validação      | Descrição                     |
| -------------- | ----------------------------- |
| Status Code    | Retorno HTTP 201              |
| Estrutura JSON | Plantão criado com `id`       |

##### Script de Teste

```javascript
pm.test("Status 201 Created", function () {
    pm.response.to.have.status(201);
});
pm.test("Plantão criado", function () {
    pm.expect(pm.response.json().id).to.be.a("number");
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Estrutura da Resposta | PASSED    |

##### Evidências
<img width="1486" height="890" alt="Limitenferiordomêsdereferência(sucesso)" src="https://github.com/user-attachments/assets/cecc6100-fbbe-44fc-a4c6-f104dfd49660" />


#### Teste API 06 - Limite Superior do Mês de Referência

##### Objetivo do Teste

Validar que o sistema aceita a criação de um plantão no último instante do mês de referência da escala.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-08-31T23:59:00",
  "horaSaida": "2026-09-01T07:00:00",
  "quantidadeProfissionais": 1,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `201 Created`

##### Validações Realizadas

| Validação   | Descrição        |
| ----------- | ---------------- |
| Status Code | Retorno HTTP 201 |

##### Script de Teste

```javascript
pm.test("Status 201 Created", function () {
    pm.response.to.have.status(201);
});
```

##### Resultado Obtido

| Item        | Resultado |
| ----------- | --------- |
| Status Code | PASSED    |

##### Evidências

<img width="1486" height="890" alt="Limitesuperiordomêsdereferência(sucesso)" src="https://github.com/user-attachments/assets/d4eac2bd-7841-4b37-a72a-42a637dc258b" />

#### Teste API 07 - Data Fora do Mês de Referência

##### Objetivo do Teste

Validar que o sistema rejeita a criação de um plantão integralmente fora do mês de referência da escala.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-09-01T08:00:00",
  "horaSaida": "2026-09-01T20:00:00",
  "quantidadeProfissionais": 1,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "data fora do mês de referência da escala",
  "path": "/api/v1/plantoes"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 400                   |
| Mensagem de Erro | Indica mês de referência           |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Mensagem indica mês de referência", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("mês de referência");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | PASSED    |
| Mensagem de Erro | PASSED    |

##### Evidências
<img width="1486" height="890" alt="mesreferenciaerrado" src="https://github.com/user-attachments/assets/8c2cf7db-45d1-4bf4-ba14-5e263c95e445" />


#### Teste API 08 - Sobreposição de Horários

##### Objetivo do Teste

Validar que o sistema rejeita a criação de um plantão sobreposto a outro já existente no mesmo setor e dia.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-08-10T07:00:00",
  "horaSaida": "2026-08-10T19:00:00",
  "quantidadeProfissionais": 1,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "não pode haver sobreposição de horários",
  "path": "/api/v1/plantoes"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 400 ou 409            |
| Mensagem de Erro | Indica sobreposição                |

##### Script de Teste

```javascript
pm.test("Status de conflito (400 ou 409)", function () {
    pm.expect(pm.response.code).to.be.oneOf([400, 409]);
});
pm.test("Mensagem indica sobreposição", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("sobreposição");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | FAILED    |
| Mensagem de Erro | FAILED    |

##### Evidências

<img width="1486" height="890" alt="sobreposiçãohorario" src="https://github.com/user-attachments/assets/c0f9b86b-bb6b-4cfc-8f11-8cce08856c38" />


#### Teste API 09 - Escala Inexistente

##### Objetivo do Teste

Validar que o sistema rejeita a criação de um plantão quando o `escalaId` informado não corresponde a uma escala cadastrada.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | POST                        |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "horaEntrada": "2026-08-10T07:00:00",
  "horaSaida": "2026-08-10T19:00:00",
  "quantidadeProfissionais": 1,
  "escalaId": 999999
}
```

##### Resposta Esperada

**Status Code:** `404 Not Found`

```json
{
  "status": 404,
  "message": "escala não encontrada com o escalaId informado",
  "path": "/api/v1/plantoes"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 404                   |
| Mensagem de Erro | Indica escala não encontrada       |

##### Script de Teste

```javascript
pm.test("Status 404 Not Found", function () {
    pm.response.to.have.status(404);
});
pm.test("Mensagem de escala não encontrada", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("não encontrada");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | PASSED    |
| Mensagem de Erro | PASSED    |

##### Evidências
<img width="1486" height="890" alt="escalainexistente" src="https://github.com/user-attachments/assets/eeaee247-5052-479f-9378-380e53f8f0b8" />
