package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.LoginPage;

@Tag("regression")
@Order(2)
public class LoginValidationTest extends BaseTest {

    @ParameterizedTest
    @DisplayName("Проверка ошибки при пустом одном поле (логин или пароль)")
    @CsvSource({
            "valid_login, ''",
            "'', valid_password"
    })
    public void testLoginWithOneEmptyField(String login, String password) {
        LoginPage loginPage = new LoginPage(driver);

        // Определяем, что вводить: валидное значение из конфига или пустую строку
        String loginToEnter = login.equals("valid_login") ? getProperty("login") : login;
        String passwordToEnter = password.equals("valid_password") ? getProperty("password") : password;

        // Выполнение действий
        loginPage.enterLogin(loginToEnter);
        loginPage.enterPassword(passwordToEnter);
        loginPage.clickLogin();

        // Проверка сообщения об ошибке
        String actualError = loginPage.getErrorMessage();
        Assertions.assertEquals("Введены неверные данные",
                actualError,
                "Текст сообщения об ошибке не совпадает с ожидаемым");
    }
}
