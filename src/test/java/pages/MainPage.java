package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;


    // Локаторы
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By accountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By constructorLink = By.xpath("//p[text()='Конструктор']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

    @Step("Проверить видимость кнопки конструктора")
    public boolean isConstructorLinkDisplayed() {
        return driver.findElement(constructorLink).isDisplayed();
    }

    @Step("Перейти в личный кабинет")
    public ProfilePage goToAccountPage() {
        driver.findElement(accountButton).click();
        return new ProfilePage(driver);
    }

}