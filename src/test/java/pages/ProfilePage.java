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