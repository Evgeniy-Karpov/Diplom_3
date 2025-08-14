package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By logoutButton = By.xpath("//button[text()='Выход']");
    private final By constructorLink = By.xpath("//p[text()='Конструктор']");
    private final By logo = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");
    private final By profileSection = By.xpath("//a[text()='Профиль']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step ("Проверить видимость раздела 'Профиль'")
    public boolean isProfileSectionVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(profileSection))
                    .isDisplayed();
        } catch (Exception e) {
            System.out.println("Profile section not found. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    @Step("Выйти из аккаунта")
    public void logout() {
        driver.findElement(logoutButton).click();
    }


    @Step("Кликнуть на 'Конструктор' и вернуться на главную")
    public MainPage clickConstructorLink() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorLink)).click();
        return new MainPage(driver);
    }

    @Step("Кликнуть на логотип и вернуться на главную")
    public MainPage clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logo)).click();
        return new MainPage(driver);
    }

    @Step("Нажать кнопку 'Выход'")
    public LoginPage clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        return new LoginPage(driver);
    }

    @Step("Проверить, что страница профиля загружена")
    public boolean isProfilePageLoaded() {
        try {
            return wait.until(ExpectedConditions.urlContains("/account/profile"))
                    && isProfileSectionVisible();
        } catch (Exception e) {
            System.out.println("Profile page not loaded. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }
}