# Testes de API - Ícaro

## Introdução

Este documento reúne os testes de API elaborados para validar os principais fluxos da aplicação Pulso, desenvolvido no contexto do PDS. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada teste inclui informações detalhadas sobre o endpoint, o corpo da requisição e o teste em si

## CDU 01 - Gerenciar Escala

#### Objetivo
O objetivo dos testes de Gerenciamento de Escalas é validar todas as operações relacionadas ao ciclo de vida de uma escala, garantindo que usuários autorizados possam criar, consultar, atualizar, listar e excluir escalas conforme as regras de negócio estabelecidas pelo sistema. Além disso, os testes visam verificar a integridade dos dados, o tratamento adequado de entradas inválidas, a aplicação das restrições de negócio e a consistência das respostas retornadas pela API.

### Fluxo Principal - Criar Escala

#### Teste API 01 - Criar Escala com Dados Válidos

##### Objetivo do Teste

Validar que uma nova escala pode ser criada com sucesso quando todos os campos obrigatórios são informados corretamente.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Escala Fevereiro",
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `201 Created`

```json
{
    "id": 3,
    "nome": "Escala Fevereiro",
    "mesReferencia": "2027-02-01",
    "status": "RASCUNHO",
    "dataCriacao": "2026-06-17T20:36:32.958995483",
    "dataAtualizacao": "2026-06-17T20:36:32.958995753",
    "nomeLider": "Lucas Almeida",
    "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg"
}
```

##### Validações Realizadas

| Validação           | Descrição                  |
| ------------------- | -------------------------- |
| Status Code         | Retorno HTTP 201           |
| Campos Obrigatórios | Verificar existência do id |
| Estrutura JSON      | Resposta válida            |
| Performance         | Menor que 2000 ms          |

##### Script de Teste

```javascript
pm.test("Status code é 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Resposta contém ID", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData.id).to.exist;
});

pm.test("Escala criada com sucesso", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.exist;
    pm.expect(jsonData.nome).to.eql("Escala Fevereiro");
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
| Estrutura da Resposta | PASSED    |
| Sucesso               | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="Captura de tela 2026-06-17 204502" src="https://github.com/user-attachments/assets/521773cd-0138-4072-aaad-bc1e2d806c74" />

---

#### Teste API 02 - Criar Escala Sem Nome

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo obrigatório `nome` não é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": null,
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Nome não foi informado.",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação               | Descrição                                      |
| ----------------------- | ---------------------------------------------- |
| Status Code             | Retorno HTTP 400                               |
| Validação de Negócio    | Nome é obrigatório                             |
| Persistência de Dados   | Escala não deve ser criada                     |
| Mensagem de Erro        | Retornar mensagem informativa ao usuário       |
| Estrutura JSON          | Resposta válida                                |
| Performance             | Menor que 2000 ms                              |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Mensagem de erro retornada", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("nome");
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | FAILED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="Captura de tela 2026-06-17 205039" src="https://github.com/user-attachments/assets/4736c59c-5871-4a0b-b758-2c7350ceee52" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/47439181-18f6-4e4c-9ca1-90322ac8cff2" />

---

#### Teste API 03 - Criar Escala com Nome Não Textual

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `nome` é informado com um tipo diferente de texto.

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json
{
    "nome": true,
    "mes": "2027-02-01",
    "setorId": 1
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Nome informado não é um texto.",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                      |
| --------------------- | ---------------------------------------------- |
| Status Code           | Retorno HTTP 400                               |
| Tipo do Campo         | Campo nome deve aceitar apenas texto           |
| Mensagem de Erro      | Informar que o valor recebido possui tipo inválido |
| Persistência de Dados | Escala não deve ser criada                     |
| Estrutura JSON        | Resposta válida                                |
| Performance           | Menor que 2000 ms                              |

##### Script de Teste

```javascript
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

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | PASSED    |
| Escala Não Criada     | FAILED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="Captura de tela 2026-06-17 210437" src="https://github.com/user-attachments/assets/ffee67ae-edaa-445a-a320-be40e5ec891d" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/cc98bfb3-1b67-4b22-9241-67d041606902" />

---

#### Teste API 04 - Criar Escala com Nome Menor que 3 Caracteres

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `nome` possui menos de 3 caracteres.


