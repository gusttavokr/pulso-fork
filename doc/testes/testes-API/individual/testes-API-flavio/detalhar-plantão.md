# Testes de API - Flávio Matias

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si.

## CDU 02 - Gerenciar Plantão

#### Objetivo

O objetivo dos testes de Gerenciamento de Plantão é validar as operações relacionadas ao ciclo de vida de um plantão dentro de uma escala, garantindo que o Líder de Escala possa criar, atualizar, excluir, listar e detalhar plantões conforme as regras de negócio estabelecidas. Os testes verificam a integridade dos dados, o tratamento de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Alternativo III - Detalhar Plantão

#### Teste API 01 - Detalhamento com Sucesso

##### Objetivo do Teste

Validar que o sistema retorna os dados de um plantão existente junto de seus profissionais combinados.

##### Informações da Requisição

| Campo        | Valor                          |
| ------------ | ------------------------------ |
| Método HTTP  | GET                            |
| Endpoint     | `/api/v1/plantoes/{plantaoId}` |
| Autenticação | Bearer Token                   |
| Caso de Uso  | CDU 02 - Gerenciar Plantão     |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
  "id": 184,
  "especialidade": "CLINICA_GERAL",
  "horaEntrada": "2026-08-10T07:00:00",
  "horaSaida": "2026-08-10T19:00:00",
  "comentario": "Plantão de teste",
  "formaPagamento": "PIX",
  "estimativaValor": 1200,
  "idEscala": 1,
  "statusEscala": "RASCUNHO",
  "quantidadeProfissionais": 2,
  "profissionaisCombinados": []
}
```

##### Validações Realizadas

| Validação      | Descrição                              |
| -------------- | -------------------------------------- |
| Status Code    | Retorno HTTP 200                       |
| Estrutura JSON | Campos `id`, `idEscala` e alocações    |

##### Script de Teste

```javascript
pm.test("Status 200 OK", function () {
    pm.response.to.have.status(200);
});
pm.test("Estrutura do detalhe do plantão", function () {
    const j = pm.response.json();
    pm.expect(j.id).to.be.a("number");
    pm.expect(j).to.have.property("idEscala");
    pm.expect(j).to.have.property("profissionaisCombinados");
    pm.expect(j.profissionaisCombinados).to.be.an("array");
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| Estrutura da Resposta | PASSED    |

##### Evidências

<img width="1486" height="890" alt="sucesso" src="https://github.com/user-attachments/assets/dadae8a3-2049-471e-8a96-fb5e1d9aedf4" />

#### Teste API 02 - Detalhar Plantão Inexistente

##### Objetivo do Teste

Validar que o sistema rejeita a consulta quando o `plantaoId` informado não corresponde a um plantão cadastrado.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | GET                         |
| Endpoint     | `/api/v1/plantoes/99999`    |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `404 Not Found`

```json
{
  "status": 404,
  "message": "Plantão não encontrado",
  "path": "/api/v1/plantoes/99999"
}
```

##### Validações Realizadas

| Validação        | Descrição                       |
| ---------------- | ------------------------------- |
| Status Code      | Retorno HTTP 404                |
| Mensagem de Erro | Indica plantão não encontrado   |

##### Script de Teste

```javascript
pm.test("Status 404 Not Found", function () {
    pm.response.to.have.status(404);
});
pm.test("Mensagem de plantão não encontrado", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("não encontrado");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | PASSED    |
| Mensagem de Erro | PASSED    |

##### Evidências
<img width="1486" height="890" alt="inexistente" src="https://github.com/user-attachments/assets/c8919f4f-a2f0-43b7-b922-58f8d38fccd8" />
