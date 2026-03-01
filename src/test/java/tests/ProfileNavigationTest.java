package tests;

import data.LoginRequest;
import data.User;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.ProfilePage;
import steps.UserSteps;
import java.time.Duration;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AllureJunit5.class)
@Feature("Навигация")
public class ProfileNavigationTest extends BaseTest {
    private UserSteps steps = new UserSteps();
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private User user;
    private String token;

    @BeforeEach
    public void setUp() {

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        // Создаем пользователя через API
        user = User.random();
        steps.register(user).statusCode(HTTP_OK);

        // Авторизация
        token = steps.login(new LoginRequest(user.getEmail(), user.getPassword()))
                .extract().path("accessToken");

        // Открываем главную страницу
        driver.get(BASE_TEST_URL);
    }

    @AfterEach
    void tearDown() {

        // Закрываем браузер
        if (driver != null) {
            driver.quit();
        }

        // Удаляем пользователя после теста
        String token = steps.login(new LoginRequest(user.getEmail(), user.getPassword()))
                .extract().path("accessToken");
        String jwt = token.replace("Bearer ", "");
        steps.delete(jwt).statusCode(HTTP_ACCEPTED);
    }

    @Test
    @Story("Переход в Личный кабинет")
    @DisplayName("Переход по клику на кнопку «Личный кабинет»")
    void shouldNavigateToProfileViaPersonalAccount() {
        mainPage.clickPersonalAccount();
        assertThat(driver.getCurrentUrl(), containsString("/login"));
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));
        assertThat(driver.getCurrentUrl(), containsString("account/profile"));
    }

    @Test
    @Story("Возврат из Личного кабинета")
    @DisplayName("Переход в конструктор по клику на кнопку «Конструктор»")
    void shouldReturnToConstructorViaConstructorLink() {
        mainPage.clickPersonalAccount();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));

        profilePage.clickConstructor();
        assertEquals(mainPage.getConstructorTitle(), "Соберите бургер");
    }

    @Test
    @Story("Возврат из ЛК")
    @DisplayName("Переход в конструктор по клику на логотип")
    void shouldReturnToConstructorViaLogo() {
        mainPage.clickPersonalAccount();
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));

        profilePage.clickLogo();
        assertEquals(mainPage.getConstructorTitle(), "Соберите бургер");
    }
}
