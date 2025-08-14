import api.UserClient;
import browser.Browser;
import browser.WebDriverFactory;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.RegisterPage;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class RegistrationTest {
    private WebDriver driver;
    private String email;
    private String password;
    private String name;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        email = UserGenerator.generateRandomEmail();
        password = UserGenerator.generateRandomPassword();
        name = UserGenerator.generateRandomName();
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            UserClient.deleteUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void testSuccessfulRegistration(Browser browser) {
        WebDriver driver = WebDriverFactory.createDriver(browser);

        try {
            RegisterPage registerPage = RegisterPage.open(driver);
            registerPage.fillName(name)
                    .fillEmail(email)
                    .fillPassword(password)
                    .clickRegisterButton();

            LoginPage loginPage = new LoginPage(driver);
            assertTrue(loginPage.isLoginButtonDisplayed(),
                    "После регистрации должна отображаться страница входа");


        } finally {
            driver.quit();
            if (accessToken != null) {
                UserClient.deleteUser(accessToken);
            }
        }
    }
    @ParameterizedTest
    @EnumSource(Browser.class)
    public void testRegistrationWithShortPassword(Browser browser) {
        WebDriver driver = WebDriverFactory.createDriver(browser);
        String shortPassword = "12345"; // Меньше 6 символов

        try {
            RegisterPage registerPage = RegisterPage.open(driver);
            registerPage.fillName(name)
                    .fillEmail(email)
                    .fillPassword(shortPassword)
                    .clickRegisterButton();

            assertTrue(driver.getCurrentUrl().contains("register"),
                    "При некорректном пароле должна оставаться страница регистрации");

            assertTrue(registerPage.isErrorMessageDisplayed("Некорректный пароль"),
                    "Должно отображаться сообщение о некорректном пароле");

        } finally {
            driver.quit();
        }
    }

}