##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Es",
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Nome informado tem menos de 3 caracteres.",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                         |
| --------------------- | ------------------------------------------------- |
| Status Code           | Retorno HTTP 400                                  |
| Tamanho Mínimo        | Campo nome deve possuir no mínimo 3 caracteres    |
| Mensagem de Erro      | Informar que o nome possui tamanho inválido       |
| Persistência de Dados | Escala não deve ser criada                        |
| Estrutura JSON        | Resposta válida                                   |
| Performance           | Menor que 2000 ms                                 |

##### Script de Teste

```javascript
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

pm.test("Escala não foi criada", function () {
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
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Tratamento Erro       | PASSED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/0b728a3e-7424-49b0-bcd0-8688a02c5b5a" />

---

#### Teste API 05 - Criar Escala com Nome Possuindo Exatamente 3 Caracteres

##### Objetivo do Teste

Validar que uma escala pode ser criada com sucesso quando o campo `nome` possui exatamente a quantidade mínima de caracteres permitida pela regra de negócio.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `201 Created`

```json
{
    "id": 3,
    "nome": "Esc",
    "mesReferencia": "2027-02-01",
    "status": "RASCUNHO",
    "dataCriacao": "2026-06-17T21:14:23.097298689",
    "dataAtualizacao": "2026-06-17T21:14:23.09729906",
    "nomeLider": "Lucas Almeida",
    "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg"
}
```

##### Validações Realizadas

| Validação          | Descrição                                         |
| ------------------ | ------------------------------------------------- |
| Status Code        | Retorno HTTP 201                                  |
| Regra de Tamanho   | Nome com exatamente 3 caracteres deve ser aceito  |
| Persistência       | Escala deve ser criada no sistema                 |
| Status Inicial     | Status da escala deve ser RASCUNHO                |
| Estrutura JSON     | Resposta válida                                   |
| Performance        | Menor que 2000 ms                                 |

##### Script de Teste

```javascript
pm.test("Status code é 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Resposta contém ID", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.exist;
});

pm.test("Nome foi persistido corretamente", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.nome).to.eql("Esc");
});

pm.test("Status inicial é RASCUNHO", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.status).to.eql("RASCUNHO");
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
| Contém ID             | PASSED    |
| Nome Persistido       | PASSED    |
| Status RASCUNHO       | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/864b3e37-14f3-461d-bede-2de3a81295e5" />

---

#### Teste API 06 - Criar Escala com Nome Possuindo Exatamente 255 Caracteres

##### Objetivo do Teste

Validar que uma escala pode ser criada com sucesso quando o campo `nome` possui exatamente o número máximo de caracteres permitido pela regra de negócio.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "mes": "2027-02-01",
    "setorId": 1
}
```

> O valor enviado para o campo `nome` deve possuir exatamente 255 caracteres.

##### Resposta Esperada

**Status Code:** `201 Created`

```json
{
    "id": 3,
    "nome": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaa",
    "mesReferencia": "2027-02-01",
    "status": "RASCUNHO",
    "dataCriacao": "2026-06-17T21:26:48.793523047",
    "dataAtualizacao": "2026-06-17T21:26:48.793523418",
    "nomeLider": "Lucas Almeida",
    "fotoLider": "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg"
}
```

##### Validações Realizadas

| Validação             | Descrição                                          |
| --------------------- | -------------------------------------------------- |
| Status Code           | Retorno HTTP 201                                   |
| Regra de Tamanho      | Nome com exatamente 255 caracteres deve ser aceito |
| Persistência          | Escala deve ser criada no sistema                  |
| Integridade dos Dados | Nome completo deve ser armazenado sem truncamento  |
| Valores Informados    | Nome, mês e setor devem corresponder aos enviados  |
| Valores Padrão        | Campo copia deve assumir false                     |
| Status Inicial        | Status da escala deve ser RASCUNHO                 |
| Estrutura JSON        | Resposta válida                                    |
| Performance           | Menor que 2000 ms                                  |

##### Script de Teste

```javascript
pm.test("Status code é 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Resposta contém ID", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.id).to.exist;
});

pm.test("Nome foi persistido corretamente", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.nome).to.eql(pm.request.body ? jsonData.nome : jsonData.nome);
});

pm.test("Status inicial é RASCUNHO", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.status).to.eql("RASCUNHO");
});

