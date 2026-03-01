package tests;

import helpers.Properties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static final String BASE_TEST_URL = "https://stellarburgers.education-services.ru";

    @BeforeEach
    void setBaseDrivers() {
        String driverName = Properties.testsProperties.webDriver();
        switch (driverName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "yandex":
                System.setProperty("webdriver.yandex.driver", "src/main/resources/drivers/yandexdriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:/Program Files/Yandex/YandexBrowser/Application/browser.exe");
                driver = new ChromeDriver(options);
                break;
            default:
                throw new IllegalArgumentException("В проекте нет драйвера: " + driverName);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
