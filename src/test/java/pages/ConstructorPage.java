package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы вкладок
    private final By bunsTab = By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Булки']/..");
    private final By saucesTab = By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Соусы']/..");
    private final By fillingsTab = By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Начинки']/..");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Методы для переключения вкладок
    public void clickBunsTab() {
        clickTab(bunsTab, "Булки");
    }

    public void clickSaucesTab() {
        clickTab(saucesTab, "Соусы");
    }

    public void clickFillingsTab() {
        clickTab(fillingsTab, "Начинки");
    }

    private void clickTab(By tabLocator, String tabName) {
        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(tabLocator));
            ((JavascriptExecutor)driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
                    tab
            );
            tab.click();
        } catch (ElementClickInterceptedException e) {
            // Резервный вариант через JavaScript
            ((JavascriptExecutor)driver).executeScript(
                    "arguments[0].click();",
                    driver.findElement(tabLocator)
            );
        }
    }

    // Проверка активности вкладки
    public boolean isTabActive(String tabName) {
        By activeTabWithText = By.xpath(
                String.format("//div[contains(@class, 'current')]//span[text()='%s']", tabName)
        );
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(activeTabWithText)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

}