package tests;

import org.junit.jupiter.api.*;
import pages.LoginPage;

@Tag("regression")
@Order(4)
public class PasswordVisibilityTest extends BaseTest {

    @Test
    @DisplayName("Проверка переключения видимости пароля")
    public void testPasswordVisibilityToggle() {
        LoginPage loginPage = new LoginPage(driver);

        // 1. Вводим пароль
        String testPassword = "TestPassword123";
        loginPage.enterPassword(testPassword);

        // 2. Проверяем, что по умолчанию пароль скрыт (атрибут password = true)
        Assertions.assertEquals("true", loginPage.getPasswordFieldAttribute("password"), 
            "Пароль должен быть маскирован по умолчанию");

        // 3. Кликаем на иконку "глаза" (переключатель видимости)
        loginPage.clickPasswordToggle();

        // 4. Проверяем, что теперь пароль открыт (атрибут password = false)
        Assertions.assertEquals("false", loginPage.getPasswordFieldAttribute("password"), 
            "Пароль должен стать видимым после нажатия на иконку");
    }
}
