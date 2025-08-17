package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;


    // Локаторы
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By accountButton = By.xpath("//p[text()='Личный Кабинет']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public boolean isAuthorizedUser() {
        return wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//button[text()='Оформить заказ']")))
                .isDisplayed();
    }

    @Step("Клик на кнопку 'Войти в аккаунт'")
    public void clickLoginAccountButton() {
        driver.findElement(loginButton).click();
    }


    @Step("Открыть главную страницу")
    public static MainPage open(WebDriver driver) {
        driver.get("https://stellarburgers.nomoreparties.site/");
        return new MainPage(driver);
    }

    @Step("Перейти в личный кабинет")
    public ProfilePage goToAccountPage() {
        driver.findElement(accountButton).click();
        return new ProfilePage(driver);
    }

}