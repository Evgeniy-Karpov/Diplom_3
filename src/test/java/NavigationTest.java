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
public class NavigationTest {
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
    public void testNavigateFromProfileToConstructorViaLink(Browser browser) {
        driver = WebDriverFactory.createDriver(browser);

        MainPage mainPage = LoginPage.open(driver)
                .login(email, password);
        ProfilePage profilePage = mainPage.goToAccountPage();

        assertTrue(profilePage.isProfilePageLoaded(), "Страница профиля должна быть загружена");

        mainPage = profilePage.clickConstructorLink();

    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void testNavigateFromProfileToConstructorViaLogo(Browser browser) {
        driver = WebDriverFactory.createDriver(browser);


        MainPage mainPage = LoginPage.open(driver)
                .login(email, password);
        ProfilePage profilePage = mainPage.goToAccountPage();

        assertTrue(profilePage.isProfilePageLoaded(), "Страница профиля должна быть загружена");

        mainPage = profilePage.clickLogo();

    }

}