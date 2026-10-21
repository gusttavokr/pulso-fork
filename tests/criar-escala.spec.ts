import { test, expect } from "@playwright/test";

test.describe("Criar Escala", () => {
  test("deve criar uma escala", async ({ page }) => {
    await page.goto("/login");

    await page.getByPlaceholder("E-mail").fill("lider1@pulso.dev");
    await page.getByPlaceholder("Senha").fill("senha123");

    await Promise.all([
      page.waitForURL((url) => !url.pathname.includes("login")),
      page.getByRole("button", { name: "Entrar", exact: true }).click(),
    ]);

    await page.getByText("Minhas escalas", { exact: true }).click();

    await expect(page).toHaveURL(/minhas-escalas/);

    await page.getByText("Criar nova escala", { exact: true }).click();

    await expect(
      page.getByText("Nome da escala", { exact: true }),
    ).toBeVisible();

    console.log("chegou etapa 1");

    const inputs = page.locator("input");

    await inputs.nth(0).fill("Escala teste Playwright");

    await page
      .getByRole("button", {
        name: "Próxima etapa",
        exact: true,
      })
      .click();

    await expect(
      page.getByText("Mês de referência", { exact: true }),
    ).toBeVisible({
      timeout: 10000,
    });

    console.log("CHEGOU ETAPA 2");
  });
});
