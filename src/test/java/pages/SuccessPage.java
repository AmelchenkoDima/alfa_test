package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SuccessPage extends BasePage {

    private final By successTitle = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.TextView\").textMatches(\"(?i)Вход в Alfa-Test выполнен\")"
    );

    public SuccessPage(AppiumDriver driver) {
        super(driver);
    }

    public String getSuccessTitleText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successTitle)).getText();
    }
}
