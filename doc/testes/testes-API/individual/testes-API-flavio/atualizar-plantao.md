# Testes de API - Flávio Matias

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si.

## CDU 02 - Gerenciar Plantão

#### Objetivo

O objetivo dos testes de Gerenciamento de Plantão é validar as operações relacionadas ao ciclo de vida de um plantão dentro de uma escala, garantindo que o Líder de Escala possa criar, atualizar, excluir, listar e detalhar plantões conforme as regras de negócio estabelecidas. Os testes verificam a integridade dos dados, o tratamento de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Alternativo I - Atualizar Plantão

#### Teste API 01 - Atualização com Sucesso

##### Objetivo do Teste

Validar que o Líder de Escala consegue atualizar os dados de um plantão existente em uma escala não publicada.

##### Informações da Requisição

| Campo        | Valor                          |
| ------------ | ------------------------------ |
| Método HTTP  | PUT                            |
| Endpoint     | `/api/v1/plantoes/{plantaoId}` |
| Autenticação | Bearer Token                   |
| Caso de Uso  | CDU 02 - Gerenciar Plantão     |

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
  "estimativaValor": 1500,
  "formaPagamento": "PIX",
  "estimativaPagamento": "2026-08-20",
  "comentario": "Plantão noturno com alta demanda.",
  "quantidadeProfissionais": 2,
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
  "id": 184,
  "especialidade": "CLINICA_GERAL",
  "comentario": "Plantão noturno com alta demanda.",
  "quantidadeProfissionais": 2,
  "idEscala": 1,
  "statusEscala": "RASCUNHO"
}
```

##### Validações Realizadas

| Validação         | Descrição                          |
| ----------------- | ---------------------------------- |
| Status Code       | Retorno HTTP 200                   |
| Dados Atualizados | Comentário refletido na resposta   |

##### Script de Teste

```javascript
pm.test("Status 200 OK", function () {
    pm.response.to.have.status(200);
});
pm.test("Comentário atualizado", function () {
    pm.expect(pm.response.json().comentario).to.eql("Plantão noturno com alta demanda.");
});
```

##### Resultado Obtido

| Item              | Resultado |
| ----------------- | --------- |
| Status Code       | PASSED    |
| Dados Atualizados | PASSED    |

##### Evidências

<img width="1486" height="890" alt="atualizar-sucesso" src="https://github.com/user-attachments/assets/49eeacfc-3c0f-4bf6-87b6-4f56c10a1296" />

#### Teste API 02 - Mover Plantão para Outra Escala

##### Objetivo do Teste

Validar que o sistema rejeita a atualização quando se tenta alterar o `escalaId` de um plantão existente, movendo-o para outra escala.

##### Informações da Requisição

| Campo        | Valor                          |
| ------------ | ------------------------------ |
| Método HTTP  | PUT                            |
| Endpoint     | `/api/v1/plantoes/{plantaoId}` |
| Autenticação | Bearer Token                   |
| Caso de Uso  | CDU 02 - Gerenciar Plantão     |

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
  "escalaId": 2
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "não é permitido mover o plantão para outra escala",
  "path": "/api/v1/plantoes/{id}"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 400                   |
| Mensagem de Erro | Indica mudança de escala bloqueada |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Mensagem indica mudança de escala bloqueada", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("mover");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | FAILED    |
| Mensagem de Erro | PASSED    |

##### Evidências

<img width="1486" height="890" alt="mover-escala" src="https://github.com/user-attachments/assets/90a704c9-c194-4aa9-a486-a0dcb111db1d" />


#### Teste API 03 - Reduzir Vagas Abaixo dos Profissionais Alocados

##### Objetivo do Teste

Validar que o sistema rejeita a atualização quando se tenta reduzir `quantidadeProfissionais` para um valor menor que o número de profissionais já alocados no plantão.

##### Informações da Requisição

| Campo        | Valor                          |
| ------------ | ------------------------------ |
| Método HTTP  | PUT                            |
| Endpoint     | `/api/v1/plantoes/{plantaoId}` |
| Autenticação | Bearer Token                   |
| Caso de Uso  | CDU 02 - Gerenciar Plantão     |

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
  "message": "vagas menores que profissionais alocados",
  "path": "/api/v1/plantoes/{id}"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 400                   |
| Mensagem de Erro | Indica vagas insuficientes         |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Mensagem indica vagas insuficientes", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("alocados");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | FAILED    |
| Mensagem de Erro | FAILED    |

##### Evidências

<img width="1486" height="890" alt="abaixo-profissionais-alocados" src="https://github.com/user-attachments/assets/1f9228de-8f5c-4c26-aa12-035dbed904c6" />


#### Teste API 04 - Atualizar Plantão de Escala Publicada

##### Objetivo do Teste

Validar que o sistema rejeita a atualização de um plantão pertencente a uma escala já publicada.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | PUT                         |
| Endpoint     | `/api/v1/plantoes/175`      |
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
  "escalaId": 1
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "não é possível alterar plantões de uma escala publicada",
  "path": "/api/v1/plantoes/175"
}
```

##### Validações Realizadas

| Validação        | Descrição                          |
| ---------------- | ---------------------------------- |
| Status Code      | Retorno HTTP 400                   |
| Mensagem de Erro | Indica escala publicada            |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Mensagem indica escala publicada", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("publicada");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | FAILED    |
| Mensagem de Erro | PASSED    |

##### Evidências
<img width="1486" height="890" alt="escala-publicada" src="https://github.com/user-attachments/assets/5ba633d9-e3fc-4459-9806-9a99bb1c7c87" />
