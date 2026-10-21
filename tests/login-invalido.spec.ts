import { test, expect } from "@playwright/test";

test("deve impedir login com credenciais inválidas", async ({ page }) => {
  await page.goto("/login");

  await page.locator('input[name="email"]').fill("teste@teste.com");

  await page.locator('input[type="password"]').fill("senhaerrada");

  await page.locator('button[type="submit"]').click();

  // Se o Usuário continua na tela de login
  await expect(page).toHaveURL(/login/);
});
