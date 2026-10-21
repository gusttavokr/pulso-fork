# Documento Geral de Casos de Teste - Robson

## Introdução
Este documento reúne os casos de teste elaborados para validar os principais fluxos da aplicação Pulso, desenvolvida no contexto do PDS Corporativo. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada caso de teste inclui informações detalhadas sobre o cenário a ser avaliado, os dados de entrada necessários e resultados esperados.

## CDU 12 - Conectar com Google Calendário

### Objetivo:
Permitir que o usuário conecte sua conta Google ao sistema para sincronizar eventos e enviar plantões ao Google Calendar.

### Variáveis de Entrada:
- Variável 1: email
- Variável 2: code
- Variável 3: maxResults

### Condições de Negócio:
- Condição 1: o usuário deve estar autenticado no sistema.
- Condição 2: o e-mail deve pertencer a um usuário existente.
- Condição 3: o código de autorização (`code`) deve ser informado e ser válido no Google OAuth.
- Condição 4: as credenciais OAuth do Google devem estar configuradas no servidor.
- Condição 5: ao conectar com sucesso, os tokens devem ser armazenados no banco de dados.
- Condição 6: ao consultar eventos, o usuário deve ter autorização prévia para o Google Calendar.
- Condição 7: ao consultar eventos, `maxResults` deve ser inteiro maior ou igual a 1.

### Classes de Equivalência
| Classes Válidas | Classes Inválidas |
|-----------------|-------------------|
| `email` de usuário existente e autenticado | `email` de usuário inexistente |
| `code` informado e válido | `code` inválido, expirado, vazio ou ausente |
| credenciais OAuth configuradas no servidor | credenciais OAuth ausentes ou inválidas |
| conexão ainda não existente e criada com sucesso | tentativa de uso sem autorização prévia |
| conexão existente no banco de dados | desconexão sem token salvo |
| `maxResults` inteiro >= 1 | `maxResults` nulo, zero, negativo ou não numérico |

### Aplicação de Valor Limite
| Variável | Regra/Limite | Valor limite inválido (abaixo) | Valor limite válido | Valor logo acima do limite | Casos adicionais de fronteira |
|----------|--------------|---------------------------------|---------------------|----------------------------|-------------------------------|
| `maxResults` | inteiro >= 1 | -1 e 0 | 1 | 2 | 20 (limite de listagem usado no sistema) |



### Fluxo Principal - Conectar Google Calendar

#### Testes funcionais

| email | code | Resultado esperado | Resultado obitido | Situação |
|-------|------|--------------------|-------------------|----------|
| "usuario@hospital.com" | "code-valido-google" | sucesso({"message": "Conta Google Calendar conectada com sucesso!"}) | --- | --- |
| "usuario@hospital.com" | "code-valido-google" | sucesso({"message": "Conta Google Calendar conectada com sucesso!"}) e conexão atualizada quando o token já existir | --- | --- |
| "usuario@hospital.com" | "code-invalido-ou-expirado" | erro({"error": "Erro ao conectar com Google Calendar: código de autorização inválido ou expirado"}) | --- | --- |
| "usuario-inexistente@hospital.com" | "code-valido-google" | erro("Usuário não encontrado") | --- | --- |

### Fluxo de Exceção - Callback sem code (c valor limite)

#### Testes funcionais

| email | code | Resultado esperado | Resultado obitido | Situação |
|-------|------|--------------------|-------------------|----------|
| "usuario@hospital.com" | null | erro("Requisição inválida: code não informado") | --- | --- |
| "usuario@hospital.com" | "" | erro({"error": "Erro ao conectar com Google Calendar: código de autorização inválido ou expirado"}) | --- | --- |
| "usuario@hospital.com" | "x" | erro({"error": "Erro ao conectar com Google Calendar: código inválido"}) ou sucesso caso o Google aceite o código | --- | --- |

### Fluxo Secundário - Verificar status da conexão

#### Testes funcionais

| email | Resultado esperado | Resultado obitido | Situação |
|-------|--------------------|-------------------|----------|
| "usuario@hospital.com" | sucesso({"connected": true}) | --- | --- |
| "usuario-sem-token@hospital.com" | sucesso({"connected": false}) | --- | --- |
| "usuario-inexistente@hospital.com" | sucesso({"connected": false}) | --- | --- |

### Fluxo Secundário - Listar eventos do Google Calendar

#### Testes funcionais

| email | code | maxResults | Resultado esperado | Resultado obitido | Situação |
|-------|------|------------|--------------------|-------------------|----------|
| "usuario@hospital.com" | null | 1 | sucesso(lista de eventos do Google Calendar com no máximo 1 item) | --- | --- |
| "usuario@hospital.com" | null | 2 | sucesso(lista de eventos do Google Calendar com no máximo 2 itens) | --- | --- |
| "usuario@hospital.com" | null | 10 | sucesso(lista de eventos do Google Calendar) | --- | --- |
| "usuario@hospital.com" | null | 20 | sucesso(lista de eventos do Google Calendar) | --- | --- |
| "usuario@hospital.com" | null | 0 | erro("maxResults deve ser maior ou igual a 1") ou erro de validação de requisição | --- | --- |
| "usuario@hospital.com" | null | -1 | erro("maxResults deve ser maior ou igual a 1") ou erro de validação de requisição | --- | --- |
| "usuario-sem-token@hospital.com" | null | 10 | erro("Usuário não autorizou acesso ao Google Calendar.") | --- | --- |
| "usuario-inexistente@hospital.com" | null | 10 | erro("Usuário não encontrado") | --- | --- |

### Fluxo Secundário - Desconectar Google Calendar

#### Testes funcionais

| email | Resultado esperado | Resultado obitido | Situação |
|-------|--------------------|-------------------|----------|
| "usuario@hospital.com" | sucesso({"message": "Google Calendar desconectado com sucesso!"}) | --- | --- |
| "usuario-sem-token@hospital.com" | sucesso({"message": "Google Calendar desconectado com sucesso!"}) | --- | --- |
| "usuario-inexistente@hospital.com" | erro("Usuário não encontrado") | --- | --- |

### Fluxo de Exceção I - Usuário não encontrado

#### Testes funcionais

| email | code | maxResults | Resultado esperado | Resultado obitido | Situação |
|-------|------|------------|--------------------|-------------------|----------|
| "email.invalido@hospital.com" | "code-valido-google" | 10 | erro("Usuário não encontrado") | --- | --- |
| "" | "code-valido-google" | 10 | erro("Usuário não encontrado") | --- | --- |
| null | "code-valido-google" | 10 | erro("Usuário não encontrado") | --- | --- |

### Fluxo de Exceção II - Acesso ao Google Calendar não autorizado

#### Testes funcionais

| email | code | maxResults | Resultado esperado | Resultado obitido | Situação |
|-------|------|------------|--------------------|-------------------|----------|
| "usuario-sem-token@hospital.com" | null | 10 | erro("Usuário não autorizou acesso ao Google Calendar. Porfavor, conecte sua conta.") | --- | --- |
| "usuario-sem-token@hospital.com" | null | 20 | erro("Usuário não autorizou acesso ao Google Calendar.") | --- | --- |

### Fluxo de Exceção III - Credenciais OAuth não configuradas

#### Testes funcionais

| email | code | Resultado esperado | Resultado obitido | Situação |
|-------|------|--------------------|-------------------|----------|
| "usuario@hospital.com" | "code-valido-google" | erro("As credenciais OAuth do Google não estão configuradas no servidor.") | --- | --- |
| "usuario@hospital.com" | "x" | erro("As credenciais OAuth do Google não estão configuradas no servidor.") | --- | --- |
---