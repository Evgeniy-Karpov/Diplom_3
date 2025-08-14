package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.xpath("//input[@name='name']");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By loginButton = By.xpath("//button[contains(text(), 'Войти')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Заполнить поле 'Email'")
    public void fillEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнить поле 'Пароль'")
    public void fillPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Открыть страницу входа")
    public static LoginPage open(WebDriver driver) {
        driver.get("https://stellarburgers.nomoreparties.site/login");
        return new LoginPage(driver);
    }

    @Step("Выполнить вход")
    public MainPage login(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickLoginButton();
        return new MainPage(driver);
    }

    @Step("Проверить видимость кнопки входа")
    public boolean isLoginButtonDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains("login"));
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(loginButton)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }
}