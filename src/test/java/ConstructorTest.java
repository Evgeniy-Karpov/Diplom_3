import browser.Browser;
import browser.WebDriverFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class ConstructorTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static int counter = 1;

    @BeforeEach
    public void setupWait() {

    }

    @ParameterizedTest
    @EnumSource(value = Browser.class, names = {"CHROME", "YANDEX"})
    public void testFillingsSectionInDifferentBrowsers(Browser browser) {
        // 1. Инициализация драйвера для конкретного браузера
        driver = WebDriverFactory.createDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://stellarburgers.nomoreparties.site/");

        try {
            // 2. Клик по вкладке "Начинки" с улучшенным локатором
            WebElement fillingsTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Начинки']/..")));

            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", fillingsTab);
            fillingsTab.click();

            // 3. Проверка конкретной начинки с улучшенным ожиданием
            WebElement molluskMeat = wait.until(driver -> {
                WebElement el = driver.findElement(
                        By.xpath("//img[@alt='Мясо бессмертных моллюсков Protostomia']"));
                return el.isDisplayed() ? el : null;
            });

            assertTrue(isElementInViewport(molluskMeat),
                    "Начинка должна быть видима в браузере: " + browser.name());

        } catch (Exception e) {
            // Диагностика при ошибках
            System.out.println("Ошибка в браузере: " + browser.name());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page source snippet: " + driver.getPageSource().substring(0, 500));
            throw e;
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private boolean isElementInViewport(WebElement element) {
        try {
            return wait.until(driver -> {
                Rectangle rect = element.getRect();
                Dimension windowSize = driver.manage().window().getSize();

                // Диагностика
                System.out.println("Проверка #" + counter++ + " в " + driver.getClass().getSimpleName());
                System.out.printf("Элемент: X=%d Y=%d Ш=%d В=%d%n",
                        rect.x, rect.y, rect.width, rect.height);
                System.out.printf("Окно: Ш=%d В=%d%n%n",
                        windowSize.width, windowSize.height);

                return rect.x >= 0 &&
                        rect.y >= 0 &&
                        rect.x + rect.width <= windowSize.width &&
                        rect.y + rect.height <= windowSize.height;
            });
        } catch (StaleElementReferenceException e) {
            System.out.println("Элемент устарел при проверке viewport");
            return false;
        }
    }
}