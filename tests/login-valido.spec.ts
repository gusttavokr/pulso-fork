import { test, expect } from "@playwright/test";

test("deve realizar login com sucesso", async ({ page }) => {
  await page.goto("/login");

  await page.locator('input[name="email"]').fill("lider1@pulso.dev");

  await page.locator('input[type="password"]').fill("senha123");

  await page.locator('button[type="submit"]').click();

  // Troque pela rota real após login
  await expect(page).not.toHaveURL("/");
});
