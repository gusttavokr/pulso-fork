# Integração Google Calendar - Pulso

## Configuração

### 1. Criar projeto no Google Cloud Console

1. Acesse [Google Cloud Console](https://console.cloud.google.com/)
2. Crie um novo projeto ou selecione um existente
3. Ative a **Google Calendar API**:
   - No menu, vá em "APIs e Serviços" > "Biblioteca"
   - Busque por "Google Calendar API"
   - Clique em "Ativar"

### 2. Configurar OAuth 2.0

1. Vá em "APIs e Serviços" > "Credenciais"
2. Clique em "Criar credenciais" > "ID do cliente OAuth"
3. Configure a tela de consentimento se solicitado
4. Selecione "Aplicativo da Web"
5. Adicione as URIs de redirecionamento autorizadas:
   - `http://localhost:4200/google-callback`
   - `http://localhost:8080/api/google-calendar/callback` (opcional)
6. Copie o **Client ID** e **Client Secret**

### 3. Configurar variáveis de ambiente

Crie ou edite o arquivo `.env` na raiz do projeto backend:

```env
GOOGLE_OAUTH_CLIENT_ID=seu-client-id-aqui
GOOGLE_OAUTH_CLIENT_SECRET=seu-client-secret-aqui
GOOGLE_OAUTH_REDIRECT_URI=http://localhost:4200/google-callback
```

### 4. Configurar application.yml

O arquivo `application.yml` já está configurado para ler as variáveis de ambiente:

```yaml
google:
  oauth:
    client-id: ${GOOGLE_OAUTH_CLIENT_ID:your-client-id-here}
    client-secret: ${GOOGLE_OAUTH_CLIENT_SECRET:your-client-secret-here}
    redirect-uri: ${GOOGLE_OAUTH_REDIRECT_URI:http://localhost:4200/google-callback}
```

## Como usar

### 1. Conectar Google Calendar

1. Faça login na aplicação Pulso
2. No menu lateral direito, clique em "Conectar Google Calendar"
3. Você será redirecionado para a página de autorização do Google
4. Autorize o acesso ao seu calendário
5. Após autorização, você será redirecionado de volta para a aplicação

### 2. Aceitar um plantão

1. Vá em "Plantões Abertos"
2. Encontre um plantão disponível
3. Clique em "Aceitar plantão"
4. O plantão será automaticamente adicionado ao seu Google Calendar
5. Você pode ver o evento no menu lateral direito da aplicação

### 3. Visualizar eventos

- Os próximos 20 eventos do seu Google Calendar são exibidos no menu lateral direito
- Os eventos são agrupados por data (Hoje, Amanhã, outras datas)
- Os plantões aceitos aparecem automaticamente na lista

### 4. Desconectar Google Calendar

1. No menu lateral direito, clique em "Desconectar"
2. Confirme a desconexão
3. Os dados de autorização serão removidos do banco de dados

## Estrutura de arquivos criados/modificados

### Backend
- `models/GoogleCalendarToken.java` - Entidade para armazenar tokens OAuth
- `repository/GoogleCalendarTokenRepository.java` - Repositório JPA
- `service/GoogleCalendarService.java` - Lógica de integração com Google Calendar
- `controller/GoogleCalendarController.java` - Endpoints REST
- `service/PlantaoService.java` - Modificado para adicionar plantão ao calendário
- `pom.xml` - Dependências do Google Calendar API adicionadas
- `application.yml` - Configurações OAuth2

### Frontend
- `service/google-calendar/google-calendar.service.ts` - Serviço HTTP
- `pages/google-callback/google-callback.ts` - Página de callback OAuth
- `components/sidebar-calendario/sidebar-calendario.ts` - Modificado para usar Google Calendar
- `components/sidebar-calendario/sidebar-calendario.html` - UI atualizada
- `models/sidebar-calendario.model.ts` - Campo titulo adicionado
- `app.routes.ts` - Rota de callback adicionada

## Endpoints da API

- `GET /api/google-calendar/auth-url` - Obter URL de autorização
- `POST /api/google-calendar/callback?code={code}` - Processar callback OAuth
- `GET /api/google-calendar/events?maxResults={n}` - Listar próximos eventos
- `GET /api/google-calendar/status` - Verificar status da conexão
- `DELETE /api/google-calendar/disconnect` - Desconectar calendário

## Fluxo de funcionamento

1. **Autorização inicial**: Usuário conecta sua conta Google
2. **Armazenamento seguro**: Tokens são armazenados no banco de dados
3. **Refresh automático**: Access tokens expirados são renovados automaticamente
4. **Adição de eventos**: Quando um plantão é aceito, evento é criado no Google Calendar
5. **Sincronização**: Eventos são carregados e exibidos no menu lateral

## Segurança

- Tokens são armazenados criptografados no banco de dados
- OAuth2 com PKCE é utilizado para segurança adicional
- Access tokens expiram em 1 hora e são renovados automaticamente
- Refresh tokens permitem acesso contínuo sem re-autorização

## Troubleshooting

### Erro "redirect_uri_mismatch"
- Verifique se a URI de redirecionamento no Google Cloud Console está exatamente como configurada
- URIs devem corresponder exatamente (incluindo http/https, porta, path)

### Tokens não são salvos
- Verifique se as variáveis de ambiente estão configuradas corretamente
- Confirme que o banco de dados tem a tabela `google_calendar_tokens`

### Eventos não aparecem
- Verifique se o usuário autorizou o acesso ao Google Calendar
- Confirme que há eventos futuros no calendário do usuário
- Verifique os logs do backend para erros de API

## Desenvolvimento

Para desenvolver localmente:

1. Configure as credenciais OAuth no Google Cloud Console
2. Configure as variáveis de ambiente
3. Inicie o backend: `cd backend/pulso && ./mvnw spring-boot:run`
4. Inicie o frontend: `cd frontend/pulso && npm start`
5. Acesse http://localhost:4200
