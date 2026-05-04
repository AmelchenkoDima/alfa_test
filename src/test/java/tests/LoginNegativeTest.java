package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.LoginPage;

@Tag("regression")
@Order(3)
public class LoginNegativeTest extends BaseTest {

    @ParameterizedTest
    @DisplayName("Негативные проверки входа: неверный логин или пароль")
    @CsvSource({
            "valid_login, 'WrongPassword123'",
            "'UnknownUser', valid_password"
    })
    public void testLoginWithInvalidCredentials(String login, String password) {
        LoginPage loginPage = new LoginPage(driver);

        // Подставляем валидные данные из конфига, если указан соответствующий ключ
        String loginToEnter = login.equals("valid_login") ? getProperty("login") : login;
        String passwordToEnter = password.equals("valid_password") ? getProperty("password") : password;

        loginPage.enterLogin(loginToEnter);
        loginPage.enterPassword(passwordToEnter);
        loginPage.clickLogin();

        // Проверка сообщения об ошибке
        String actualError = loginPage.getErrorMessage();
        Assertions.assertEquals("Введены неверные данные", actualError,
                "Текст сообщения об ошибке не совпадает с ожидаемым");
    }
}