pm.test("Nome possui 255 caracteres", function () {
    const jsonData = pm.response.json();

    pm.expect(jsonData.nome.length).to.eql(255);
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
| Contém ID             | PASSED    |
| Nome Persistido       | PASSED    |
| Status RASCUNHO       | PASSED    |
| Nome 255 Caracteres   | PASSED    |
| JSON Válido           | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/69a70aa9-13b8-41d4-b7e9-2a4f35d90e30" />

---

#### Teste API 07 - Tentar Criar Escala com Nome Possuindo Mais de 255 Caracteres

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `nome` possui mais de 255 caracteres.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Nome informado tem mais de 255 caracteres.",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                        |
| --------------------- | ------------------------------------------------ |
| Status Code           | Retorno HTTP 400                                 |
| Tamanho Máximo        | Campo nome deve aceitar no máximo 255 caracteres |
| Regra de Negócio      | Nome com 256 caracteres deve ser rejeitado       |
| Mensagem de Erro      | Informar que o limite máximo foi excedido        |
| Persistência de Dados | Escala não deve ser criada                       |
| Estrutura JSON        | Resposta válida                                  |
| Performance           | Menor que 2000 ms                                |

##### Script de Teste

```javascript
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

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | FAILED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/9dffd364-97bd-4a32-917f-e5c3dac8a7a4" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/66efd3e2-ee55-4894-8c24-af98f007b487" />

---

#### Teste API 08 - Tentar Criar Escala sem Informar o Mês de Referência

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo obrigatório `mes` não é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": null,
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Mês de referência não informado",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                  |
| --------------------- | ------------------------------------------ |
| Status Code           | Retorno HTTP 400                           |
| Campo Obrigatório     | Campo mes deve ser informado               |
| Regra de Negócio      | Não permitir criação sem mês de referência |
| Mensagem de Erro      | Informar ausência do mês de referência     |
| Persistência de Dados | Escala não deve ser criada                 |
| Estrutura JSON        | Resposta válida                            |
| Performance           | Menor que 2000 ms                          |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo mês", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.satisfy(text =>
            text.includes("mês") ||
            text.includes("referência")
        );
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | FAILED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/2c1eacea-e816-4c73-8a23-a939a7dd2115" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/3802af1b-deb9-4bb9-a5c5-46e123b831f4" />

---

#### Teste API 09 - Tentar Criar Escala com Mês de Referência Vazio

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `mes` é informado em formato inválido, não respeitando o padrão ISO esperado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": "",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Mês de referência informado não está no formato ISO",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                         |
| --------------------- | ------------------------------------------------- |
| Status Code           | Retorno HTTP 400                                  |
| Formato da Data       | Campo mes deve seguir o padrão ISO esperado       |
| Regra de Negócio      | Datas em formato inválido devem ser rejeitadas    |
| Mensagem de Erro      | Informar problema no formato do mês de referência |
| Persistência de Dados | Escala não deve ser criada                        |
| Estrutura JSON        | Resposta válida                                   |
| Performance           | Menor que 2000 ms                                 |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo mês", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.satisfy(text =>
            text.includes("mês") ||
            text.includes("referência")
        );
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | FAILED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/ef9959ed-368f-457c-b44e-18f4da0632bc" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/13571028-7ad3-4d6b-a8d6-abf390e029f1" />

---

#### Teste API 10 - Tentar Criar Escala com Mês de Referência Não Textual

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `mes` é informado com um tipo diferente de texto.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": true,
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Mês de referência informado não é um texto",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                      |
| --------------------- | ---------------------------------------------- |
| Status Code           | Retorno HTTP 400                               |
| Tipo do Campo         | Campo mes deve aceitar apenas valores textuais |
| Regra de Negócio      | Valores booleanos não devem ser aceitos        |
| Mensagem de Erro      | Informar incompatibilidade do tipo recebido    |
| Persistência de Dados | Escala não deve ser criada                     |
| Estrutura JSON        | Resposta válida                                |
| Performance           | Menor que 2000 ms                              |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo mês", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.satisfy(text =>
            text.includes("mês") ||
            text.includes("referência")
        );
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | FAILED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/21a136bf-0f0b-426f-af16-426728a2cbe5" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/b8730849-c01c-497f-81df-3f209d15f71c" />

---

#### Teste API 11 - Tentar Criar Escala com Mês de Referência Anterior à Data Atual

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o mês de referência informado está no passado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": "2025-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Mês de referência informado está no passado",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                        |
| --------------------- | ------------------------------------------------ |
| Status Code           | Retorno HTTP 400                                 |
| Regra Temporal        | Mês de referência não pode estar no passado      |
| Regra de Negócio      | Apenas meses atuais ou futuros devem ser aceitos |
| Mensagem de Erro      | Informar que a data informada é inválida         |
| Persistência de Dados | Escala não deve ser criada                       |
| Estrutura JSON        | Resposta válida                                  |
| Performance           | Menor que 2000 ms                                |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo mês", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.satisfy(text =>
            text.includes("mês") ||
            text.includes("referência")
        );
});

pm.test("Escala não foi criada", function () {
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
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Tratamento Erro       | PASSED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/1bc16341-da43-4694-b2f4-977149f546f8" />

---

#### Teste API 12 - Tentar Criar Escala com Campo Cópia Não Booleano

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `copia` é informado com um tipo diferente de booleano.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": "2027-02-01",
    "copia": "true",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "Copia informada não é um booleano",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                         |
| --------------------- | ------------------------------------------------- |
| Status Code           | Retorno HTTP 400                                  |
| Tipo do Campo         | Campo copia deve aceitar apenas valores booleanos |
| Regra de Negócio      | Valores textuais não devem ser aceitos            |
| Mensagem de Erro      | Informar incompatibilidade do tipo recebido       |
| Persistência de Dados | Escala não deve ser criada                        |
| Estrutura JSON        | Resposta válida                                   |
| Performance           | Menor que 2000 ms                                 |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo copia", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("copia");
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | PASSED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/ee3765d5-e16a-4a4e-891e-64f73884ad30" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/56caf09e-1eb1-4d09-ba63-71f0a0a86f89" />

---

#### Teste API 13 - Tentar Criar Escala sem Informar o Setor

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo obrigatório `setorId` não é informado.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": "2027-02-01",
    "setorId": null
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "setorId não foi informado",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                          |
| --------------------- | -------------------------------------------------- |
| Status Code           | Retorno HTTP 400                                   |
| Campo Obrigatório     | Campo setorId deve ser informado                   |
| Regra de Negócio      | Não permitir criação de escala sem setor associado |
| Mensagem de Erro      | Informar ausência do identificador do setor        |
| Persistência de Dados | Escala não deve ser criada                         |
| Estrutura JSON        | Resposta válida                                    |
| Performance           | Menor que 2000 ms                                  |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo setorId", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("setor");
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | FAILED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/f24fdf29-b548-47a6-b94d-932a505621bf" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/c6bdad17-e494-4531-b519-6a9066e69ef2" />

---

#### Teste API 14 - Tentar Criar Escala com setorId Não Numérico

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `setorId` é informado com um tipo diferente de número.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": "2027-02-01",
    "setorId": "10"
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "setorId informando não é um número",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                   |
| --------------------- | ------------------------------------------- |
| Status Code           | Retorno HTTP 400                            |
| Tipo do Campo         | Campo setorId deve aceitar apenas números   |
| Regra de Negócio      | Valores textuais não devem ser aceitos      |
| Mensagem de Erro      | Informar incompatibilidade do tipo recebido |
| Persistência de Dados | Escala não deve ser criada                  |
| Estrutura JSON        | Resposta válida                             |
| Performance           | Menor que 2000 ms                           |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo setorId", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("setor");
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | FAILED    |
| Escala Não Criada     | FAILED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/782067c2-97b4-41f1-a218-d7262fe660a3" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/f0385dca-c6c1-43b6-83e5-85a61f8e39bb" />

---

#### Teste API 15 - Tentar Criar Escala com setorId Menor que 1

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o campo `setorId` possui valor inferior ao mínimo permitido.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
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
    "nome": "Esc",
    "mes": "2027-02-01",
    "setorId": 0
}
```

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 400,
    "message": "setorId informando é menor que 1",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                         |
| --------------------- | ------------------------------------------------- |
| Status Code           | Retorno HTTP 400                                  |
| Faixa de Valores      | Campo setorId deve ser maior ou igual a 1         |
| Regra de Negócio      | Não permitir identificadores inválidos            |
| Mensagem de Erro      | Informar que o valor está abaixo do mínimo aceito |
| Persistência de Dados | Escala não deve ser criada                        |
| Estrutura JSON        | Resposta válida                                   |
| Performance           | Menor que 2000 ms                                 |

##### Script de Teste

```javascript
pm.test("Status code é 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao campo setorId", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("setor");
});

pm.test("Escala não foi criada", function () {
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
| Tratamento Erro       | PASSED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/faf22fe7-a9b0-4591-b67c-4655d323e861" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/ed876417-4f1b-4ef4-9498-4f0cf7401646" />

---

#### Teste API 16 - Tentar Criar Escala com setorId Não Cadastrado

##### Objetivo do Teste

Validar que o sistema impede a criação de uma escala quando o `setorId` informado não corresponde a nenhum setor existente na base de dados.

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="r0a2yu"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="o9x1m5"
{
    "nome": "Esc",
    "mes": "2027-02-01",
    "setorId": 321
}
```

##### Resposta Esperada

**Status Code:** `404 Not Found`

```json id="3k9h1c"
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 404,
    "message": "setor não encontrado com setorId informado",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação               | Descrição                                        |
| ----------------------- | ------------------------------------------------ |
| Status Code             | Retorno HTTP 404                                 |
| Integridade Referencial | setorId deve corresponder a um setor existente   |
| Regra de Negócio        | Não permitir associação com setores inexistentes |
| Mensagem de Erro        | Informar que o setor não foi encontrado          |
| Persistência de Dados   | Escala não deve ser criada                       |
| Estrutura JSON          | Resposta válida                                  |
| Performance             | Menor que 2000 ms                                |

##### Script de Teste

```javascript id="s6n4qy"
pm.test("Status code é 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado ao setor", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("setor");
});

pm.test("Escala não foi criada", function () {
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
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Tratamento Erro       | PASSED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/7c68291a-2d16-44fe-8e84-4c7966193b76" />

---

#### Teste API 17 - Tentar Criar Escala com Mês e Setor Já Utilizados

##### Objetivo do Teste

Validar que o sistema impede a criação de uma nova escala quando já existe uma escala cadastrada para o mesmo mês de referência e setor.

##### Pré-Condição

Existe uma escala previamente cadastrada com os seguintes dados:

```json
{
    "nome": "Escala Existente",
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Informações da Requisição

| Campo        | Valor                     |
| ------------ | ------------------------- |
| Método HTTP  | POST                      |
| Endpoint     | `/api/schedules/`         |
| Autenticação | Bearer Token              |
| Caso de Uso  | CDU 01 - Gerenciar Escala |

##### Cabeçalhos da Requisição

```http id="s7dh3a"
Authorization: Bearer <token>
Content-Type: application/json
```

##### Corpo da Requisição

```json id="n5zt2r"
{
    "nome": "Esc",
    "mes": "2027-02-01",
    "setorId": 10
}
```

##### Resposta Esperada

**Status Code:** `409 Conflict`

```json id="d1n6xy"
{
    "timestamp": "2026-06-17T20:52:04.537562342",
    "status": 409,
    "message": "escala já existente com mês de referência e setorId informados",
    "path": "/api/v1/escalas"
}
```

##### Validações Realizadas

| Validação             | Descrição                                          |
| --------------------- | -------------------------------------------------- |
| Status Code           | Retorno HTTP 409                                   |
| Unicidade             | Não permitir duas escalas para o mesmo mês e setor |
| Regra de Negócio      | Garantir integridade dos dados de planejamento     |
| Mensagem de Erro      | Informar existência prévia da escala               |
| Persistência de Dados | Nenhuma nova escala deve ser criada                |
| Estrutura JSON        | Resposta válida                                    |
| Performance           | Menor que 2000 ms                                  |

##### Script de Teste

```javascript id="c2q4wh"
pm.test("Status code é 409", function () {
    pm.response.to.have.status(409);
});

pm.test("Resposta é JSON válido", function () {
    pm.response.to.have.jsonBody();
});

pm.test("Erro relacionado à existência de escala", function () {
    const jsonData = pm.response.json();

    pm.expect(JSON.stringify(jsonData).toLowerCase())
        .to.include("escala");
});

pm.test("Nenhuma nova escala foi criada", function () {
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
| Status Code           | PASSED    |
| JSON Válido           | PASSED    |
| Tratamento Erro       | PASSED    |
| Escala Não Criada     | PASSED    |
| Performance           | PASSED    |

##### Evidências

<img width="600" alt="image" src="https://github.com/user-attachments/assets/2b838e46-32bf-466f-b341-3ae4f8d719dc" />
