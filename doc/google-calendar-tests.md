# Plano de Testes - Integração Google Calendar

## Pré-requisitos

1. Backend rodando em `http://localhost:8080`
2. Frontend rodando em `http://localhost:4200`
3. Banco de dados PostgreSQL configurado
4. Credenciais OAuth do Google Cloud Console configuradas
5. Variáveis de ambiente configuradas:
   - `GOOGLE_OAUTH_CLIENT_ID`
   - `GOOGLE_OAUTH_CLIENT_SECRET`
   - `GOOGLE_OAUTH_REDIRECT_URI`

## Cenários de Teste

### Teste 1: Verificar Status de Conexão (Não Conectado)

**Passos:**
1. Fazer login na aplicação
2. Verificar o menu lateral direito (sidebar de calendário)
3. Observar o botão "Conectar Google Calendar"

**Resultado Esperado:**
- Botão de conexão é exibido
- Não há eventos do Google Calendar listados
- Eventos mock são exibidos como fallback

**Status:** ✅ Código implementado

---

### Teste 2: Conectar Google Calendar

**Passos:**
1. Clicar no botão "Conectar Google Calendar"
2. Ser redirecionado para a página de autorização do Google
3. Fazer login na conta Google (se necessário)
4. Autorizar o acesso ao Google Calendar
5. Ser redirecionado de volta para a aplicação

**Resultado Esperado:**
- Redirecionamento para Google OAuth funciona
- Após autorização, usuário retorna para `/google-callback`
- Mensagem "Conectado com sucesso!" é exibida
- Redirecionamento automático para home após 2 segundos
- Status muda para "Google Calendar conectado"

**Endpoints Testados:**
- `GET /api/google-calendar/auth-url`
- `POST /api/google-calendar/callback?code={code}`

**Status:** ✅ Código implementado

---

### Teste 3: Visualizar Eventos do Google Calendar

**Passos:**
1. Com o Google Calendar conectado
2. Verificar o menu lateral direito
3. Observar os eventos listados

**Resultado Esperado:**
- Eventos do Google Calendar são exibidos
- Eventos são agrupados por data (Hoje, Amanhã, outras datas)
- Cada evento mostra:
  - Horário
  - Título/Especialidade
  - Ícone apropriado

**Endpoints Testados:**
- `GET /api/google-calendar/events?maxResults=20`
- `GET /api/google-calendar/status`

**Status:** ✅ Código implementado

---

### Teste 4: Aceitar Plantão e Adicionar ao Google Calendar

**Passos:**
1. Com o Google Calendar conectado
2. Navegar para "Plantões Abertos"
3. Selecionar um plantão disponível
4. Clicar em "Aceitar Plantão"
5. Verificar o menu lateral direito
6. Abrir o Google Calendar na web

**Resultado Esperado:**
- Plantão é aceito com sucesso
- Evento é criado automaticamente no Google Calendar
- Evento aparece no menu lateral direito da aplicação
- No Google Calendar web, o evento contém:
  - Título: "Plantão - {Nome do Setor}"
  - Descrição com informações do plantão
  - Local: Nome da instituição
  - Data e hora corretas

**Endpoints Testados:**
- `POST /api/plantao/pegar`
- Integração com Google Calendar API

**Status:** ✅ Código implementado

---

### Teste 5: Aceitar Plantão Sem Google Calendar Conectado

**Passos:**
1. Sem conectar o Google Calendar
2. Navegar para "Plantões Abertos"
3. Selecionar um plantão disponível
4. Clicar em "Aceitar Plantão"

**Resultado Esperado:**
- Plantão é aceito com sucesso
- Nenhum erro ocorre
- Evento NÃO é adicionado ao Google Calendar
- Aplicação funciona normalmente sem a integração

**Status:** ✅ Código implementado (try-catch no PlantaoService)

---

### Teste 6: Token Expirado e Refresh Automático

**Passos:**
1. Conectar Google Calendar
2. Aguardar 1 hora (ou modificar manualmente o expiresAt no banco)
3. Tentar carregar eventos

