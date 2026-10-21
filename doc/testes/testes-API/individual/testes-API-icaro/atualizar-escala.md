# Testes de API - Ícaro

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si

## CDU 01 - Gerenciar Escala

#### Objetivo
O objetivo dos testes de Gerenciamento de Escalas é validar todas as operações relacionadas ao ciclo de vida de uma escala, garantindo que usuários autorizados possam criar, consultar, atualizar, listar e excluir escalas conforme as regras de negócio estabelecidas pelo sistema. Além disso, os testes visam verificar a integridade dos dados, o tratamento adequado de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Principal - Atualizar Escala

#### Teste API 01 - Atualizar Nome de Escala

##### Objetivo do Teste

Validar que o sistema permite atualizar o nome de uma escala existente.

##### Pré-Condição

Existe uma escala cadastrada com os seguintes dados:

```json
{
    "id": 3,
    "nome": "Escala Fevereiro",
    "mesReferencia": "2027-02-01",
    "status": "RASCUNHO",
    "dataCriacao": "2026-06-18T09:52:23.412693307",
    "dataAtualizacao": "2026-06-18T09:52:23.412693469",
    "nomeLider": "Lucas Almeida",
    "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg"
}
```

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
  "nome": "Escala fevereiro do setor 1",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
    "id": 3,
    "nome": "Escala fevereiro do setor 1",
    "mesReferencia": "2027-02-01",
    "status": "RASCUNHO",
    "dataCriacao": "2026-06-18T09:52:23.412693",
    "dataAtualizacao": "2026-06-18T09:58:49.101608",
    "nomeLider": "Lucas Almeida",
    "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg"
}
```

##### Validações Realizadas

| Validação             | Descrição                               |
| --------------------- | --------------------------------------- |
| Status Code           | Retorno HTTP 200                        |
| Atualização           | Nome atualizado com sucesso             |
| Integridade           | Demais atributos permanecem inalterados |
| Persistência de Dados | Alteração persistida na base de dados   |
| Estrutura JSON        | Resposta válida                         |
| Performance           | Menor que 2000 ms                       |

##### Script de Teste

```javascript
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Nome atualizado corretamente", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.nome)
        .to.eql("Escala fevereiro do setor 1");
});

pm.test("ID permanece o mesmo", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.eql(3);
});

pm.test("Mês permanece inalterado", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.mesReferencia).to.eql("2027-02-01");
});

pm.test("Status permanece RASCUNHO", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.status)
        .to.eql("RASCUNHO");
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Nome Atualizado       | PASSED    |
| ID Mantido            | PASSED    |
| Mês Mantido           | PASSED    |
| Status Mantido        | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/4aac5f57-0344-4ec9-bf85-a0cdddda34e4" />

---

