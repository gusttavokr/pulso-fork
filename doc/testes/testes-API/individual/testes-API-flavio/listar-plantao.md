# Testes de API - Flávio Matias

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si.

## CDU 02 - Gerenciar Plantão

#### Objetivo

O objetivo dos testes de Gerenciamento de Plantão é validar as operações relacionadas ao ciclo de vida de um plantão dentro de uma escala, garantindo que o Líder de Escala possa criar, atualizar, excluir, listar e detalhar plantões conforme as regras de negócio estabelecidas. Os testes verificam a integridade dos dados, o tratamento de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Alternativo IV - Listar Plantões

#### Teste API 01 - Listar sem Filtros

##### Objetivo do Teste

Validar que o sistema retorna a lista de plantões do usuário logado quando nenhum filtro é informado.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | GET                         |
| Endpoint     | `/api/v1/plantoes`          |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json
[
  {
    "id": 184,
    "especialidade": "CLINICA_GERAL",
    "horaEntrada": "2026-08-10T07:00:00",
    "horaSaida": "2026-08-10T19:00:00",
    "idEscala": 1,
    "quantidadeProfissionais": 2
  }
]
```

##### Validações Realizadas

| Validação      | Descrição                          |
| -------------- | ---------------------------------- |
| Status Code    | Retorno HTTP 200                   |
| Estrutura JSON | Array ou objeto paginado           |

##### Script de Teste

```javascript
pm.test("Status 200 OK", function () {
    pm.response.to.have.status(200);
});
pm.test("Resposta é lista (array ou paginada)", function () {
    const j = pm.response.json();
    const ehLista = Array.isArray(j) || Array.isArray(j.content);
    pm.expect(ehLista, "esperava array ou objeto paginado com 'content'").to.be.true;
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| Estrutura da Resposta | FAILED    |

##### Evidências

<img width="1486" height="890" alt="sucesso" src="https://github.com/user-attachments/assets/3f2538f1-a9c6-483d-bba2-dcb6196406ba" />


#### Teste API 02 - Filtrar por Especialidade

##### Objetivo do Teste

Validar que o sistema retorna a lista de plantões filtrada por uma especialidade válida.

##### Informações da Requisição

| Campo        | Valor                                       |
| ------------ | ------------------------------------------- |
| Método HTTP  | GET                                         |
| Endpoint     | `/api/v1/plantoes?especialidade=CARDIOLOGIA`|
| Autenticação | Bearer Token                                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão                  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

##### Validações Realizadas

| Validação      | Descrição                          |
| -------------- | ---------------------------------- |
| Status Code    | Retorno HTTP 200                   |
| Estrutura JSON | Array ou objeto paginado           |

##### Script de Teste

```javascript
pm.test("Status 200 OK", function () {
    pm.response.to.have.status(200);
});
pm.test("Resposta é lista", function () {
    const j = pm.response.json();
    pm.expect(Array.isArray(j) || Array.isArray(j.content)).to.be.true;
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| Estrutura da Resposta | FAILED    |

##### Evidências

<img width="1486" height="890" alt="especialidade" src="https://github.com/user-attachments/assets/0ece2ab4-e5a5-4a83-90b2-f946539b4810" />


#### Teste API 03 - Filtrar por Faixa de Preço

##### Objetivo do Teste

Validar que o sistema retorna a lista de plantões filtrada por `precoMin` e `precoMax`.

##### Informações da Requisição

| Campo        | Valor                                          |
| ------------ | ---------------------------------------------- |
| Método HTTP  | GET                                            |
| Endpoint     | `/api/v1/plantoes?precoMin=100&precoMax=2000`  |
| Autenticação | Bearer Token                                   |
| Caso de Uso  | CDU 02 - Gerenciar Plantão                     |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

##### Validações Realizadas

| Validação      | Descrição                          |
| -------------- | ---------------------------------- |
| Status Code    | Retorno HTTP 200                   |
| Estrutura JSON | Array ou objeto paginado           |

##### Script de Teste

```javascript
pm.test("Status 200 OK", function () {
    pm.response.to.have.status(200);
});
pm.test("Resposta é lista", function () {
    const j = pm.response.json();
    pm.expect(Array.isArray(j) || Array.isArray(j.content)).to.be.true;
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| Estrutura da Resposta | FAILED    |

##### Evidências

<img width="1486" height="890" alt="preco" src="https://github.com/user-attachments/assets/afec916b-01a5-481e-a228-ad4f9223b20d" />


#### Teste API 04 - Especialidade Inválida

##### Objetivo do Teste

Validar que o sistema rejeita a listagem quando é informado um valor fora do enum de `especialidade`.

##### Informações da Requisição

| Campo        | Valor                                       |
| ------------ | ------------------------------------------- |
| Método HTTP  | GET                                         |
| Endpoint     | `/api/v1/plantoes?especialidade=INEXISTENTE`|
| Autenticação | Bearer Token                                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão                  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

##### Validações Realizadas

| Validação   | Descrição           |
| ----------- | ------------------- |
| Status Code | Retorno HTTP 400    |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
```

##### Resultado Obtido

| Item        | Resultado |
| ----------- | --------- |
| Status Code | FAILED    |

##### Evidências
<img width="1486" height="890" alt="especialidade-invalida" src="https://github.com/user-attachments/assets/0abc8d70-9af3-487e-ab58-ffc658ab3ac6" />
