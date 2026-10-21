import { test, expect } from "@playwright/test";

test.describe("Cadastro de Usuário", () => {
  test("deve realizar o cadastro de profissional com sucesso", async ({
    page,
  }) => {
    // Acessar tela de login
    await page.goto("/login");

    // Clicar em "Não possuo uma conta"
    await page.getByText("Não possuo uma conta").click();

    // Validar se redirecionou para etapa 1
    await expect(page).toHaveURL(/cadastro\/etapa1/);

    // Clicar em "Sou um profissional"
    await page.getByText("Sou um profissional").click();

    // Clicar em "Confirmar"
    await page.getByRole("button", { name: "Confirmar" }).click();

    // Validar se foi para etapa 2
    await expect(page).toHaveURL(/cadastro\/etapa2/);

    // Preencher dados básicos
    const uniqueEmail = `profissional.teste${Date.now()}@pulso.dev`;
    await page
      .getByPlaceholder("Digite seu nome completo")
      .fill("Médico de Teste E2E");
    await page.getByPlaceholder("Digite seu e-mail").fill(uniqueEmail);

    // Datepicker nascimento
    await page
      .locator('input[placeholder="Insira a data de nascimento"]')
      .fill("15/08/1990");
    await page
      .locator('input[placeholder="Insira a data de nascimento"]')
      .press("Enter");

    // Telefone
    await page.getByPlaceholder("(99) 99999-9999").fill("84999999999");

    // Senhas
    await page
      .locator('input[placeholder="No mínimo 6 caracteres"]')
      .fill("senha123");
    await page
      .locator('input[placeholder="Digite novamente a senha"]')
      .fill("senha123");

    // Clicar em "Próximo"
    await page.getByRole("button", { name: "Próximo" }).click();

    // Validar se foi para etapa 3
    await expect(page).toHaveURL(/cadastro\/etapa3/);

    // Preencher dados específicos da etapa 3 - Usando o id #crm para evitar violações de modo estrito
    await page.locator("#crm").fill("12345");

    // Selecionar Estado CRM (p-select)
    await page.locator('p-select[formControlName="estadoCrm"]').click();
    await page
      .locator('.p-select-option, .p-select-item, [role="option"]')
      .filter({ hasText: /^Rn$/ })
      .click();

    console.log(
      await page.locator('input[placeholder="Selecione a data"]').count(),
    );

    // Preencher Data de formação
    const dataFormacao = page.locator('input[placeholder="Selecione a data"]');

    await dataFormacao.click();
    await page.getByText("15", { exact: true }).click();

    await page.waitForTimeout(500);

    console.log(await page.locator("body").innerText());

    // Selecionar Especialidade (p-select)
    await page.locator('p-select[formControlName="especialidade"]').click();
    await page
      .locator('.p-select-option, .p-select-item, [role="option"]')
      .filter({ hasText: /^Cardiologia$/ })
      .click();

    // Selecionar Tipo de PIX (p-select)
    await page.locator('p-select[formControlName="tipoPix"]').click();
    await page
      .locator('.p-select-option, .p-select-item, [role="option"]')
      .filter({ hasText: /^Cpf$/ })
      .click();

    // Chave PIX
    await page.getByPlaceholder("Digite a chave PIX").fill("12345678909");

    // Clicar em "Finalizar cadastro"
    await page.getByRole("button", { name: "Finalizar cadastro" }).click();

    console.log("URL:", page.url());

    console.log(await page.locator("body").innerText());

    // Clicar em "Finalizar cadastro"
    await page.getByRole("button", { name: "Finalizar cadastro" }).click();

    // espera a requisição do cadastro acontecer
    await page.waitForResponse(
      (response) =>
        response.url().includes("/usuarios/cadastro") &&
        response.request().method() === "POST",
      { timeout: 10000 },
    );

    // valida que não ficou com erro de validação na tela
    await expect(
      page.getByText("Data de formação é obrigatória."),
    ).not.toBeVisible();

    await expect(
      page.getByText("Preencha todos os campos obrigatórios."),
    ).not.toBeVisible();

    console.log("Cadastro enviado com sucesso");
  });

  test("deve realizar o cadastro de instituição com sucesso", async ({
    page,
  }) => {
    // Acessar tela de login
    await page.goto("/login");

    // Clicar em "Não possuo uma conta"
    await page.getByText("Não possuo uma conta").click();

    // Clicar em "Sou uma instituição"
    await page.getByText("Sou uma instituição").click();

    // Clicar em "Confirmar"
    await page.getByRole("button", { name: "Confirmar" }).click();

    // Validar se foi para etapa 2
    await expect(page).toHaveURL(/cadastro\/etapa2/);

    // Preencher dados básicos
    const uniqueEmail = `hospital.teste${Date.now()}@pulso.dev`;
    await page
      .getByPlaceholder("“Hospital Walfredo Gurgel”")
      .fill("Hospital E2E Teste");
    await page.getByPlaceholder("“hospital@gmail.com”").fill(uniqueEmail);

    // CNPJ - Vamos usar um CNPJ válido matematicamente para passar na validação: 06.990.590/0001-23
    await page.getByPlaceholder("00.000.000/0000-00").fill("06990590000123");

    // Telefone
    await page.getByPlaceholder("(99) 99999-9999").fill("84888888888");

    // Senhas
    await page
      .locator('input[placeholder="No mínimo 6 caracteres"]')
      .fill("senha123");
    await page
      .locator('input[placeholder="Digite novamente a senha"]')
      .fill("senha123");

    // Clicar em "Próximo"
    await page.getByRole("button", { name: "Próximo" }).click();

    // Validar se foi para etapa 3
    await expect(page).toHaveURL(/cadastro\/etapa3/);

    // Endereço completo
    await page
      .getByPlaceholder("Rua Itaquara, 1234, bairro Pompeia")
      .fill("Rua dos Hospitais, 123");

    //Foto do perfil (Upload do arquivo)
    await page.setInputFiles('input[type="file"]', {
      name: "avatar.png",
      mimeType: "image/png",
      buffer: Buffer.from("fake-image-data-content"),
    });

    // Clicar em "Finalizar cadastro"
    // Clicar em "Finalizar cadastro"
    await page.getByRole("button", { name: "Finalizar cadastro" }).click();

    // Aguarda o POST do cadastro
    await page.waitForResponse(
      (response) =>
        response.url().includes("/usuarios/cadastro") &&
        response.request().method() === "POST",
      { timeout: 10000 },
    );

    // Garante que não caiu em erro de validação
    await expect(
      page.getByText("Preencha todos os campos obrigatórios."),
    ).not.toBeVisible();

    console.log("Cadastro de instituição enviado com sucesso");
  });

  test("deve impedir avanço com senhas incompatíveis na etapa 2", async ({
    page,
  }) => {
    // Acessar tela de login
    await page.goto("/login");

    // Clicar em "Não possuo uma conta"
    await page.getByText("Não possuo uma conta").click();

    // Escolher profissional
    await page.getByText("Sou um profissional").click();
    await page.getByRole("button", { name: "Confirmar" }).click();

    // Preencher dados básicos com senhas diferentes
    await page
      .getByPlaceholder("Digite seu nome completo")
      .fill("Nome Invalido");
    await page.getByPlaceholder("Digite seu e-mail").fill("invalido@email.com");
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
      .fill("senhaDiferente");

    // Tentar avançar
    await page.getByRole("button", { name: "Próximo" }).click();

    // Verificar se exibiu a mensagem de erro
    await expect(page.getByText("As senhas não coincidem.")).toBeVisible();
    // E permaneceu na etapa 2
    await expect(page).toHaveURL(/cadastro\/etapa2/);
  });

  test("deve impedir avanço de instituição com CNPJ inválido", async ({
    page,
  }) => {
    // Acessar tela de login
    await page.goto("/login");

    // Clicar em "Não possuo uma conta"
    await page.getByText("Não possuo uma conta").click();

    // Escolher instituição
    await page.getByText("Sou uma instituição").click();
    await page.getByRole("button", { name: "Confirmar" }).click();

    // Preencher com CNPJ inválido
    await page
      .getByPlaceholder("“Hospital Walfredo Gurgel”")
      .fill("Hospital Invalido");
    await page
      .getByPlaceholder("“hospital@gmail.com”")
      .fill("invalido@hospital.com");
    await page.getByPlaceholder("00.000.000/0000-00").fill("11111111111111"); // CNPJ inválido
    await page.getByPlaceholder("(99) 99999-9999").fill("84888888888");
    await page
      .locator('input[placeholder="No mínimo 6 caracteres"]')
      .fill("senha123");
    await page
      .locator('input[placeholder="Digite novamente a senha"]')
      .fill("senha123");

    // Tentar avançar
    await page.getByRole("button", { name: "Próximo" }).click();

    // Verificar erro de CNPJ
    await expect(page.getByText("CNPJ inválido.")).toBeVisible();
    // E permaneceu na etapa 2
    await expect(page).toHaveURL(/cadastro\/etapa2/);
  });
});
