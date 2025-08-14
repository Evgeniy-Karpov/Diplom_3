package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;

public class UserClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String USER_ENDPOINT = "/api/auth/user";

    @Step("Создать пользователя")
    public static Response createUser(String email, String password, String name) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .when()
                .post(REGISTER_ENDPOINT);
    }

    @Step("Удалить пользователя")
    public static void deleteUser(String accessToken) {
        try {
            given()
                    .header("Authorization", accessToken)
                    .baseUri(BASE_URL)
                    .when()
                    .delete(USER_ENDPOINT)
                    .then()
                    .statusCode(SC_ACCEPTED);
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Step("Логин пользователя")
    public static Response login(String email, String password) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(Map.of("email", email, "password", password))
                .when()
                .post(LOGIN_ENDPOINT);
    }

}