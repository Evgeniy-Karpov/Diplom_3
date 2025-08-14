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
import pages.*;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class LoginTest {
    private WebDriver driver;
    private String email;
    private String password;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        email = UserGenerator.generateRandomEmail();
        password = UserGenerator.generateRandomPassword();
        String name = UserGenerator.generateRandomName();

        UserClient.createUser(email, password, name);
        accessToken = UserClient.login(email, password).path("accessToken");
    }

    @AfterEach
    public void tearDown() {
        UserClient.deleteUser(accessToken);
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void testLoginFromMainPage(Browser browser) {
        driver = WebDriverFactory.createDriver(browser);

        MainPage mainPage = MainPage.open(driver);
        mainPage.clickLoginAccountButton();

        performLogin();
        assertTrue(mainPage.isConstructorLinkDisplayed(), "Должна отображаться главная страница после входа");
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void testLoginFromAccountButton(Browser browser) {
        driver = WebDriverFactory.createDriver(browser);

        MainPage mainPage = MainPage.open(driver);
        mainPage.goToAccountPage();

        performLogin();
        assertTrue(mainPage.isConstructorLinkDisplayed(), "Должна отображаться главная страница после входа");
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void testLoginFromRegisterPage(Browser browser) {
        driver = WebDriverFactory.createDriver(browser);

        RegisterPage registerPage = RegisterPage.open(driver);
        registerPage.clickLoginLink();

        performLogin();
        assertTrue(new MainPage(driver).isConstructorLinkDisplayed(), "Должна отображаться главная страница после входа");
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void testLoginFromForgotPasswordPage(Browser browser) {
        driver = WebDriverFactory.createDriver(browser);

        ForgotPasswordPage forgotPasswordPage = ForgotPasswordPage.open(driver);
        forgotPasswordPage.clickLoginLink();

        performLogin();
        assertTrue(new MainPage(driver).isConstructorLinkDisplayed(), "Должна отображаться главная страница после входа");
    }

    private void performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillEmail(email);
        loginPage.fillPassword(password);
        loginPage.clickLoginButton();
    }
}