# CDU 12 - Integração com Google Calendar

## Objetivo

O objetivo deste caso de uso é permitir que usuários integrem sua conta Google Calendar ao plataforma Pulso, possibilitando a sincronização e consulta de eventos relacionados aos plantões cadastrados no sistema. O fluxo contempla autorização OAuth, verificação de conexão, consulta de eventos e desconexão da conta Google.

### Fluxo Principal - Integração com Google Calendar

#### Teste API 01 - Obter URL de Autorização

##### Objetivo do Teste

Validar que o sistema gera corretamente a URL de autorização OAuth do Google Calendar para usuários autenticados.

##### Informações da Requisição

| Campo        | Valor                               |
| ------------ | ----------------------------------- |
| Método HTTP  | GET                                 |
| Endpoint     | `/api/google-calendar/auth-url`     |
| Autenticação | JWT Obrigatório                     |
| Caso de Uso  | CDU 12 - Integração Google Calendar |

##### Cabeçalhos da Requisição

```http
Authorization: Bearer <jwt>
```

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
  "authUrl": "https://accounts.google.com/o/oauth2/auth?..."
}
```

##### Validações Realizadas

| Validação         | Descrição                                |
| ----------------- | ---------------------------------------- |
| Status Code       | Verificar retorno HTTP 200               |
| Campo Obrigatório | Verificar presença do campo authUrl      |
| Content-Type      | Verificar retorno JSON                   |
| JSON Válido       | Validar estrutura da resposta            |
| Dados Específicos | Verificar que a URL aponta para o Google |
| Performance       | Tempo de resposta inferior a 2000 ms     |

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASS      |
| Estrutura da Resposta | PASS      |
| Performance           | PASS      |
| Segurança             | PASS      |

##### Evidências

Execução do teste no Postman.

![CT-01 - Obter URL de Autorização](https://github.com/user-attachments/assets/5b44fa55-076d-4091-b787-52ad7429b196)

#### Teste API 02 - Verificar Status da Integração

##### Objetivo do Teste

Validar que o sistema informa corretamente se o usuário possui integração ativa com o Google Calendar.

##### Informações da Requisição

| Campo        | Valor                               |
| ------------ | ----------------------------------- |
| Método HTTP  | GET                                 |
| Endpoint     | `/api/google-calendar/status`       |
| Autenticação | JWT Obrigatório                     |
| Caso de Uso  | CDU 12 - Integração Google Calendar |

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
  "connected": true
}
```

##### Validações Realizadas

| Validação         | Descrição                             |
| ----------------- | ------------------------------------- |
| Status Code       | Verificar retorno HTTP 200            |
| Campo Obrigatório | Verificar presença do campo connected |
| Tipo de Dado      | Verificar valor booleano              |
| JSON Válido       | Validar estrutura da resposta         |
| Performance       | Tempo de resposta inferior a 2000 ms  |

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASS      |
| Estrutura da Resposta | PASS      |
| Performance           | PASS      |
| Segurança             | PASS      |

##### Evidências

Execução do teste no Postman.

![CT-02 - Obter URL de Autorização](https://github.com/user-attachments/assets/176715ca-3ab7-4e40-8814-409ef86a2b96)

---

#### Teste API 03 - Conectar Conta Google Calendar

##### Objetivo do Teste

Validar que o sistema conclui corretamente o fluxo OAuth utilizando um código de autorização válido.

##### Informações da Requisição

| Campo        | Valor                               |
| ------------ | ----------------------------------- |
| Método HTTP  | POST                                |
| Endpoint     | `/api/google-calendar/callback`     |
| Autenticação | JWT Obrigatório                     |
| Caso de Uso  | CDU 12 - Integração Google Calendar |

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
  "message": "Conta Google Calendar conectada com sucesso!"
}
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASS      |
| Estrutura da Resposta | PASS      |
| Performance           | PASS      |
| Segurança             | PASS      |

##### Evidências

Execução do teste no Postman.

![CT-03 - Obter URL de Autorização](https://github.com/user-attachments/assets/8943f68c-ac7d-46e8-bbf2-2d4ed2e8abc5)

---

#### Teste API 04 - Código OAuth Inválido

##### Objetivo do Teste

Validar o tratamento de erro para códigos OAuth inválidos.

##### Resposta Esperada

**Status Code:** `400 Bad Request`

```json
{
  "error": "..."
}
```

##### Resultado Obtido

| Item               | Resultado |
| ------------------ | --------- |
| Status Code        | PASS      |
| Tratamento de Erro | PASS      |
| Segurança          | PASS      |

##### Evidências

Execução do teste no Postman.

![CT-04 - Obter URL de Autorização](https://github.com/user-attachments/assets/971af5fc-4415-4817-b60a-50ee6ad45fef)

---

#### Teste API 05 - Consultar Eventos do Calendário

##### Objetivo do Teste

Validar a recuperação dos eventos cadastrados na conta Google Calendar conectada.

##### Informações da Requisição

| Campo        | Valor                               |
| ------------ | ----------------------------------- |
| Método HTTP  | GET                                 |
| Endpoint     | `/api/google-calendar/events`       |
| Autenticação | JWT Obrigatório                     |
| Caso de Uso  | CDU 12 - Integração Google Calendar |

##### Resposta Esperada

**Status Code:** `200 OK`

```json
[
  {
    "id": "...",
    "summary": "...",
    "start": {},
    "end": {}
  }
]
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASS      |
| Estrutura da Resposta | PASS      |
| Performance           | PASS      |
| Segurança             | PASS      |

##### Evidências

Execução do teste no Postman.

![CT-05 - Obter URL de Autorização](https://github.com/user-attachments/assets/92f2ed6a-2ac5-4e3e-a7c3-ee43459acea7)

---

#### Teste API 06 - Desconectar Google Calendar

##### Objetivo do Teste

Validar a remoção da autorização da conta Google Calendar.

##### Informações da Requisição

| Campo        | Valor                               |
| ------------ | ----------------------------------- |
| Método HTTP  | DELETE                              |
| Endpoint     | `/api/google-calendar/disconnect`   |
| Autenticação | JWT Obrigatório                     |
| Caso de Uso  | CDU XX - Integração Google Calendar |

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
  "message": "Google Calendar desconectado com sucesso!"
}
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASS      |
| Estrutura da Resposta | PASS      |
| Performance           | PASS      |
| Segurança             | PASS      |

##### Evidências

Execução do teste no Postman.

![CT-06 - Obter URL de Autorização](https://github.com/user-attachments/assets/cad03792-d594-4170-a1cb-cbc88ab28459)

---

#### Teste API 07 - Consultar Eventos Sem Integração Ativa

##### Objetivo do Teste

Consultar eventos do calendário

##### Informações da Requisição

| Campo        | Valor                               |
| ------------ | ----------------------------------- |
| Método HTTP  | GET                                 |
| Endpoint     | `/api/google-calendar/auth-url`     |
| Autenticação | JWT Obrigatório                     |
| Caso de Uso  | CDU 12 - Integração Google Calendar |

##### Resposta Esperada

**Status Code:** `200 OK`

```json
{
  "message": "Google Calendar desconectado com sucesso!"
}
```

##### Resultado Obtido

| Item                  | Resultado |
| --------------------- | --------- |
| Status Code           | PASS      |
| Estrutura da Resposta | PASS      |
| Performance           | PASS      |
| Segurança             | PASS      |

##### Evidências

Execução do teste no Postman.

![CT-07 - Obter URL de Autorização](https://github.com/user-attachments/assets/4a60e873-d367-4f40-a5b2-6560a41f026f)
