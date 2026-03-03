package steps;

import data.LoginRequest;
import data.User;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static tests.BaseTest.BASE_TEST_URL;

public class UserSteps {

    @Step("Регистрация пользователя")
    public ValidatableResponse register(User user) {
        return given()
                .baseUri(BASE_TEST_URL)
                .header("Content-Type", "application/json")
                .body(user)
                .post("/api/auth/register")
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse login(LoginRequest login) {
        return given()
                .baseUri(BASE_TEST_URL)
                .header("Content-Type", "application/json")
                .body(login)
                .post("/api/auth/login")
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String token) {
        return given()
                .baseUri(BASE_TEST_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/auth/user")
                .then();
    }
}
