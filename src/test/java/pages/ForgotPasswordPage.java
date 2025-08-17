package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {
    private final WebDriver driver;

    private final By loginLink = By.xpath("//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик на ссылку 'Войти'")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    @Step("Открыть страницу восстановления пароля")
    public static ForgotPasswordPage open(WebDriver driver) {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
        return new ForgotPasswordPage(driver);
    }
}