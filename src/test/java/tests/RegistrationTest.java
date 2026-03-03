package tests;

import data.LoginRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.RegisterPage;
import data.User;
import steps.UserSteps;
import java.time.Duration;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;


@ExtendWith(AllureJunit5.class)
@Feature("Регистрация пользователя")
public class RegistrationTest extends BaseTest {

    private final UserSteps steps = new UserSteps();
    private RegisterPage registerPage;
    private User user;

    @BeforeEach
    void setUp() {

        registerPage = new RegisterPage(driver);

        // Создаем пользователя через Api
        user = User.random();
    }

    @AfterEach
    void tearDown() {

        // Закрываем браузер
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Story("Регистрация")
    @DisplayName("Успешная регистрация")
    void shouldRegisterSuccessfully() {
        driver.get(BASE_TEST_URL + "/register");
        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(user.getPassword());
        registerPage.clickRegisterButton();

        // Переход на страницу входа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/login"));
        assertThat(driver.getCurrentUrl(), containsString("/login"));

        // Удаляем пользователя после теста
        String token = steps.login(new LoginRequest(user.getEmail(), user.getPassword()))
                .extract().path("accessToken");
        String jwt = token.replace("Bearer ", "");
        steps.delete(jwt).statusCode(HTTP_ACCEPTED);
    }

    @Test
    @Story("Регистрация")
    @DisplayName("Ошибка при пароле короче 6 символов")
    void shouldShowErrorForShortPassword() {
        driver.get(BASE_TEST_URL + "/register");
        registerPage = new RegisterPage(driver);
        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword("12345"); // 5 символов
        registerPage.clickRegisterButton();

        // Появляется ошибка "Некорректный пароль"
        assertThat(registerPage.isErrorVisible(), is(true));
        assertThat(registerPage.getErrorText(), containsString("Некорректный пароль"));
    }
}
