# Testes de API - Flávio Matias

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si.

## CDU 02 - Gerenciar Plantão

#### Objetivo

O objetivo dos testes de Gerenciamento de Plantão é validar as operações relacionadas ao ciclo de vida de um plantão dentro de uma escala, garantindo que o Líder de Escala possa criar, atualizar, excluir, listar e detalhar plantões conforme as regras de negócio estabelecidas. Os testes verificam a integridade dos dados, o tratamento de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Alternativo II - Excluir Plantão

#### Teste API 01 - Exclusão com Sucesso

##### Objetivo do Teste

Validar que o Líder de Escala consegue excluir um plantão existente em uma escala não publicada e com data futura.

##### Informações da Requisição

| Campo        | Valor                          |
| ------------ | ------------------------------ |
| Método HTTP  | DELETE                         |
| Endpoint     | `/api/v1/plantoes/{plantaoId}` |
| Autenticação | Bearer Token                   |
| Caso de Uso  | CDU 02 - Gerenciar Plantão     |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `204 No Content`

*Resposta sem corpo.*

##### Validações Realizadas

| Validação    | Descrição                  |
| ------------ | -------------------------- |
| Status Code  | Retorno HTTP 204           |
| Corpo Vazio  | Resposta sem conteúdo      |
| Performance  | Menor que 2000 ms          |

##### Script de Teste

```javascript
pm.test("Status 204 No Content", function () {
    pm.response.to.have.status(204);
});
pm.test("Corpo vazio", function () {
    pm.expect(pm.response.text()).to.be.empty;
});
pm.test("Tempo de resposta < 2000ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item        | Resultado |
| ----------- | --------- |
| Status Code | PASSED    |
| Corpo Vazio | PASSED    |
| Performance | PASSED    |

##### Evidências

<img width="1486" height="890" alt="sucesso" src="https://github.com/user-attachments/assets/2a5ae957-e102-4e84-bcd1-90158902c9e1" />

#### Teste API 02 - Excluir Plantão de Escala Publicada

##### Objetivo do Teste

Validar que o sistema rejeita a exclusão de um plantão pertencente a uma escala já publicada.

##### Informações da Requisição

| Campo        | Valor                       |
| ------------ | --------------------------- |
| Método HTTP  | DELETE                      |
| Endpoint     | `/api/v1/plantoes/175`      |
| Autenticação | Bearer Token                |
| Caso de Uso  | CDU 02 - Gerenciar Plantão  |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "não é possível excluir plantão de escala publicada",
  "path": "/api/v1/plantoes/175"
}
```

##### Validações Realizadas

| Validação        | Descrição                  |
| ---------------- | -------------------------- |
| Status Code      | Retorno HTTP 400           |
| Mensagem de Erro | Indica escala publicada    |

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

<img width="1486" height="890" alt="escala-publicada" src="https://github.com/user-attachments/assets/7de57d16-aea3-43be-bf03-932387eba4a4" />


#### Teste API 03 - Excluir Plantão com Data Retroativa

##### Objetivo do Teste

Validar que o sistema rejeita a exclusão de um plantão cuja data de entrada já tenha passado em relação ao horário atual.

##### Informações da Requisição

| Campo        | Valor                          |
| ------------ | ------------------------------ |
| Método HTTP  | DELETE                         |
| Endpoint     | `/api/v1/plantoes/{plantaoId}` |
| Autenticação | Bearer Token                   |
| Caso de Uso  | CDU 02 - Gerenciar Plantão     |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "status": 400,
  "message": "não é possível deletar plantões com data retroativa",
  "path": "/api/v1/plantoes/{id}"
}
```

##### Validações Realizadas

| Validação        | Descrição                  |
| ---------------- | -------------------------- |
| Status Code      | Retorno HTTP 400           |
| Mensagem de Erro | Indica data retroativa     |

##### Script de Teste

```javascript
pm.test("Status 400 Bad Request", function () {
    pm.response.to.have.status(400);
});
pm.test("Mensagem indica data retroativa", function () {
    pm.expect(pm.response.json().message.toLowerCase()).to.include("retroativa");
});
```

##### Resultado Obtido

| Item             | Resultado |
| ---------------- | --------- |
| Status Code      | FAILED    |
| Mensagem de Erro | FAILED    |

##### Evidências
<img width="1486" height="890" alt="data-retroativa" src="https://github.com/user-attachments/assets/7a6dac3a-6426-49ef-a209-13b5ea093c8b" />
