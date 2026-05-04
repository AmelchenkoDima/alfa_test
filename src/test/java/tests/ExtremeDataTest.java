package tests;

import org.junit.jupiter.api.*;
import pages.LoginPage;

@Tag("regression")
@Order(5)
public class ExtremeDataTest extends BaseTest {

    @Test
    @DisplayName("Проверка ограничения ввода в поля логина и пароля (не более 50 символов)")
    public void testInputLimit() {
        LoginPage loginPage = new LoginPage(driver);

        // Строка длиной 70 символов
        String longString = "1234567890123456789012345678901234567890123456789012345678901234567890";
        int limit = 50;

        // 1. Проверяем поле логина
        loginPage.enterLogin(longString);
        String actualLogin = loginPage.getLoginFieldAttribute("text");

        // Проверка: Длина текста в поле логина не превышает 50 символов
        Assertions.assertTrue(actualLogin.length() <= limit, 
            "Поле ЛОГИН позволяет ввести более " + limit + " символов. Введено: " + actualLogin.length());

        // 2. Проверяем поле пароля
        loginPage.enterPassword(longString);
        String actualPassword = loginPage.getPasswordFieldAttribute("text");

        // Проверка: Длина текста в поле пароля не превышает 50 символов
        Assertions.assertTrue(actualPassword.length() <= limit, 
            "Поле ПАРОЛЬ позволяет ввести более " + limit + " символов. Введено: " + actualPassword.length());
    }
}
