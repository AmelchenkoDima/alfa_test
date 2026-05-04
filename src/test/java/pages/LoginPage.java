package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By title = By.cssSelector("*[id='com.alfabank.qapp:id/tvTitle']");

    private final By loginField =
            By.xpath("//android.widget.EditText[@resource-id=\"com.alfabank.qapp:id/etUsername\"]"
    );

    private final By passwordField =
            By.xpath("//android.widget.EditText[@resource-id=\"com.alfabank.qapp:id/etPassword\"]"
            );

    private final By loginButton = By.cssSelector("#btnConfirm");

    private final By errorText = By.xpath("//android.widget.TextView[@resource-id=\"com.alfabank.qapp:id/tvError\"]");

    private final By passwordToggle = By.xpath("//android.widget.ImageButton[@content-desc=\"Show password\"]");

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isTitleDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void enterLogin(String login) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(loginField));
        el.clear();
        if (login != null && !login.isEmpty()) {
            el.sendKeys(login);
        }
    }

    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        el.clear();
        if (password != null && !password.isEmpty()) {
            el.sendKeys(password);
        }
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    /**
     * Кликает по иконке переключения видимости пароля.
     */
    public void clickPasswordToggle() {
        wait.until(ExpectedConditions.elementToBeClickable(passwordToggle)).click();
    }

    /**
     * Получает значение атрибута для поля логина.
     */
    public String getLoginFieldAttribute(String attribute) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(loginField)).getAttribute(attribute);
    }

    /**
     * Получает значение атрибута "password" для поля пароля.
     * Возвращает "true", если пароль маскирован, и "false", если виден.
     */
    public String getPasswordFieldAttribute(String attribute) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(passwordField)).getAttribute(attribute);
    }

    /**
     * Получает текст ошибки, дожидаясь его появления.
     */
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorText));
        return wait.until(d -> {
            String text = d.findElement(errorText).getText();
            if (text != null && !text.trim().isEmpty()) {
                return text;
            } else {
                return null;
            }
        });
    }
}