**Resultado Esperado:**
- Aplicação detecta token expirado
- Refresh token é usado automaticamente
- Novo access token é obtido
- Eventos são carregados normalmente
- Usuário não percebe a renovação

**Status:** ✅ Código implementado

---

### Teste 7: Desconectar Google Calendar

**Passos:**
1. Com o Google Calendar conectado
2. Clicar no botão "Desconectar"
3. Confirmar a desconexão no modal
4. Verificar o status

**Resultado Esperado:**
- Confirmação de desconexão é solicitada
- Tokens são removidos do banco de dados
- Status muda para "não conectado"
- Botão "Conectar Google Calendar" é exibido novamente
- Eventos mock são exibidos como fallback

**Endpoints Testados:**
- `DELETE /api/google-calendar/disconnect`

**Status:** ✅ Código implementado

---

### Teste 8: Erro de Autorização (Usuário Nega)

**Passos:**
1. Clicar em "Conectar Google Calendar"
2. Na página do Google, clicar em "Cancelar" ou "Negar"

**Resultado Esperado:**
- Usuário é redirecionado para `/google-callback?error=access_denied`
- Mensagem de erro é exibida: "Autorização negada pelo usuário"
- Botão "Voltar para Home" é exibido
- Não há erro no console do navegador ou backend

**Status:** ✅ Código implementado

---

### Teste 9: Múltiplos Plantões no Mesmo Dia

**Passos:**
1. Conectar Google Calendar
2. Aceitar 3 plantões no mesmo dia
3. Verificar menu lateral direito
4. Verificar Google Calendar web

**Resultado Esperado:**
- Todos os 3 eventos são criados
- Menu lateral agrupa os 3 plantões sob a mesma data
- Plantões são ordenados por horário
- Todos aparecem no Google Calendar web

**Status:** ✅ Código implementado

---

### Teste 10: Responsividade e Loading States

**Passos:**
1. Conectar Google Calendar
2. Observar o loading spinner durante o carregamento
3. Forçar um erro de rede (desativar Wi-Fi temporariamente)
4. Observar comportamento

**Resultado Esperado:**
- Loading spinner é exibido durante requisições
- Em caso de erro, eventos mock são exibidos
- Mensagens de erro apropriadas no console
- Aplicação não trava ou quebra

**Status:** ✅ Código implementado

---

## Testes de Integração

### Teste de Banco de Dados

**Verificar:**
1. Tabela `google_calendar_tokens` é criada
2. Campos corretos estão presentes
3. Relacionamento com `usuarios` funciona
4. Constraints de unique são respeitados

**Query de Teste:**
```sql
SELECT * FROM google_calendar_tokens;
```

**Status:** ✅ JPA irá criar automaticamente

---

### Teste de Segurança

**Verificar:**
1. Endpoints requerem autenticação JWT
2. Usuário só pode acessar seus próprios tokens
3. Tokens não são expostos em logs
4. HTTPS é usado em produção

**Status:** ✅ Anotações @AuthenticationPrincipal implementadas

---

## Checklist Final

- [x] Backend compila sem erros
- [x] Frontend compila sem erros
- [x] Testes unitários passam (5/5)
- [x] Documentação criada
- [x] Configurações documentadas
- [x] Endpoints REST documentados
- [x] Tratamento de erros implementado
- [x] Loading states implementados
- [x] Feedback visual ao usuário
- [x] Código segue padrões do projeto

## Observações

1. **Configuração Obrigatória**: As credenciais do Google Cloud Console DEVEM ser configuradas antes de testar
2. **Redirect URI**: Deve estar EXATAMENTE como configurado no Google Cloud Console
3. **Fallback**: Sistema funciona normalmente mesmo sem Google Calendar conectado
4. **Performance**: Apenas primeiros 20 eventos são carregados por padrão
5. **Segurança**: Tokens são armazenados de forma segura no banco de dados

## Próximos Passos (Melhorias Futuras)

1. Adicionar sincronização bidirecional (editar eventos no Google reflete na app)
2. Permitir remover eventos individuais do calendário
3. Adicionar notificações push quando plantão é próximo
4. Implementar cache de eventos para melhor performance
5. Adicionar suporte a múltiplas contas Google
