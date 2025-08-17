package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By nameField = By.xpath("//label[contains(text(),'Имя')]/following-sibling::input");
    private final By emailField = By.xpath("//label[contains(text(),'Email')]/following-sibling::input");
    private final By passwordField = By.xpath("//label[contains(text(),'Пароль')]/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Статический метод для открытия страницы
    public static RegisterPage open(WebDriver driver) {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        RegisterPage page = new RegisterPage(driver);
        page.wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/register"));
        return page;
    }

    @Step("Кликнуть на ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver);
    }

    @Step("Заполнить поле 'Имя'")
    public RegisterPage fillName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(nameField)).sendKeys(name);
        return this;
    }

    @Step("Заполнить поле 'Email'")
    public RegisterPage fillEmail(String email) {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailField));
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Заполнить поле 'Пароль'")
    public RegisterPage fillPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    @Step("Проверить сообщение об ошибке")
    public boolean isErrorMessageDisplayed(String expectedMessage) {
        try {
            By errorLocator = By.xpath("//p[contains(@class, 'input__error') and contains(text(),'" + expectedMessage + "')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}