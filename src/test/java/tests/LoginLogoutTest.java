package tests;

import data.LoginRequest;
import data.User;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.*;
import steps.UserSteps;
import java.time.Duration;
import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(AllureJunit5.class)
@Feature("Авторизация")
public class LoginLogoutTest extends BaseTest {

    private final UserSteps steps = new UserSteps();
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private ForgotPasswordPage forgotPasswordPage;
    private User user;

    @BeforeEach
    public void setUp() {

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);

        // Создаем пользователя через API
        user = User.random();
        steps.register(user).statusCode(HTTP_OK);

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
    @Story("Вход в аккаунт")
    @DisplayName("Вход через кнопку «Войти в аккаунт» на главной")
    void shouldLoginViaMainPageButton() {
        mainPage.clickLoginButtonMainPage(); // Переход на /login
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));
        assertThat(driver.getCurrentUrl(), containsString("account/profile"));
    }

    @Test
    @Story("Вход в аккаунт")
    @DisplayName("Вход через кнопку «Личный кабинет»")
    void shouldLoginViaPersonalAccountButton() {
        mainPage.clickPersonalAccount(); // Открывает /login
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));
        assertThat(driver.getCurrentUrl(), containsString("account/profile"));
    }

    @Test
    @Story("Вход в аккаунт")
    @DisplayName("Вход через кнопку в форме регистрации")
    void shouldLoginViaRegisterFormLink() {
        driver.get(BASE_TEST_URL + "/register");
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink(); // "Войти" на странице регистрации
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));
        assertThat(driver.getCurrentUrl(), containsString("account/profile"));
    }

    @Test
    @Story("Вход в аккаунт")
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    void shouldLoginViaForgotPasswordLink() {
        driver.get(BASE_TEST_URL + "/forgot-password");
        forgotPasswordPage.clickLoginLink(); // "Войти" на странице восстановления
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));
        assertThat(driver.getCurrentUrl(), containsString("account/profile"));
    }

    @Test
    @Story("Выход из аккаунта")
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    void shouldLogoutViaLogoutButton() {
        mainPage.clickLoginButtonMainPage(); // Переход на /login
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        mainPage.clickPersonalAccount();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("account/profile"));
        assertThat(driver.getCurrentUrl(), containsString("account/profile"));

        profilePage.clickLogout();
        wait.until(ExpectedConditions.urlContains("/login"));
        assertThat(driver.getCurrentUrl(), containsString("/login"));
    }
}
