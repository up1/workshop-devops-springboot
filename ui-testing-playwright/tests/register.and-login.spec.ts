import { test, expect } from '@playwright/test';

test('Success with register and login', async ({ page }) => {
  await page.goto('http://localhost:8080/login');
  
  // Check "Bank App"
  await expect(page.locator('body > nav > a')).toHaveText(/Bank App/);

  // Register
  await page.getByRole('link', { name: 'Register here' }).click();
  await page.locator('input[name="username"]').fill('user1');
  await page.locator('input[name="password"]').fill('user1');
  await page.getByRole('button', { name: 'Register' }).click();

  // Login
  await page.locator('input[name="username"]').fill('user1');
  await page.locator('input[name="password"]').fill('user1');
  await page.getByRole('button', { name: 'Login' }).click();

  await expect(page.locator('body > div > div.text-center > h2')).toHaveText('Welcome, user1');
  await expect(page.locator('body > div > div.text-center > h3')).toHaveText('Current Balance: $0.00');

  // Logout
  await page.getByRole('link', { name: 'Logout' }).click();
  await expect(page.locator('body > div > form > button')).toHaveText('Login');
});