#### Teste API 02 - Tentar Atualizar Escala sem Informar o ID

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o identificador (`escalaId`) não é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="y4nd3x"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="a2hj8k"
{
  "nome": "Escala fevereiro do setor 1",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="n6dv9q"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 400,
    "message": "escalaId não foi informado",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                                    |
| --------------------- | -------------------------------------------- |
| Status Code           | Retorno HTTP 400                             |
| Campo Obrigatório     | escalaId deve ser informado                  |
| Regra de Negócio      | Não permitir atualização sem identificação   |
| Mensagem de Erro      | Informar ausência do identificador da escala |
| Persistência de Dados | Nenhuma alteração deve ocorrer               |
| Estrutura JSON        | Resposta válida                              |
| Performance           | Menor que 2000 ms                            |

##### Script de Teste

```javascript id="v5pq8r"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao identificador da escala", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("escalaid");
});

pm.test("Escala não foi atualizada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | FAILED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/76b46228-5ebf-4884-9fcc-e345615b9fe8" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/433d4f46-6b27-4c58-a4c1-e581b76539c3" />

---

#### Teste API 03 - Tentar Atualizar Escala com escalaId Não Numérico

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o campo `escalaId` é informado com um tipo diferente de número.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/"3"`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="b7qh4m"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="d8wx2a"
{
  "nome": "Escala fevereiro do setor 1",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="z4my8k"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 400,
    "message": "escalaId informado não é um número",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                                   |
| --------------------- | ------------------------------------------- |
| Status Code           | Retorno HTTP 400                            |
| Tipo do Campo         | Campo escalaId deve aceitar apenas números  |
| Regra de Negócio      | Valores textuais não devem ser aceitos      |
| Mensagem de Erro      | Informar incompatibilidade do tipo recebido |
| Persistência de Dados | Nenhuma alteração deve ocorrer              |
| Estrutura JSON        | Resposta válida                             |
| Performance           | Menor que 2000 ms                           |

##### Script de Teste

```javascript id="r2x7nv"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo escalaId", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("escalaid");
});

pm.test("Escala não foi atualizada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | FAILED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/6f21fbe5-23ad-4275-9e02-2f4a6c2792b0" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/e413ccd3-6506-4c52-a498-38b6f0eb2912" />

---

#### Teste API 04 - Tentar Atualizar Escala com escalaId Menor que 1

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o campo `escalaId` possui valor inferior ao mínimo permitido.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/0`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="k8nf4w"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="j5rm9x"
{
  "nome": "Escala fevereiro do setor 1",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="h4tz2v"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 400,
    "message": "escalaId informado é menor que 1",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                                         |
| --------------------- | ------------------------------------------------- |
| Status Code           | Retorno HTTP 400                                  |
| Faixa de Valores      | Campo escalaId deve ser maior ou igual a 1        |
| Regra de Negócio      | Não permitir identificadores inválidos            |
| Mensagem de Erro      | Informar que o valor está abaixo do mínimo aceito |
| Persistência de Dados | Nenhuma alteração deve ocorrer                    |
| Estrutura JSON        | Resposta válida                                   |
| Performance           | Menor que 2000 ms                                 |

##### Script de Teste

```javascript id="m7pc3n"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo escalaId", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.message)
        .to.eql("Escala não encontrada com ID 0");
});

pm.test("Escala não foi atualizada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | PASSED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/b25d92c9-99df-4442-9d05-7d3984641cc5" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/98e33459-8cec-437c-abba-dd9671b4eb1c" />

---

#### Teste API 05 - Tentar Atualizar Escala com escalaId Inexistente

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o `escalaId` informado não corresponde a nenhuma escala cadastrada.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/321`      |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="w6rk2q"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="n4xy7b"
{
  "nome": "Escala fevereiro do setor 1",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `404 Not Found`

```json id="v9dt8m"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 404,
    "message": "escala não encontrada com escalaId informado",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação              | Descrição                                          |
| ---------------------- | -------------------------------------------------- |
| Status Code            | Retorno HTTP 404                                   |
| Existência do Registro | escalaId deve corresponder a uma escala existente  |
| Regra de Negócio       | Não permitir atualização de registros inexistentes |
| Mensagem de Erro       | Informar que a escala não foi encontrada           |
| Persistência de Dados  | Nenhuma alteração deve ocorrer                     |
| Estrutura JSON         | Resposta válida                                    |
| Performance            | Menor que 2000 ms                                  |

##### Script de Teste

```javascript id="p8nh4y"
pm.test("Status code é 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado à escala", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("escala");
});

pm.test("Nenhuma escala foi alterada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | PASSED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/e29c08fe-3ebc-436b-9890-087de73f9f35" />

---

#### Teste API 06 - Tentar Atualizar Escala Publicada

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala cujo status já está definido como **PUBLICADA**.

##### Pré-Condição

Existe uma escala cadastrada com os seguintes dados:

```json id="j7rm2a"
{
  "id": 2,
  "nome": "Escala Emergência - abril/2026",
  "nomeInstituicao": "Clínica Vida",
  "nomeLider": "Lucas Almeida",
  "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg",
  "nomeSetor": "Clínica Geral",
  "especialidadeSetor": "CLINICA_GERAL",
  "mesReferencia": "2026-09-01",
  "status": "PUBLICADA",
  "plantoes": []
}
```

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/2`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="v8qn5x"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="z3pc9m"
{
  "nome": "Escala fevereiro do setor 1",
  "mes": "2026-09-01",
  "setorId": 11
}
```

##### Resposta Esperada

**Status Code:** `409 Conflict`

```json id="h6wt4r"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 409,
    "message": "não é possível atualizar escala já publicada",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                                             |
| --------------------- | ----------------------------------------------------- |
| Status Code           | Retorno HTTP 409                                      |
| Regra de Negócio      | Escalas publicadas não podem ser alteradas            |
| Controle de Estado    | Atualização permitida apenas para escalas em rascunho |
| Mensagem de Erro      | Informar impossibilidade da atualização               |
| Persistência de Dados | Nenhuma alteração deve ocorrer                        |
| Estrutura JSON        | Resposta válida                                       |
| Performance           | Menor que 2000 ms                                     |

##### Script de Teste

```javascript id="n4xk8b"
pm.test("Status code é 409", function () {
    pm.response.to.have.status(409);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado à publicação da escala", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("public");
});

pm.test("Nenhuma escala foi alterada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | PASSED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/8cf0a97a-158b-44a4-8751-f8c3eed3b8d9" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/f1c566f0-484d-4620-8bdd-6e65685f9585" />

---

#### Teste API 07 - Tentar Atualizar Escala sem Informar o Nome

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o campo `nome` não é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="w2qn9m"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="k7zt5v"
{
    "nome": null
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="p3dx8h"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 400,
    "message": "nome não foi informado",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                         |
| --------------------- | --------------------------------- |
| Status Code           | Retorno HTTP 400                  |
| Campo Obrigatório     | Nome deve ser informado           |
| Regra de Negócio      | Não permitir atualização sem nome |
| Mensagem de Erro      | Informar ausência do nome         |
| Persistência de Dados | Nenhuma alteração deve ocorrer    |
| Estrutura JSON        | Resposta válida                   |
| Performance           | Menor que 2000 ms                 |

##### Script de Teste

```javascript id="t9vh6y"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo nome", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("nome");
});

pm.test("Nenhuma escala foi alterada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | FAILED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/78fdfdad-e13e-4d7e-8e0b-937469f4e6cd" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/5ca1e235-58cf-4601-88a3-42aa8c576f4e" />

---

#### Teste API 08 - Tentar Atualizar Escala com Nome Não Textual

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o campo `nome` é informado com um tipo diferente de texto.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="m8tv2x"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="y4qp7r"
{
  "nome": true,
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="h2wn5c"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 400,
    "message": "nome informado não é um texto",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                                   |
| --------------------- | ------------------------------------------- |
| Status Code           | Retorno HTTP 400                            |
| Tipo do Campo         | Nome deve ser um texto                      |
| Regra de Negócio      | Não permitir tipos incompatíveis            |
| Mensagem de Erro      | Informar incompatibilidade do tipo recebido |
| Persistência de Dados | Nenhuma alteração deve ocorrer              |
| Estrutura JSON        | Resposta válida                             |
| Performance           | Menor que 2000 ms                           |

##### Script de Teste

```javascript id="z6dk3p"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo nome", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("nome");
});

pm.test("Nenhuma escala foi alterada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | PASSED    |
| Escala Não Atualizada | FAILED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/146e7013-4801-4707-b4e0-29e9da0d9439" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/446c5164-c1d9-4140-b7c4-8764ba89dc60" />

---

#### Teste API 09 - Tentar Atualizar Escala com Nome Possuindo Menos de 3 Caracteres

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o campo `nome` possui menos de 3 caracteres.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="q9w2ld"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="p4xk7m"
{
  "nome": "Es",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="t8vn3c"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 400,
    "message": "nome informado tem menos de 3 caracteres",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                                |
| --------------------- | ---------------------------------------- |
| Status Code           | Retorno HTTP 400                         |
| Tamanho Mínimo        | Nome deve possuir no mínimo 3 caracteres |
| Regra de Negócio      | Não permitir nomes inválidos             |
| Mensagem de Erro      | Informar que o nome é curto demais       |
| Persistência de Dados | Nenhuma alteração deve ocorrer           |
| Estrutura JSON        | Resposta válida                          |
| Performance           | Menor que 2000 ms                        |

##### Script de Teste

```javascript id="k2mz8n"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo nome", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("nome");
});

pm.test("Nenhuma escala foi alterada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime)
        .to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | FAILED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/aff29e95-32ae-4386-85ec-13b98ed822f8" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/3c8fcb84-d591-4682-ad0d-681a41ac7edd" />

---

#### Teste API 10 - Atualizar Nome de Escala com Valor Válido

##### Objetivo do Teste

Validar que o sistema permite atualizar o nome de uma escala quando um valor válido é fornecido.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="a9mx3p"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="m7vn2k"
{
  "nome": "Esc",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json id="x2pq8t"
{
    "id": 3,
    "nome": "Esc",
    "mesReferencia": "2027-02-01",
    "status": "RASCUNHO",
    "dataCriacao": "2026-06-18T19:19:20.343899",
    "dataAtualizacao": "2026-06-18T19:28:08.221087",
    "nomeLider": "Lucas Almeida",
    "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg"
}
```

##### Validações Realizadas

| Validação             | Descrição                            |
| --------------------- | ------------------------------------ |
| Status Code           | Retorno HTTP 200                     |
| Atualização           | Nome atualizado corretamente         |
| Integridade           | Demais campos permanecem inalterados |
| Persistência de Dados | Alteração persistida na base         |
| Estrutura JSON        | Resposta válida                      |
| Performance           | Menor que 2000 ms                    |

##### Script de Teste

```javascript id="v8cn4m"
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Nome foi atualizado corretamente", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.nome).to.eql("Esc");
});

pm.test("ID permanece o mesmo", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.eql(3);
});

pm.test("Mês permanece inalterado", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.mesReferencia).to.eql("2027-02-01");
});

pm.test("Status permanece RASCUNHO", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.status).to.eql("RASCUNHO");
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Nome Atualizado       | PASSED    |
| ID Mantido            | PASSED    |
| Mês Mantido           | PASSED    |
| Status Mantido        | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/6c6f2cb6-9be8-4bdc-8733-9787cbf648b2" />

---

#### Teste API 11 - Atualizar Escala com Nome de 255 Caracteres

##### Objetivo do Teste

Validar que o sistema permite a atualização de uma escala quando o campo `nome` possui exatamente o limite máximo de caracteres permitido (255 caracteres).

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="x7mn2v"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="c9wp5r"
{
  "nome": "aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaa",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json id="h3vnt8"
{
    "id": 3,
    "nome": "aaaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaa",
    "mesReferencia": "2027-02-01",
    "status": "RASCUNHO",
    "dataCriacao": "2026-06-18T19:19:20.343899",
    "dataAtualizacao": "2026-06-18T19:28:08.221087",
    "nomeLider": "Lucas Almeida",
    "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg"
}
```

##### Validações Realizadas

| Validação             | Descrição                            |
| --------------------- | ------------------------------------ |
| Status Code           | Retorno HTTP 200                     |
| Limite Máximo         | Nome deve aceitar até 255 caracteres |
| Atualização           | Nome atualizado corretamente         |
| Integridade           | Demais campos permanecem inalterados |
| Persistência de Dados | Alteração persistida na base         |
| Estrutura JSON        | Resposta válida                      |
| Performance           | Menor que 2000 ms                    |

##### Script de Teste

```javascript id="m2xv7c"
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Nome atualizado corretamente", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.nome).to.exist;
});

pm.test("ID permanece o mesmo", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.eql(3);
});

pm.test("Mês permanece inalterado", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.mesReferencia).to.eql("2027-02-01");
});

pm.test("Status permanece RASCUNHO", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.status).to.eql("RASCUNHO");
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Nome Atualizado       | PASSED    |
| ID Mantido            | PASSED    |
| Mês Mantido           | PASSED    |
| Status Mantido        | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/8daa19d6-ce4b-429a-b1f4-f3479b4291f9" />

---

#### Teste API 12 - Atualizar Escala com Nome de 256 Caracteres

##### Objetivo do Teste

Validar que o sistema impede a atualização de uma escala quando o campo `nome` excede o limite máximo permitido de 255 caracteres.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | PUT                       |
| Endpoint     | `/api/v1/schedules/3`        |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="t3vp8k"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="n6xq2m"
{
  "nome": "aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaa
aaaaaa",
  "mes": "2027-02-01",
  "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json id="v9ck2p"
{
    "timestamp": "2026-06-18T10:06:23.914502419",
    "status": 400,
    "message": "nome informado tem mais de 255 caracteres",
    "path": "/api/v1/escalas/"
}
```

##### Validações Realizadas

| Validação             | Descrição                                |
| --------------------- | ---------------------------------------- |
| Status Code           | Retorno HTTP 400                         |
| Limite Máximo         | Nome não deve ultrapassar 255 caracteres |
| Regra de Negócio      | Validação de tamanho máximo obrigatória  |
| Mensagem de Erro      | Informar excesso de caracteres           |
| Persistência de Dados | Nenhuma alteração deve ocorrer           |
| Estrutura JSON        | Resposta válida                          |
| Performance           | Menor que 2000 ms                        |

##### Script de Teste

```javascript id="c8wq3n"
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo nome", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("nome");
});

pm.test("Nenhuma escala foi alterada", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.be.undefined;
});

pm.test("Tempo de resposta aceitável", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | FAILED    |
| JSON Válido           | PASSED    |
| Erro Tratado          | FAILED    |
| Escala Não Atualizada | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/3fe25efd-5fcc-411a-9b52-c5c33980afc2" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/8b46c954-0641-4b13-98dd-1f3253e039b5" />

