# <img src="https://playwright.dev/img/playwright-logo.svg" width="64" style="position:relative; top:8px;"> Guia Prático de Playwright

## O que é o Playwright?

O Playwright é um framework open-source de automação de testes desenvolvido para aplicações web. Ele permite simular ações de um usuário real, como navegar entre páginas, clicar em botões, preencher formulários e validar informações exibidas na tela. Seu principal uso é na realização de testes End-to-End (E2E), que verificam o funcionamento completo de uma aplicação a partir da interface do usuário, podendo validar desde o frontend até a comunicação com APIs e bancos de dados. Além disso, o Playwright oferece suporte a diversos navegadores, captura de evidências visuais, emulação de dispositivos móveis e execução paralela de testes, tornando o processo de garantia de qualidade mais confiável e eficiente.

O Playwright testa a aplicação pela perspectiva do usuário. Embora os testes sejam executados no navegador e interajam diretamente com o frontend, eles também podem validar o funcionamento do backend, APIs e banco de dados quando a aplicação estiver integrada.

## Requisitos

- [Node.js](https://nodejs.org/pt-br/download)
  > Ao instalar o Node.js, virá junto o npm.

## Passo a Passo para uso do Playwright

Neste passo a passo, ensinaremos a como configurar o seu projeto para usar o Playwright. Sendo válido para qualquer framework usado.

### Passo 1 - Configurar o projeto

Para este guia, disponibilizamos um projeto front-end em Angular na pasta `./frontend`. Acesse a pasta e instale as dependências com:

```bash
cd frontend
npm install
```

### Passo 2 - Instalar o Playwright

Seguindo o princípio da separação de responsabilidades, manteremos os testes de ponta a ponta isolados do código da aplicação.

```bash
npm init playwright@latest
```

> Se preferir, você pode fazer a instalação manualmente com:

```bash
npm init playwright
```

> Para navegadores específicos, use:

```bash
npm init playwright-firefox
```

Durante a instalação, o terminal fará algumas perguntas interativas. Escolha as seguintes opções:

- **TypeScript ou JavaScript?** Selecione `TypeScript` (Como o frontend é feito em Angular, escolha TypeScript).
- **Where to put your End-to-End tests?** Apenas aperte Enter para manter o nome padrão: `tests`.
- **Add a GitHub Actions workflow?** Digite `true` (Y) para já criar o arquivo de CI/CD.
- **Install Playwright operating system dependencies?** Digite `true` (Y) para instalar os navegadores e dependências necessárias.

Após a execução, será criada uma pasta `./tests`.

### Passo 3 - Conectar o Playwright ao Framework (Angular)

Como separamos o Playwright do código do Angular, precisamos ensinar o Playwright a inicializar a nossa aplicação antes de rodar os testes. Caso contrário, ele tentará acessar uma página que não está de pé.

Quando um teste Playwright é executado, ele precisa acessar uma aplicação em funcionamento. Como nosso frontend Angular não estará necessariamente iniciado, configuraremos o Playwright para iniciar automaticamente o servidor antes da execução dos testes.

Abra o arquivo `playwright.config.ts` criado na raiz do seu projeto e, no final do arquivo, configure a propriedade `webServer`:

```typescript
export default defineConfig({
  // ... outras configurações padrão acima

  webServer: {
    command: "npm run start --prefix frontend", // 1. Comando para subir o Angular
    url: "http://localhost:4200", // 2. URL onde o Angular roda
    reuseExistingServer: !process.env.CI, // 3. Reutiliza o servidor se já estiver aberto localmente
    timeout: 120 * 1000, // 4. Tempo limite para o Angular buildar e subir
  },

  use: {
    baseURL: "http://localhost:4200", // Define a URL base para não precisar digitá-la em todo teste
    // ... outras configurações
  },
});
```

#### 💡 O que esse comando faz?

O parâmetro `--prefix frontend` diz ao Node para entrar temporariamente na pasta do Angular, executar o `npm run start` (que roda o `ng serve`) e carregar a aplicação de forma automatizada.

## Passo 4 - Criando nosso Primeiro Teste

4.1. Crie o arquivo do teste. `tests/home.spec.ts`
4.2. Exemplo de conteúdo:

```typescript
import { test, expect } from "@playwright/test"; // Testes e Asserts

test("meu primeiro teste", async ({ page }) => {
  await page.goto("/");

  // Exemplos de comandos:
  // await page.click('button');
  // await page.fill('#email', 'gustavo@email.com');

  await expect(page).toHaveTitle(/Pulso/i); // Verificando o título da página
});
```

4.3. Execute:

```bash
npx playwright test
```

4.4. Após a execução, deve ser exibido o retorno definido

```bash
✓ meu primeiro teste (1.2s)

1 passed
```

---

## Passo 5 - Aplicando o Playwright no Projeto Pulso

Após a configuração inicial do Playwright, a ferramenta foi integrada ao projeto Pulso para validar funcionalidades críticas da aplicação através de testes End-to-End (E2E).

### Estrutura de Testes

Foi criada a pasta `tests` na raiz do projeto para centralizar os cenários automatizados:

```text
tests/
├── home.spec.ts
├── login-valido.spec.ts
└── login-invalido.spec.ts
```

### Configuração do Playwright

O arquivo `playwright.config.ts` foi configurado para utilizar a aplicação Angular executando localmente na porta 4200.

```typescript
import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./tests",

  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: process.env.CI ? 1 : undefined,

  reporter: "html",

  use: {
    baseURL: "http://localhost:4200",
    trace: "on-first-retry",
  },

  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] },
    },
    {
      name: "firefox",
      use: { ...devices["Desktop Firefox"] },
    },
    {
      name: "webkit",
      use: { ...devices["Desktop Safari"] },
    },
  ],
});
```

### Observação sobre o uso com Docker

Durante a integração do Playwright ao projeto Pulso, a configuração `webServer` foi mantida comentada no arquivo `playwright.config.ts`.

Isso ocorreu porque a aplicação já é iniciada através do Docker Compose, utilizando o comando:

```bash
docker compose up

```

Como os containers do frontend e backend já ficam disponíveis antes da execução dos testes, não é necessário que o Playwright inicialize automaticamente o servidor Angular.

#### O que essa configuração faz?

- Define a pasta `tests` como diretório padrão dos testes.
- Executa os testes em paralelo quando possível.
- Gera relatórios HTML automaticamente.
- Permite execução nos navegadores Chromium, Firefox e WebKit.
- Configura a URL base da aplicação como `http://localhost:4200`.
- Captura rastreamento (`trace`) automaticamente em casos de falha para facilitar a depuração.

---

### Teste 1 - Acesso à Página Inicial

O primeiro teste valida se a aplicação está acessível e se o título da página corresponde ao sistema Pulso.

Arquivo: `home.spec.ts`

```typescript
import { test, expect } from "@playwright/test";

test("deve abrir a página inicial", async ({ page }) => {
  await page.goto("/");

  await expect(page).toHaveTitle(/Pulso/i);
});
```

#### Objetivo do teste

- Acessar a página inicial da aplicação.
- Verificar se o sistema foi carregado corretamente.
- Validar o título da página exibido ao usuário.

---

### Teste 2 - Login com Credenciais Válidas

Este teste verifica se um usuário cadastrado consegue realizar autenticação com sucesso.

Arquivo: `login-valido.spec.ts`

```typescript
import { test, expect } from "@playwright/test";

test("deve realizar login com sucesso", async ({ page }) => {
  await page.goto("/login");

  await page.locator('input[name="email"]').fill("lider1@pulso.dev");

  await page.locator('input[type="password"]').fill("senha123");

  await page.locator('button[type="submit"]').click();

  await expect(page).not.toHaveURL("/");
});
```

#### Objetivo do teste

- Acessar a tela de login.
- Preencher credenciais válidas.
- Realizar autenticação.
- Confirmar que o usuário foi redirecionado para outra página após o login.

---

### Teste 3 - Login com Credenciais Inválidas

Este teste garante que usuários com credenciais incorretas não consigam acessar a aplicação.

Arquivo: `login-invalido.spec.ts`

```typescript
import { test, expect } from "@playwright/test";

test("deve impedir login com credenciais inválidas", async ({ page }) => {
  await page.goto("/login");

  await page.locator('input[name="email"]').fill("teste@teste.com");

  await page.locator('input[type="password"]').fill("senhaerrada");

  await page.locator('button[type="submit"]').click();

  await expect(page).toHaveURL(/login/);
});
```

#### Objetivo do teste

- Acessar a tela de login.
- Informar credenciais inválidas.
- Tentar autenticar.
- Verificar que o usuário permanece na página de login.

---

---

### Teste 4 - Cadastro de Profissional com Sucesso

Este teste valida o fluxo completo de cadastro de um profissional, verificando o preenchimento dos dados pessoais e profissionais até o envio do cadastro.

Arquivo: `cadastro-spec.ts`

```typescript
import { test, expect } from "@playwright/test";

test("deve realizar o cadastro de profissional com sucesso", async ({
  page,
}) => {
  await page.goto("/login");

  await page.getByText("Não possuo uma conta").click();

  await expect(page).toHaveURL(/cadastro\/etapa1/);

  await page.getByText("Sou um profissional").click();

  await page
    .getByRole("button", {
      name: "Confirmar",
    })
    .click();

  await expect(page).toHaveURL(/cadastro\/etapa2/);

  const uniqueEmail = `profissional.teste${Date.now()}@pulso.dev`;

  await page
    .getByPlaceholder("Digite seu nome completo")
    .fill("Médico de Teste E2E");

  await page.getByPlaceholder("Digite seu e-mail").fill(uniqueEmail);

  await page
    .locator('input[placeholder="Insira a data de nascimento"]')
    .fill("15/08/1990");

  await page
    .locator('input[placeholder="Insira a data de nascimento"]')
    .press("Enter");

  await page.getByPlaceholder("(99) 99999-9999").fill("84999999999");

  await page
    .locator('input[placeholder="No mínimo 6 caracteres"]')
    .fill("senha123");

  await page
    .locator('input[placeholder="Digite novamente a senha"]')
    .fill("senha123");

  await page
    .getByRole("button", {
      name: "Próximo",
    })
    .click();

  await expect(page).toHaveURL(/cadastro\/etapa3/);

  await page.locator("#crm").fill("12345");

  await page.locator('p-select[formControlName="estadoCrm"]').click();

  await page
    .locator('.p-select-option, .p-select-item, [role="option"]')
    .filter({ hasText: /^Rn$/ })
    .click();

  await page
    .getByRole("button", {
      name: "Finalizar cadastro",
    })
    .click();

  await page.waitForResponse(
    (response) =>
      response.url().includes("/usuarios/cadastro") &&
      response.request().method() === "POST",
    { timeout: 10000 },
  );

  await expect(
    page.getByText("Preencha todos os campos obrigatórios."),
  ).not.toBeVisible();
});
```

#### Objetivo do teste

- Acessar o fluxo de cadastro.
- Escolher o tipo profissional.
- Preencher dados pessoais.
- Preencher dados profissionais.
- Enviar o cadastro.
- Validar que o cadastro foi realizado sem erros.

---

### Teste 5 - Criação de Nova Escala

Este teste valida se um usuário autenticado consegue acessar o módulo de escalas e iniciar a criação de uma nova escala.

Arquivo: `criar-escala.spec.ts`

```typescript
import { test, expect } from "@playwright/test";

test("deve criar uma escala", async ({ page }) => {
  await page.goto("/login");

  await page.getByPlaceholder("E-mail").fill("lider1@pulso.dev");

  await page.getByPlaceholder("Senha").fill("senha123");

  await Promise.all([
    page.waitForURL((url) => !url.pathname.includes("login")),

    page
      .getByRole("button", {
        name: "Entrar",
        exact: true,
      })
      .click(),
  ]);

  await page
    .getByText("Minhas escalas", {
      exact: true,
    })
    .click();

  await expect(page).toHaveURL(/minhas-escalas/);

  await page
    .getByText("Criar nova escala", {
      exact: true,
    })
    .click();

  await expect(
    page.getByText("Nome da escala", {
      exact: true,
    }),
  ).toBeVisible();

  const inputs = page.locator("input");

  await inputs.nth(0).fill("Escala teste Playwright");

  await page
    .getByRole("button", {
      name: "Próxima etapa",
      exact: true,
    })
    .click();

  await expect(
    page.getByText("Mês de referência", {
      exact: true,
    }),
  ).toBeVisible();
});
```

#### Objetivo do teste

- Realizar login com usuário válido.
- Acessar a área de escalas.
- Criar uma nova escala.
- Validar o avanço para a próxima etapa do formulário.

---

### Executando os Testes

Para executar todos os testes automatizados:

```bash
npx playwright test
```

Para executar apenas um teste específico:

```bash
npx playwright test tests/home.spec.ts
```

ou

```bash
npx playwright test tests/login-valido.spec.ts
```

Após a execução, o Playwright exibirá o resultado dos testes no terminal.

Exemplo:

```bash
✓ deve abrir a página inicial
✓ deve realizar login com sucesso
✓ deve impedir login com credenciais inválidas

3 passed
```

Além disso, será gerado automaticamente um relatório HTML contendo detalhes da execução dos testes, permitindo a análise dos resultados e possíveis falhas encontradas.

## 🌐 Como usar essa mesma lógica em OUTROS Frameworks?

O grande trunfo do Playwright é que ele não se importa com a tecnologia do seu front-end. Se você decidir migrar o projeto para **React**, **Vue**, **Next.js** ou **Svelte**, a estrutura de testes continua exatamente a mesma.

A única coisa que você precisará mudar no futuro são as propriedades `command` e `url` dentro do `webServer`. Veja os exemplos:

- **Se fosse um projeto React (Vite):**
  ```typescript
  command: 'npm run dev --prefix frontend',
  url: 'http://localhost:5173',
  ```
- **Se fosse um projeto Next.js:**
  ```typescript
  command: 'npm run dev --prefix frontend',
  url: 'http://localhost:3000',
  ```
- **Se fosse um projeto Vue:**
  ```typescript
  command: 'npm run serve --prefix frontend',
  url: 'http://localhost:8080',
  ```

## Referências

- Análise comparativa: https://testomat.io/blog/playwright-vs-selenium-vs-cypress-a-detailed-comparison/
- Angular Documentation: https://angular.dev/
- Getting Started: https://playwright.dev/docs/intro
- Playwright Documentation: https://playwright.dev/
