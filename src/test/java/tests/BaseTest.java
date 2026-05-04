package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected AndroidDriver driver;
    protected Properties properties = new Properties();
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Загружаем конфигурации из файлов
        loadProperties("src/test/resources/configs/emulator.properties");
        loadProperties("src/test/resources/configs/login.properties");

        String apkPath = new File(getProperty("app")).getAbsolutePath();

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(getProperty("platformName"))
                .setAutomationName(getProperty("automationName"))
                .setDeviceName(getProperty("deviceName"))
                .setApp(apkPath)
                .setAppPackage(getProperty("appPackage"))
                .setAppActivity(getProperty("appActivity"))
                .setNoReset(Boolean.parseBoolean(getProperty("noReset")))
                .setFullReset(Boolean.parseBoolean(getProperty("fullReset")))
                .setNewCommandTimeout(Duration.ofSeconds(Long.parseLong(getProperty("newCommandTimeout"))));

        driver = new AndroidDriver(new URL(getProperty("remoteURL")), options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * Получает значение свойства.
     * Приоритет:
     * 1. Системные свойства (-DTEST_LOGIN)
     * 2. Переменные окружения (TEST_LOGIN)
     * 3. Значение из файлов .properties (login.properties / emulator.properties)
     */
    protected String getProperty(String key) {
        String envKey = "TEST_" + key.toUpperCase().replace(".", "_");

        // 1. Пытаемся взять из System Properties (-D)
        String value = System.getProperty(envKey);

        // 2. Пытаемся взять из Environment Variables
        if (value == null || value.isEmpty()) {
            value = System.getenv(envKey);
        }

        // Если нашли в окружении (CI/CD или ручной ввод в настройках запуска) — возвращаем
        if (value != null && !value.isEmpty()) {
            return value;
        }

        // 3. Если не нашли в окружении, берем из загруженных файлов .properties (локальный запуск)
        value = properties.getProperty(key);

        if (value != null) {
            // Для логина и пароля выводим уведомление, что используем локальный файл
            if (key.equals("login") || key.equals("password")) {
                System.out.println("ИНФО: Переменная окружения " + envKey + " не найдена. Используется значение из локального файла настроек для: " + key);
            }
            return value;
        }

        throw new RuntimeException("Property '" + key + "' (or environment variable '" + envKey + "') not found in Environment Variables or Properties files.");
    }

    private void loadProperties(String path) {
        File file = new File(path);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load properties from " + path, e);
            }
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
