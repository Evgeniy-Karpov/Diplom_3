import browser.Browser;
import browser.WebDriverFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import pages.ConstructorPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorTest {
    private WebDriver driver;


    @BeforeEach
    public void setupWait() {

    }

    static Stream<Arguments> browserAndSectionProvider() {
        return Stream.of(
                Arguments.of(Browser.CHROME, "Булки"),
                Arguments.of(Browser.CHROME, "Соусы"),
                Arguments.of(Browser.CHROME, "Начинки"),
                Arguments.of(Browser.YANDEX, "Булки"),
                Arguments.of(Browser.YANDEX, "Соусы"),
                Arguments.of(Browser.YANDEX, "Начинки")
        );
    }

    @ParameterizedTest
    @MethodSource("browserAndSectionProvider")
    public void testConstructorSections(Browser browser, String sectionName) {
        driver = WebDriverFactory.createDriver(browser);
        ConstructorPage constructorPage = new ConstructorPage(driver);
        driver.get("https://stellarburgers.nomoreparties.site/");

        try {
            switch (sectionName) {
                case "Булки":
                    constructorPage.clickBunsTab();
                    break;
                case "Соусы":
                    constructorPage.clickSaucesTab();
                    break;
                case "Начинки":
                    constructorPage.clickFillingsTab();
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный раздел: " + sectionName);
            }

            assertTrue(constructorPage.isTabActive(sectionName),
                    "Вкладка '" + sectionName + "' должна быть активной");

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

}