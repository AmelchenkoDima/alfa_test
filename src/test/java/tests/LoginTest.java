package tests;

import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.SuccessPage;

@Tag("smoke")
@Tag("regression")
@Order(1)
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Успешный вход в приложение")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        SuccessPage successPage = new SuccessPage(driver);

        // Проверка заголовка на первой странице
        Assertions.assertTrue(loginPage.isTitleDisplayed(), "Заголовок страницы логина не отображается");

        // Выполнение входа
        loginPage.enterLogin(getProperty("login"));
        loginPage.enterPassword(getProperty("password"));
        loginPage.clickLogin();

        // Проверка перехода на следующую страницу после успешной авторизации
        String actualTitle = successPage.getSuccessTitleText();
        Assertions.assertEquals("Вход в Alfa-Test выполнен",
                actualTitle, "Текст заголовка успеха не совпадает");
    }
}
